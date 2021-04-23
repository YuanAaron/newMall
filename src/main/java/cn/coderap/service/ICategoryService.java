package cn.coderap.service;

import cn.coderap.vo.CategoryVO;
import cn.coderap.vo.ResponseVO;

import java.util.List;

/**
 * Created by yw
 * 2021/4/22
 */
public interface ICategoryService {

    ResponseVO<List<CategoryVO>> categories();
}
