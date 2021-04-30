package cn.coderap.service;

import cn.coderap.pojo.bo.CartAddForm;
import cn.coderap.pojo.bo.CartUpdateForm;
import cn.coderap.pojo.vo.CartVO;
import cn.coderap.pojo.vo.ResponseVO;

public interface ICartService {
    ResponseVO<CartVO> add(Integer uid,CartAddForm form);

    ResponseVO<CartVO> list(Integer uid);

    ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm form);

    ResponseVO<CartVO> delete(Integer uid, Integer productId);

    ResponseVO<CartVO> selectAll(Integer uid);

    ResponseVO<CartVO> unSelectAll(Integer uid);

    ResponseVO<Integer> sum(Integer uid);
}
