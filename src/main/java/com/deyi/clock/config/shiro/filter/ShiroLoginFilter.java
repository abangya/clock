package com.deyi.clock.config.shiro.filter;


import com.alibaba.fastjson.JSONObject;
import com.deyi.clock.config.core.Result;
import com.deyi.clock.config.core.ResultCode;
import com.deyi.clock.config.shiro.ResponseBean;
import com.deyi.clock.utils.log.LogUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyz
 * @version 1.0.0
 * @ClassName ShiroLoginFilter
 * @Description 登录失效过滤器
 * @createTime 2019年06月11日 11:18
 */
public class ShiroLoginFilter extends FormAuthenticationFilter{
    protected final Logger platformLogger = LogUtils.getPlatformLogger();
    /**
     * 在访问controller前判断是否登录，返回json，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isAjax(request)) {
            platformLogger.info("拦截ajax，登录认证失效");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            Result resultData = new Result();
            resultData.setCode(ResultCode.NOT_LOGIN);
            resultData.setMessage("登录认证失效，请重新登录!");
            //httpServletResponse.getWriter().write(JSONObject.toJSON(resultData).toString());
            httpServletResponse.sendError(403,"登录已失效");
        } else {
            //saveRequestAndRedirectToLogin(request, response);
           /**
             * @Mark 非ajax请求重定向为登录页面
             */
            platformLogger.info("登录认证失效");
            httpServletResponse.sendRedirect("/login");
        }
        return false;
    }

    private boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
