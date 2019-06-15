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

    private Integer state;

    private String role;

    private String description;

    private Integer sort;
}