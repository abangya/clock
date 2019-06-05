package com.deyi.clock.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName DimensionVo
 * @Description TODO
 * @createTime 2019年05月30日 15:22
 */
@Data
public class DimensionVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer dId;
    private String startTime;
    private String endTime;
    private Integer parentId;
    private Integer sort;

}
