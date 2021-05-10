package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.OrderVO;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.ICartService;
import cn.coderap.service.IOrderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class OrderServiceImplTest extends NewMallApplicationTest {

    @Resource
    private IOrderService orderService;
    @Resource
    private ICartService cartService;

    private Integer uid = 1;
    private Integer productId = 26;
    private Integer shippingId = 4;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void before() {
        CartAddForm form = new CartAddForm();
        form.setProductId(productId);
        form.setSelected(true);
        ResponseVO<CartVO> responseVO = cartService.add(1, form);
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }

    @Test
    public void create() {
        ResponseVO<OrderVO> responseVO = orderService.create(uid, shippingId);
        log.info("result={}", gson.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }
}