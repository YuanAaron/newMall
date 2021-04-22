package cn.coderap.mapper;

import cn.coderap.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer countByUsername(String username);

    Integer countByEmail(String email);

    User selectByUsername(String username);
}