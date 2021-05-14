package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.bo.CartUpdateForm;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.ICartService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

@Slf4j
public class CartServiceImplTest extends NewMallApplicationTest {

    @Resource
    private ICartService cartService;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddForm form = new CartAddForm();
        form.setProductId(26);
        form.setSelected(true);
        ResponseVO<CartVO> responseVO = cartService.add(1, form);
        log.info("list={}", gson.toJson(responseVO));
    }

    @Test
    public void list() {
        ResponseVO<CartVO> list = cartService.list(1);
        log.info("list={}", gson.toJson(list));
    }

    @Test
    public void update() {
        CartUpdateForm form = new CartUpdateForm();
        form.setQuantity(5);
        form.setSelected(true);
        ResponseVO<CartVO> responseVO = cartService.update(1, 26, form);
        log.info("list={}", gson.toJson(responseVO));
    }

    @Test
    public void delete() {
        ResponseVO<CartVO> responseVO = cartService.delete(1, 27);
        log.info("list={}", gson.toJson(responseVO));
    }

    @Test
    public void selectAll() {
        ResponseVO<CartVO> responseVO = cartService.selectAll(1);
        log.info("list={}", gson.toJson(responseVO));
    }

    @Test
    public void unSelectAll() {
//        ResponseVO<CartVO> responseVO = cartService.unSelectAll(1);
//        log.info("list={}", gson.toJson(responseVO));
    }

    @Test
    public void sum() {
        ResponseVO<Integer> responseVO = cartService.sum(1);
        log.info("sum={}", gson.toJson(responseVO));
    }

}