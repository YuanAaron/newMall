package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.service.IProductService;
import cn.coderap.pojo.vo.ResponseVO;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

public class ProductServiceImplTest extends NewMallApplicationTest {

    @Resource
    private IProductService productService;

    @Test
    public void list() {
        //100002 100012 null
        ResponseVO<PageInfo> list = productService.list(null, 1, 10);
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), list.getCode());
    }

}