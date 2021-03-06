package com.deyi.clock.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "2018-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @ApiModelProperty(example = "2018-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    private Integer createUser;
    private Integer updateUser;
    private Integer status;
    private String registerIp;
    @ApiModelProperty(example = "2018-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;
    private String salt;
    private String tel;
    private String nickName;
}
