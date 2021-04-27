package cn.coderap.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by yw
 * 2021/4/22
 */
@Data
public class CategoryVO {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sortOrder;
    private List<CategoryVO> subCategories;
}
