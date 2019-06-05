package com.deyi.clock.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName RolePermissionVo
 * @Description TODO
 * @createTime 2019年06月05日 10:30
 */
@Data
public class RolePermissionVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer permissionId;
    private String permissionName;

}
