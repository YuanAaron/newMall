package cn.coderap.mapper;

import cn.coderap.pojo.Shipping;

import java.util.List;
import java.util.Map;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    Integer deleteByUidAndId(Map<String,Integer> map);

    Integer update(Shipping shipping);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByUidAndShippingId(Integer uid,Integer shippingId);
}