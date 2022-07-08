package com.lzk.originalusercommon.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzk.originalusercommon.model.LoginUser;
import com.lzk.originalusercommon.util.ServletPathMatcher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @module
 * @date 2021/6/5 16:43
 */
public class TokenFilterRegistrationBean extends FilterRegistrationBean<Filter> {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    private static final ThreadLocal<LoginUser> THREAD_LOCAL = new ThreadLocal<>();

    private RedisTemplate<String,Object> redisTemplate;

    private final String EXCLUSIONS_NAME = "exclusions";

    public TokenFilterRegistrationBean(RedisTemplate<String,Object> redisTemplate,Integer order,List<String> exclusions,String... urlPatterns){
        this.redisTemplate = redisTemplate;
        init(order,exclusions,urlPatterns);
    }

    public void init(Integer order,List<String> exclusions,String... urlPatterns) {
        setFilter(new TokenFilter());
        addInitParameter(EXCLUSIONS_NAME, JSON.toJSONString(exclusions));
        addUrlPatterns(urlPatterns);
        setOrder(order);
    }

    class TokenFilter implements Filter {

        private List<String> exclusionsParam;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            String parameter = filterConfig.getInitParameter(EXCLUSIONS_NAME);
            LOG.info("exclusions:{}", parameter);
            if (StringUtils.isNotBlank(parameter)) exclusionsParam = JSON.parseArray(parameter,String.class);
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String uri = request.getRequestURI();
            LOG.info("请求URI:{}", "  " + uri);
            if (isExclusions(uri)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String header = request.getHeader("Authorization");
                if (StringUtils.isNotBlank(header) && checkToken(header))
                    filterChain.doFilter(servletRequest, servletResponse);
                returnJSON(response, 1000, "登录已过期");
            }
        }

        private boolean isExclusions(String uri) {
            if (exclusionsParam == null) return false;
            ListIterator<String> iterator = exclusionsParam.listIterator();
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (ServletPathMatcher.getInstance().matches(s, uri)) {
                    return true;
                }
            }
            return false;
        }

        private boolean checkToken(String token) {
            if (!redisTemplate.hasKey(token)) return false;
            String s = (String) redisTemplate.opsForValue().get(token);
            THREAD_LOCAL.set(JSONObject.parseObject(s,LoginUser.class));
            redisTemplate.expire(token, 2, TimeUnit.HOURS);
            return true;
        }

    }

    private void returnJSON(HttpServletResponse response, Integer code, String msg) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("msg", msg);
        String jsonString = jsonObject.toJSONString();
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
}
