package cn.fenrana.blog.event;


import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.entity.enums.LogType;
import cn.fenrana.blog.utils.IpUtils;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


public class LogEvent extends ApplicationEvent {

    private Log log;

    public LogEvent(Object source, Log log, HttpServletRequest request) {
        super(source);
        log.setUsername("Fenrana");
        String ipAddr = IpUtils.getIpAddr(request);
        log.setIp(ipAddr);
        log.setCreateTime(LocalDateTime.now());
        this.log = log;
    }

    public LogEvent(Object source, HttpServletRequest request, String operation, String method) {
        this(source, new Log(operation, method), request);
    }

    public LogEvent(Object source, HttpServletRequest request, String operation, String method, String params) {
        this(source, new Log(operation, method, params), request);
    }

    public Log getLog() {
        return log;
    }
}
