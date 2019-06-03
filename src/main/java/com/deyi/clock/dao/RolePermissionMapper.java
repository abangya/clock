/*
*
* RolepermissionMapper.java
* @date 2019-06-03
*/
package com.deyi.clock.dao;

import com.deyi.clock.domain.RolePermission;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionMapper {
    /**
     *
     * @mbg.generated 2019-06-03
     */
    int deleteByPrimaryKey(@Param("roleId") Integer roleid, @Param("permissionId") Integer permissionid);

    /**
     *
     * @mbg.generated 2019-06-03
     */
    int insert(RolePermission record);

    /**
     *
     * @mbg.generated 2019-06-03
     */
    int insertSelective(RolePermission record);
}