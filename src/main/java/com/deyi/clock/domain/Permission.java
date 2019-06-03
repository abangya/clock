/*
*
* Permission.java
* @date 2019-06-03
*/
package com.deyi.clock.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {

    private Integer id;

    /**
     * 权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /**
     *  资源类型，[menu|button]
     */
    private String resourceType;

    /**
     * 资源路径 如：/userinfo/list
     */
    private String url;

    /**
     * 权限名称
     */
    private String name;

    /**
     *  是否可用0可用  1不可用
     */
    private Integer state;

}