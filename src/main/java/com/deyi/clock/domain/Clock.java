package com.deyi.clock.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName Clock
 * @Description 打卡记录表
 * @createTime 2019年05月28日 17:49
 */
@Data
public class Clock implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String UserName;
    @ApiModelProperty(example = "2018-10-10 10:10:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer userId;
    private String ipAddress;

}
