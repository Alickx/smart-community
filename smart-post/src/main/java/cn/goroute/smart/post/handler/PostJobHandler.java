package cn.goroute.smart.post.handler;

import cn.goroute.smart.post.service.ThumbService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostJobHandler{

    @Autowired
    ThumbService thumbService;

    @XxlJob("transThumbFromRedis2DHandler")
    public void transThumbFromRedis2DHandler() throws Exception {
        XxlJobHelper.log("=>调度任务:{} 开始执行点赞数据持久化",this.getClass().getName());
        int transSize = thumbService.trans();
        XxlJobHelper.log("=>调度任务结束，一共持久化{}条数据",transSize);
    }





}
