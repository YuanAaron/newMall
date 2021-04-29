package cn.coderap.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 添加到redis购物车中的内容
 * 只保留以下三个字段，其他根据productId从数据库中查，这样可以获取商品数据的最新值
 * Created by yw
 * 2021/4/27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRedisVO {
    private Integer productId;
    private Integer quantity;
    private Boolean selected;
}
