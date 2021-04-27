package cn.coderap.controller;

import cn.coderap.service.IProductService;
import cn.coderap.pojo.vo.ResponseVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yw
 * 2021/4/25
 */
@RestController
public class ProductController {

    @Resource
    private IProductService productService;

    @GetMapping("/products")
    public ResponseVO<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return productService.list(categoryId,pageNum,pageSize);
    }
}
