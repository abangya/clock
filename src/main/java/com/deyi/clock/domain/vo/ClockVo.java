package com.deyi.clock.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockVo
 * @Description TODO
 * @createTime 2019年05月30日 15:16
 */
@Data
public class ClockVo {

    private Integer clockId;
    private String clockPhoto;
    private String clockCreateTime;
    private String clockIpAddress;
    private String clockName;
    private int id;
    private String userName;
    private int age;
    private String photo;
    private Integer clId;
    private Integer level;
    private List<DimensionVo> dimensionVoList;
}
