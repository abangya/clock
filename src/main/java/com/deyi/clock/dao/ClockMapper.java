package com.deyi.clock.dao;

import com.deyi.clock.domain.Clock;
import com.deyi.clock.domain.dto.ClockDto;
import com.deyi.clock.domain.vo.ClockVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockMapper
 * @Description TODO
 * @createTime 2019年05月28日 17:51
 */
public interface ClockMapper {

    /**
     * @title clockAllUser
     * @description 全部记录人员
     * @author lyz
     * @param: clockDto
     * @updateTime 2019/5/29 0029 20:22
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws
     */
    List<ClockVo> clockAllUser(ClockDto clockDto);

    /**
     * @title clockAllUserCount
     * @description 分页总数
     * @author lyz
     * @param: clockDto
     * @updateTime 2019/6/5 0005 15:10
     * @return: java.lang.Integer
     * @throws
     */
    Integer clockAllUserCount(ClockDto clockDto);
    
    /**
     * @title userClock
     * @description 个人打卡记录
     * @author lyz
     * @param: clockDto
     * @updateTime 2019/5/30 0030 10:13 
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @throws 
     */
    List<Map<String,Object>> userClock(ClockDto clockDto);

    List<Map<String,Object>> levelClock(@Param("userId")Integer userId);

}
