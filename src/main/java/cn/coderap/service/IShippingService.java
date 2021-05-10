package cn.coderap.service;

import cn.coderap.pojo.bo.ShippingForm;
import cn.coderap.pojo.vo.ResponseVO;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IShippingService {

    ResponseVO<Map<String,Integer>> add(Integer uid, ShippingForm form);

    ResponseVO delete(Integer uid,Integer shippingId);

    ResponseVO update(Integer uid,Integer shippingId,ShippingForm form);

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
