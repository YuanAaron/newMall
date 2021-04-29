package cn.coderap.service;

import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.ResponseVO;

public interface ICartService {
    ResponseVO<CartVO> add(Integer uid,CartAddForm form);

    ResponseVO<CartVO> list(Integer uid);
}
