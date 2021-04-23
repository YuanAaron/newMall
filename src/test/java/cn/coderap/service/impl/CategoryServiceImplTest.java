package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.service.ICategoryService;
import cn.coderap.vo.CategoryVO;
import cn.coderap.vo.ResponseVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CategoryServiceImplTest extends NewMallApplicationTest {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVO<List<CategoryVO>> listResponseVO = categoryService.categories();
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), listResponseVO.getCode());
    }
}