package com.deyi.clock.domain.vo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName RoleVo
 * @Description TODO
 * @createTime 2019年06月05日 10:28
 */
@Data
public class UserRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String description;
    private List<RolePermissionVo> rolePermissionVoList;

}
