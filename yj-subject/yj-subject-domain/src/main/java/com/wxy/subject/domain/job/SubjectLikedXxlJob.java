package com.wxy.subject.domain.job;

import com.wxy.subject.domain.service.SubjectLikedDomainService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: 32115
 * @description: SubjectLiked服务任务调度
 * @date: 2024/5/31
 */
@Component
@Slf4j
public class SubjectLikedXxlJob {

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;


    /**
     * @author: 32115
     * @description: 同分布点赞数到数据库
     * @date: 2024/5/31
     * @return: void
     */
    @XxlJob("subjectLikedJobHandler")
    public void subjectLikedJobHandler() {
        try {
            log.info("点赞信息同步任务开始执行——————————————————————————");
            // 调用domain层方法执行定时任务
            subjectLikedDomainService.syncSubjectLiked();
        } catch (Exception e){
            XxlJobHelper.log("subjectLikedJobHandler.error" + e.getMessage());
        }
    }

}
