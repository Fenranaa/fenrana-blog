package cn.fenrana.blog.listener.logger;


import cn.fenrana.blog.entity.Log;
import cn.fenrana.blog.entity.enums.LogType;
import cn.fenrana.blog.event.LogEvent;
import cn.fenrana.blog.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class LogEventListener {

    @Autowired
    private  LogMapper logMapper;


    @Async
    @EventListener
    public void onApplicationEvent(LogEvent logEvent) {
        //
        System.out.println("测试监听");
        System.out.println(logEvent.getLog());
        Log log = logEvent.getLog();
        logMapper.insert(log);
    }

}
