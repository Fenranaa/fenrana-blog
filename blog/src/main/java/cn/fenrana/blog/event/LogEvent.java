package cn.fenrana.blog.event;


import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.utils.ServletUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;


public class LogEvent extends ApplicationEvent {

    private Log log;

    public LogEvent(Object source, Log log) {
        super(source);
        log.setUsername("Fenrana");
        String requestIp = ServletUtils.getRequestIp();
        log.setIp(requestIp);
        log.setBrowserName(ServletUtils.getBrowserName());
        log.setDevice(ServletUtils.getRequestDrive());
        log.setBrowserVersion(ServletUtils.getBrowserVersion());
        log.setCreateTime(Instant.now().toEpochMilli());
        this.log = log;
    }

    public LogEvent(Object source, String operation, String method) {
        this(source, new Log(operation, method));
    }

    public LogEvent(Object source, String operation, String method, String params) {
        this(source, new Log(operation, method, params));
    }

    public Log getLog() {
        return log;
    }
}
