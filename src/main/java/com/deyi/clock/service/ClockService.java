package com.deyi.clock.service;

import com.deyi.clock.domain.dto.ClockDto;
import com.deyi.clock.domain.vo.ClockVo;
import java.util.List;
import java.util.Map;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ClockService
 * @Description TODO
 * @createTime 2019年05月28日 17:52
 */
public interface ClockService {

    List<ClockVo> clockAllUser(ClockDto clockDto);

    List<Map<String,Object>> userClock(ClockDto clockDto);

    List<Map<String,Object>> levelClock(Integer userId);
}
