package com.deyi.clock.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName Clock
 * @Description 打卡记录表
 * @createTime 2019年05月28日 17:49
 */
@Data
public class Clock {
    private String id;
    private String UserName;
    private Date createTime;
    private Integer userId;
    private String ipAddress;
}
