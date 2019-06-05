package com.deyi.clock.domain.dto;


import lombok.Data;

/**
 * @title 登录dto
 * @description 
 * @author lyz 
 * @updateTime 2019/5/28 0028 13:48 
 * @throws 
 */
@Data
public class LoginDTO {

    private static final long serialVersionUID = 1L;

    private String userName;

    private String password;

    private boolean rememberMe;

}
