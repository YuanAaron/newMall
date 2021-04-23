package cn.coderap.controller;

import cn.coderap.service.ICategoryService;
import cn.coderap.vo.CategoryVO;
import cn.coderap.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yw
 * 2021/4/20
 */
@Slf4j
@RestController
public class CategoryController {

    @Resource
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVO<List<CategoryVO>> categories() {
        return categoryService.categories();
    }
}
