package cn.coderap.service.impl;

import cn.coderap.enums.OrderStatusEnum;
import cn.coderap.enums.PaymentTypeEnum;
import cn.coderap.enums.ProductStatusEnum;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.mapper.OrderItemMapper;
import cn.coderap.mapper.OrderMapper;
import cn.coderap.mapper.ProductMapper;
import cn.coderap.mapper.ShippingMapper;
import cn.coderap.pojo.Order;
import cn.coderap.pojo.OrderItem;
import cn.coderap.pojo.Product;
import cn.coderap.pojo.Shipping;
import cn.coderap.pojo.vo.CartRedisVO;
import cn.coderap.pojo.vo.OrderItemVO;
import cn.coderap.pojo.vo.OrderVO;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.ICartService;
import cn.coderap.service.IOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yw
 * 2021/5/10
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private ShippingMapper shippingMapper;
    @Resource
    private ICartService cartService;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;

    @Transactional
    @Override
    public ResponseVO<OrderVO> create(Integer uid, Integer shippingId) {
        //1、收货地址校验（反正后面要查出来的）
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping == null) {
            return ResponseVO.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //2、获取购物车中被选中的商品，然后校验数据库中是否有该商品、商品是否下架、库存是否充足
        List<CartRedisVO> cartRedisVOList = cartService.listForCartRedisVO(uid).stream()
                .filter(CartRedisVO::getSelected).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartRedisVOList)) {
            return ResponseVO.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }

        //获取cartRedisVOList里的productIds(避免在循环中查sql)
        Set<Integer> productIds = cartRedisVOList.stream().map(CartRedisVO::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIds);
        //将list转成map方便后续的判断
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getId, e -> e));

        Long orderNo = generateOrderNo();
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartRedisVO cartRedisVO : cartRedisVOList) {
            Product product = productMap.get(cartRedisVO.getProductId());
            //数据库中是否有该商品
            if (product == null) {
                return ResponseVO.error(ResponseEnum.PRODUCT_NOT_EXIST,"商品不存在.productId=" + cartRedisVO.getProductId());
            }
            //商品是否下架
            if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
                return ResponseVO.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,"商品不是在售状态." + product.getName());
            }
            //库存是否充足
            if (cartRedisVO.getQuantity() > product.getStock()) {
                return ResponseVO.error(ResponseEnum.PRODUCT_STOCK_ERROR,"库存不正确." + product.getName());
            }
            OrderItem orderItem = buildOrderItem(uid, orderNo, cartRedisVO.getQuantity(), product);
            orderItemList.add(orderItem);
            //5、减库存 //TODO 超卖？
            product.setStock(product.getStock()-cartRedisVO.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVO.error(ResponseEnum.ERROR);
            }
        }
        //3、计算总价（只计算选中的商品，企业中还会有优惠券）
        //4、生成订单并入库（order和order_item），其中会用到事务
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int rowOfOrder = orderMapper.insertSelective(order);
        if (rowOfOrder <= 0) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        int rowOfOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowOfOrderItem <= 0) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }

        //6、更新购物车（选中的商品），之所以没有放在减库存之后，是因为redis的事务（打包命令）不能回滚
        for (CartRedisVO cartRedisVO : cartRedisVOList) {
            cartService.delete(uid, cartRedisVO.getProductId());
        }
        //7、构造OrderVO返回给前端
        OrderVO orderVO = buildOrderVO(order,orderItemList,shipping);
        return ResponseVO.success(orderVO);
    }

    private OrderVO buildOrderVO(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);

        List<OrderItemVO> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(e, orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());
        orderVO.setOrderItemVoList(orderItemVoList);

        orderVO.setShippingId(shipping.getId());
        orderVO.setShippingVO(shipping);
        return orderVO;
    }

    private Order buildOrder(Integer uid,Long orderNo,Integer shippingId,List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }

    /**
     * 这里简单处理
     * 企业级：分布式唯一id/主键
     * @return
     */
    private Long generateOrderNo() {
        //时间戳+随机数
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
