package com.deyi.clock.domain.vo;

import lombok.Data;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName DimensionVo
 * @Description TODO
 * @createTime 2019年05月30日 15:22
 */
@Data
public class DimensionVo {

    private Integer dId;
    private String startTime;
    private String endTime;
    private Integer parentId;
    private Integer sort;

}
