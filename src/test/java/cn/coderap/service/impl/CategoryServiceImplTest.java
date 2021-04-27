package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.enums.ResponseEnum;
import cn.coderap.service.ICategoryService;
import cn.coderap.pojo.vo.CategoryVO;
import cn.coderap.pojo.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CategoryServiceImplTest extends NewMallApplicationTest {

    @Autowired
    private ICategoryService categoryService;

    @Test
    public void selectAll() {
        ResponseVO<List<CategoryVO>> listResponseVO = categoryService.categories();
        Assert.assertEquals(ResponseEnum.SUCESS.getCode(), listResponseVO.getCode());
    }

    @Test
    public void findSubCategoryId() {
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001, set);
        log.info("set={}",set);
    }
}