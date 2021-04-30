package cn.coderap.controller;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.pojo.User;
import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.bo.CartUpdateForm;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.ICartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by yw
 * 2021/4/27
 */
@RestController
public class CartController {

    @Resource
    private ICartService cartService;

    @GetMapping("/carts")
    public ResponseVO<CartVO> list(HttpSession session) {
        User user = (User)session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session) { //TODO 用户登录后可以放到ThreadLocal中，以方便使用
        User user = (User)session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.add(user.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVO<CartVO> update(@PathVariable Integer productId,
                                     @RequestBody CartUpdateForm form,
                                     HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.update(user.getId(), productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVO<CartVO> delete(@PathVariable Integer productId,
                                     HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVO<CartVO> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVO<CartVO> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVO<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(NewMallConsts.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}
