/*
*
* Userrole.java
* @date 2019-06-03
*/
package com.deyi.clock.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRole implements Serializable {
    /**
     * 
     */
    private Integer userId;

    /**
     * 
     */
    private Integer roleId;

}