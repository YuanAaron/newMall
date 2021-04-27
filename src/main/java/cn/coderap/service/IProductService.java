package cn.coderap.service;

import cn.coderap.vo.ResponseVO;
import com.github.pagehelper.PageInfo;

public interface IProductService {

    ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);
}
