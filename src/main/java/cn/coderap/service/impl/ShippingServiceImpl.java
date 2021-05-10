package cn.coderap.service.impl;

import cn.coderap.enums.ResponseEnum;
import cn.coderap.mapper.ShippingMapper;
import cn.coderap.pojo.Shipping;
import cn.coderap.pojo.bo.ShippingForm;
import cn.coderap.pojo.vo.ResponseVO;
import cn.coderap.service.IShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yw
 * 2021/4/30
 */
@Service
public class ShippingServiceImpl implements IShippingService {
    @Resource
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVO<Map<String,Integer>> add(Integer uid, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        //如果返回的字段多，可以新建一个VO对象
        Map<String,Integer> map = new HashMap<>();
        map.put("shippingId", shipping.getId());
        return ResponseVO.success(map);
    }

    @Override
    public ResponseVO delete(Integer uid, Integer shippingId) {
        Map<String,Integer> map = new HashMap<>();
        map.put("uid", uid);
        map.put("shippingId", shippingId);
        int row = shippingMapper.deleteByUidAndId(map);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO update(Integer uid, Integer shippingId, ShippingForm form) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        int row = shippingMapper.update(shipping);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.ERROR);
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ResponseVO.success(pageInfo);
    }
}
