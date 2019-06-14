package com.deyi.clock.domain.dto;

import com.deyi.clock.domain.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName UserDto
 * @Description TODO
 * @createTime 2019年06月14日 10:16
 */
@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private User user;
    private String roleId;
}
