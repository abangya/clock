package com.deyi.clock.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName BaseDto
 * @Description TODO
 * @createTime 2019年05月29日 14:05
 */
@Data
public class BaseDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer startNum;
    private Integer size;
    private String startTime;
    private String endTime;
    private String searchTime;
    private String searchName;
    private String searchRoleStr;
    private Set searchRole;
}
