/*
*
* UserLevelMapper.java
* @date 2019-06-11
*/
package com.deyi.clock.dao;


import com.deyi.clock.domain.UserLevel;
import org.apache.ibatis.annotations.Param;

public interface UserLevelMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int deleteByUserId(@Param("userId")Integer userId);

    int insertSelective(UserLevel record);

    UserLevel selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(UserLevel record);

}