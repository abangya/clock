package com.deyi.clock.domain.dto;

import lombok.Data;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName BaseDto
 * @Description TODO
 * @createTime 2019年05月29日 14:05
 */
@Data
public class BaseDto{
    private Integer pageNum;
    private Integer pageSize;
    private String startTime;
    private String endTime;
    private String searchTime;
}
