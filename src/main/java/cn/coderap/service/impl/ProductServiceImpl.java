package cn.coderap.service.impl;

import cn.coderap.mapper.ProductMapper;
import cn.coderap.pojo.Product;
import cn.coderap.service.ICategoryService;
import cn.coderap.service.IProductService;
import cn.coderap.vo.ProductVO;
import cn.coderap.vo.ResponseVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by yw
 * 2021/4/25
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ICategoryService categoryService;
    @Resource
    private ProductMapper productMapper;

    @Override
    public ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        //根据categoryId获取其所有子分类
        Set<Integer> set = new HashSet<>();
        //该判断不能少，否则set有null这个元素，set.size()为1
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId, set);
            set.add(categoryId);
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectByCategoryIdSet(set);
//        log.info("productList={}", productList);
        List<ProductVO> productVOList = productList.stream().map(e -> {
            ProductVO vo = new ProductVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productVOList);
        return ResponseVO.success(pageInfo);
    }
}
