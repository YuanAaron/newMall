package cn.coderap.service.impl;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.mapper.CategoryMapper;
import cn.coderap.pojo.Category;
import cn.coderap.service.ICategoryService;
import cn.coderap.vo.CategoryVO;
import cn.coderap.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yw
 * 2021/4/20
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public ResponseVO<List<CategoryVO>> categories() {
        List<Category> categories = categoryMapper.selectAll();
        //查询出所有parent_id=0的数据
//        List<CategoryVO> categoryVOList = new ArrayList<>();
//        //for循环写法
//        for (Category category : categories) {
//            if (category.getParentId().equals(NewMallConsts.ROOT_PARENT_ID)) {
//                CategoryVO categoryVO = category2categoryVO(category);
//                categoryVOList.add(categoryVO);
//            }
//        }

        //lambda表达式+stream流写法（阅读性挺差）: e就是category
        List<CategoryVO> categoryVOList = categories.stream().filter(e -> e.getParentId().equals(NewMallConsts.ROOT_PARENT_ID))
                .map(this::category2categoryVO).collect(Collectors.toList());

        return ResponseVO.success(categoryVOList);
    }

    private List<CategoryVO> findSubCategoriesById(Integer id) {
        List<CategoryVO> subCategoryVOList = new ArrayList<>();
        List<Category> subCategories = categoryMapper.selectByParentId(id);
        if (CollectionUtils.isEmpty(subCategories)) {
            return subCategoryVOList;
        }
        for (Category subCategory : subCategories) {
            CategoryVO subCategoryVO = category2categoryVO(subCategory);
            subCategoryVOList.add(subCategoryVO);
        }
        return subCategoryVOList;
    }

    private CategoryVO category2categoryVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        //多次查询的写法（不推荐）
        categoryVO.setSubCategories(findSubCategoriesById(categoryVO.getId()));
        return categoryVO;
    }
}
