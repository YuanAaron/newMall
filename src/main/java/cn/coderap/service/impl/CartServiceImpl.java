package cn.coderap.service.impl;

import cn.coderap.enums.ProductStatusEnum;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.mapper.ProductMapper;
import cn.coderap.pojo.Product;
import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.bo.CartUpdateForm;
import cn.coderap.pojo.vo.CartProductVO;
import cn.coderap.pojo.vo.CartRedisVO;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.ICartService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yw
 * 2021/4/27
 */
@Service
public class CartServiceImpl implements ICartService {

    public static final String CART_REDIS_KEY_TEMPLATE="CART_%d";

    @Resource
    private ProductMapper productMapper;
    //这里不能使用@Resource，原因：
    //@Autowired注解默认按照类型（byType）装配依赖对象，如果想按照名称（byName）来装配，可以结合@Qualifier注解一起使用；
    //@Resource默认按照名字（ByName）自动注入，
    @Autowired
    private StringRedisTemplate redisTemplate;
    private Gson gson = new Gson();

    @Override
    public ResponseVO<CartVO> add(Integer uid, CartAddForm form) {
        //1、判断商品是否存在
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        if (product == null) {
            return ResponseVO.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //2、判断商品是否在售
        if (product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode())
                || product.getStatus().equals(ProductStatusEnum.DELETE.getCode())) {
            return ResponseVO.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        //3、判断商品库存是否充足
        if (product.getStock()<=0) {
            return ResponseVO.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //4、写入redis
        //key: CART_1
        //redis购物车数据类型的选择：
        //使用List，在更新购物车中某个商品的数量时需要遍历，效率低，而使用Hash可以避免
        Integer quantity = 1;
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = opsForHash.get(key, String.valueOf(product.getId()));
        CartRedisVO cart;
        if (StringUtils.isEmpty(value)) {
            //没有该商品，新增
            cart = new CartRedisVO(product.getId(), quantity,form.getSelected());
        } else {
            //已经有该商品，数量+1
            cart = gson.fromJson(value, CartRedisVO.class);
            cart.setQuantity(cart.getQuantity()+quantity);
        }
        opsForHash.put(key,String.valueOf(product.getId()), gson.toJson(cart));

        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> list(Integer uid) {
        List<CartRedisVO> cartList = listForCartRedisVO(uid);
        List<Product> productList = productMapper.selectByCartList(cartList);

        CartVO cartVO = new CartVO();
        List<CartProductVO> cartProductVoList = new ArrayList<>();
        Boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (CartRedisVO cart : cartList) {
            Product product = productList.stream().filter(e->e.getId().equals(cart.getProductId())).findAny().orElse(null);
            if (product != null) {
                CartProductVO cartProductVO = new CartProductVO(cart.getProductId(), cart.getQuantity(), product.getName(),
                        product.getSubtitle(), product.getMainImage(),
                        product.getPrice(), product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(), cart.getSelected());
                cartProductVoList.add(cartProductVO);

                if (!cartProductVO.getProductSelected()) {
                    selectAll = false;
                }

                cartTotalQuantity += cartProductVO.getQuantity();
                if (cartProductVO.getProductSelected()) {
                    cartTotalPrice = cartTotalPrice.add(cartProductVO.getProductTotalPrice());
                }
            }
//            cartTotalQuantity += cart.getQuantity(); //老师放在了这里
        }
        cartVO.setCartProductVoList(cartProductVoList);
        //有一个没有选中，就不叫全选
        cartVO.setSelectAll(selectAll);
        cartVO.setCartTotalQuantity(cartTotalQuantity);
        //总价只计算选中的
        cartVO.setCartTotalPrice(cartTotalPrice);
        return ResponseVO.success(cartVO);
    }

    @Override
    public ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm form) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = opsForHash.get(key, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品
            ResponseVO.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        //已经有该商品，更新
        CartRedisVO cart = gson.fromJson(value, CartRedisVO.class);
        if (form.getQuantity() != null && form.getQuantity() >= 0 ) {
            cart.setQuantity(form.getQuantity());
        }
        if (form.getSelected() != null) {
            cart.setSelected(form.getSelected());
        }
        opsForHash.put(key, String.valueOf(productId), gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = opsForHash.get(key, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品
            ResponseVO.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);
        }
        opsForHash.delete(key, String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        List<CartRedisVO> cartRedisVoList = listForCartRedisVO(uid);
        for (CartRedisVO cart : cartRedisVoList) {
            cart.setSelected(true);
            opsForHash.put(key, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> unSelectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        List<CartRedisVO> cartRedisVoList = listForCartRedisVO(uid);
        for (CartRedisVO cart : cartRedisVoList) {
            cart.setSelected(false);
            opsForHash.put(key, String.valueOf(cart.getProductId()), gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVO<Integer> sum(Integer uid) {
        Integer sum = listForCartRedisVO(uid).stream().map(CartRedisVO::getQuantity).reduce(0, Integer::sum);
        return ResponseVO.success(sum);
    }

    public List<CartRedisVO> listForCartRedisVO(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String key = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Map<String, String> map = opsForHash.entries(key);

        List<CartRedisVO> list = new ArrayList<>();
        for (Map.Entry<String,String> entry : map.entrySet()) {
            CartRedisVO cart = gson.fromJson(entry.getValue(), CartRedisVO.class);
            list.add(cart);
        }
        return list;
    }
}
