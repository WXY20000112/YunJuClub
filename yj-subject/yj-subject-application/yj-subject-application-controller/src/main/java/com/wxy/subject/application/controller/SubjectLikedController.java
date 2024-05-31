package com.wxy.subject.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.subject.application.converter.SubjectLikedDtoConverter;
import com.wxy.subject.application.dto.SubjectLikedDto;
import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.common.entity.Result;
import com.wxy.subject.common.utils.ThreadLocalUtil;
import com.wxy.subject.domain.entity.SubjectLikedBO;
import com.wxy.subject.domain.service.SubjectLikedDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedController
 * @author: 32115
 * @create: 2024-05-31 15:31
 */
@RestController
@RequestMapping("/subjectLiked")
@Slf4j
public class SubjectLikedController {

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    /**
     * @author: 32115
     * @description: 添加点赞信息
     * @date: 2024/5/31
     * @param: subjectLikedDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    @AopLogAnnotations
    public Result<Boolean> addSubjectLiked(@RequestBody SubjectLikedDto subjectLikedDto) {
        try {
            // 获取点赞用户
            String likeUserId = ThreadLocalUtil.getLoginId();
            // 设置点赞用户
            subjectLikedDto.setLikeUserId(likeUserId);
            // 参数校验
            Preconditions.checkNotNull(subjectLikedDto.getSubjectId(), "题目ID不能为空");
            Preconditions.checkNotNull(subjectLikedDto.getLikeUserId(), "点赞用户不能为空");
            Preconditions.checkNotNull(subjectLikedDto.getStatus(), "点赞状态不能为空");
            // Dto转BO
            SubjectLikedBO subjectLikedBO = SubjectLikedDtoConverter
                    .CONVERTER.converterDtoToBo(subjectLikedDto);
            // 调用业务层
            subjectLikedDomainService.addSubjectLiked(subjectLikedBO);
            return Result.success(true);
        } catch (Exception e) {
            log.error("SubjectLikedController.addSubjectLiked.SubjectLikedDto:{}", subjectLikedDto, e);
            return Result.error("点赞失败");
        }
    }
}
