package cn.coderap.service;

import cn.coderap.pojo.vo.OrderVO;
import cn.coderap.pojo.vo.ResponseVO;

/**
 * Created by yw
 * 2021/5/10
 */
public interface IOrderService {

    ResponseVO<OrderVO> create(Integer uid,Integer shippingId);
}
