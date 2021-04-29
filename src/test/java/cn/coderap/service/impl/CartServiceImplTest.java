package cn.coderap.service.impl;

import cn.coderap.NewMallApplicationTest;
import cn.coderap.pojo.bo.CartAddForm;
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
        form.setProductId(29);
        form.setSelected(true);
        ResponseVO<CartVO> responseVO = cartService.add(1, form);
        log.info("list={}", gson.toJson(responseVO));
    }

    @Test
    public void list() {
        ResponseVO<CartVO> list = cartService.list(1);
        log.info("list={}", gson.toJson(list));
    }

}