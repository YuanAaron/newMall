package cn.coderap.service;
import cn.coderap.pojo.vo.CategoryVO;
import cn.coderap.pojo.vo.ResponseVO;

import java.util.List;
import java.util.Set;

/**
 * Created by yw
 * 2021/4/22
 */
public interface ICategoryService {

    ResponseVO<List<CategoryVO>> categories();

    /**
     * 查询所有子分类的id
     * @param id
     * @param res
     */
    void findSubCategoryId(Integer id, Set<Integer> res);
}
