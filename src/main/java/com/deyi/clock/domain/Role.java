/*
*
* Role.java
* @date 2019-06-03
*/
package com.deyi.clock.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {

    private Integer id;

    /**
     * 0可用1不可用
     */
    private Integer state;

    /**
     * 角色标识程序中判断使用,如"admin"
     */
    private String role;

    /**
     * 角色描述,UI界面显示使用
     */
    private String description;

    private Integer sort;
}