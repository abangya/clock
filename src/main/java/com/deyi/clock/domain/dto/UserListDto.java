package com.deyi.clock.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserListDto
 * @Description TODO
 * @createTime 2019年06月05日 14:24
 */
@Data
public class UserListDto extends BaseDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
}
