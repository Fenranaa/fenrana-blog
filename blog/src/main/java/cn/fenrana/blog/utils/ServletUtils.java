package cn.fenrana.blog.utils;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentInfo;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ServletUtils {
    private ServletUtils() {
    }

    @NonNull
    public static Optional<HttpServletRequest> getCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest);
    }

    /**
     * 获取请求的IP地址
     */
    @Nullable
    public static String getRequestIp() {
        return getCurrentRequest().map(ServletUtil::getClientIP).orElse(null);
    }

    /**
     * 获得UserAgent对象，hutool工具封装的
     */
    private static UserAgent getUserAgent() {
        Optional<HttpServletRequest> currentRequest = getCurrentRequest();
        HttpServletRequest httpServletRequest = currentRequest.get();
        String header = httpServletRequest.getHeader("User-Agent");
        return UserAgentUtil.parse(header);

    }

    /**
     * 获取的请求的平台
     */
    public static String getRequestDrive() {
        UserAgent userAgent = getUserAgent();
        Platform platform = userAgent.getPlatform();
        return platform.getName();
    }

    /**
     * 获取浏览器
     */
    public static String getBrowserName() {
        return getUserAgent().getBrowser().getName();
    }
    /**
     *
     * 获取浏览器的版本
     * */
    public static String getBrowserVersion() {
        return getUserAgent().getVersion();
    }


}
