package com.deyi.clock.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserVo
 * @Description TODO
 * @createTime 2019年06月05日 10:24
 */
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userName;
    private Integer age;
    private String photo;
    private String realName;
    private String headImg;
    private String phone;
    private Integer gender;
    @ApiModelProperty(example = "2018-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer status;
    private String registerIp;
    @ApiModelProperty(example = "2018-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;
    private String tel;
    private String nickName;
    private List<UserRoleVo> userRoleVoList;

}
