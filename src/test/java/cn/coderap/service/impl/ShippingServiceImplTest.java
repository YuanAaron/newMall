package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.pojo.bo.ShippingForm;
import cn.coderap.pojo.vo.ResponseVO;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Map;

@Slf4j
public class ShippingServiceImplTest extends NewMallApplicationTest {

    @Resource
    private ShippingServiceImpl shippingService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Integer uid = 1;
    private Integer shippingId;
    private ShippingForm form;

    @Before
    public void before() {
        ShippingForm form = new ShippingForm();
        form.setReceiverName("zhangsan");
        form.setReceiverPhone("010");
        form.setReceiverMobile("13766668888");
        form.setReceiverProvince("北京");
        form.setReceiverCity("北京市");
        form.setReceiverDistrict("昌平区");
        form.setReceiverAddress("xxx街道yyy");
        form.setReceiverZip("010");
        this.form = form;

        add();
    }

    public void add() {
        ResponseVO<Map<String, Integer>> responseVO = shippingService.add(uid, form);
        this.shippingId = responseVO.getData().get("shippingId");
        log.info("result={}", gson.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }

    @After
    public void delete() {
        ResponseVO responseVO = shippingService.delete(uid, shippingId);
        log.info("result={}", gson.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }

    @Test
    public void update() {
        form.setReceiverDistrict("海淀区");
        ResponseVO<Map<String, Integer>> responseVO = shippingService.update(uid, shippingId,form);
        log.info("result={}", gson.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }

    @Test
    public void list() {
        ResponseVO<PageInfo> responseVO = shippingService.list(uid, 1, 3);
        log.info("result={}", gson.toJson(responseVO));
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), responseVO.getCode());
    }
}