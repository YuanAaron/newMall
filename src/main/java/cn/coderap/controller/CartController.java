package cn.coderap.controller;

import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.ResponseVO;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by yw
 * 2021/4/27
 */
@RestController
public class CartController {

    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm) {
        return null;
    }
}
