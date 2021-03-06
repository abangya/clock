package com.deyi.clock.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockVo
 * @Description TODO
 * @createTime 2019年05月30日 15:16
 */
@Data
public class ClockVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer clockId;
    private String clockPhoto;
    private String clockCreateTime;
    private String clockIpAddress;
    private String clockName;
    private int id;
    private String realName;
    private int age;
    private String photo;
    private Integer clId;
    private Integer level;
    private String describe;
    private List<DimensionVo> dimensionVoList;

}
