/*
*
* Rolepermission.java
* @date 2019-06-03
*/
package com.deyi.clock.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {

    private Integer roleId;
    private Integer permissionId;

}