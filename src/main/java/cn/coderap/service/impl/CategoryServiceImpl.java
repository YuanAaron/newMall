package cn.coderap.service.impl;

import cn.coderap.consts.NewMallConsts;
import cn.coderap.mapper.CategoryMapper;
import cn.coderap.pojo.Category;
import cn.coderap.service.ICategoryService;
import cn.coderap.pojo.vo.CategoryVO;
import cn.coderap.pojo.vo.ResponseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

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
        List<CategoryVO> categoryVOList = new ArrayList<>();
        //for循环写法
        for (Category category : categories) {
            if (category.getParentId().equals(NewMallConsts.ROOT_PARENT_ID)) {
                CategoryVO categoryVO = category2categoryVO(category);
                categoryVOList.add(categoryVO);
            }
        }
        categoryVOList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());

        //lambda表达式+stream流写法（阅读性挺差）: e就是category
//        List<CategoryVO> categoryVOList = categories.stream().filter(e -> e.getParentId().equals(NewMallConsts.ROOT_PARENT_ID))
//                .map(this::category2categoryVO).sorted(Comparator.comparing(CategoryVO::getSortOrder).reversed())
//                .collect(Collectors.toList());

        return ResponseVO.success(categoryVOList);
    }

    private void findSubCategories(List<CategoryVO> categoryVOList,List<Category> categories) {
        for (CategoryVO categoryVO : categoryVOList) {
            List<CategoryVO> subCategoryVOList = new ArrayList<>();
            for (Category category : categories) {
                if (categoryVO.getId().equals(category.getParentId())) {
                    CategoryVO subCategoryVO = category2categoryVO(category);
                    subCategoryVOList.add(subCategoryVO);
                }
                //老师都放在了这里
//                subCategoryVOList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed()); //默认从小到大，取反即可
//                categoryVO.setSubCategories(subCategoryVOList);//容易忘记
//                findSubCategories(subCategoryVOList, categories);
            }
            subCategoryVOList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed()); //默认从小到大，取反即可
            categoryVO.setSubCategories(subCategoryVOList);//容易忘记
            findSubCategories(subCategoryVOList, categories);
        }
    }

    private CategoryVO category2categoryVO(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> res) {
        List<Category> categories = categoryMapper.selectAll();
        //避免递归时多次查询数据库
        findSubCategoryId(id, res, categories);
    }

    public void findSubCategoryId(Integer id, Set<Integer> res,List<Category> categories) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)) {
                res.add(category.getId());
                findSubCategoryId(category.getId(), res,categories);
            }
        }
    }
}
