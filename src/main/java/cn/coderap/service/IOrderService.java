package cn.coderap.service;

import cn.coderap.pojo.vo.OrderVO;
import cn.coderap.pojo.vo.ResponseVO;
import com.github.pagehelper.PageInfo;

/**
 * Created by yw
 * 2021/5/10
 */
public interface IOrderService {

    ResponseVO<OrderVO> create(Integer uid,Integer shippingId);
    ResponseVO<PageInfo> list(Integer uid,Integer pageNum,Integer pageSize);
    ResponseVO<OrderVO> detail(Integer uid,Long orderNo);
    ResponseVO cancel(Integer uid,Long orderNo);
    void paid(Long orderNo);
}
