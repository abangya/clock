package com.deyi.clock.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName User
 * @Description TODO
 * @createTime 2019年05月22日 16:52
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userName;
    private Integer age;
    private String photo;
    private String password;
    private String realName;
    private String headImg;
    private String phone;
    private Integer gender;
    private Date createTime;
    private Date updateTime;
    private Integer createUser;
    private Integer updateUser;
    private Integer status;
    private String registerIp;
    private Date lastLoginTime;
    private String salt;
    private String tel;
    private String nickName;
}
