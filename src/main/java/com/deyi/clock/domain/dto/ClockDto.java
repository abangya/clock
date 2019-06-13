package com.deyi.clock.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockDto
 * @Description TODO
 * @createTime 2019年05月29日 13:57
 */
@Data
public class ClockDto extends BaseDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String realName;

}
