package cn.coderap.mapper;

import cn.coderap.pojo.Product;
import cn.coderap.pojo.vo.CartRedisVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> selectByCategoryIdSet(@Param("categoryIdSet") Set<Integer> categoryIdSet); //这里的@Param不能少

    List<Product> selectByCartList(@Param("cartList") List<CartRedisVO> cartList);

    List<Product> selectByProductIdSet(@Param("productIdSet") Set<Integer> productIdSet);
}