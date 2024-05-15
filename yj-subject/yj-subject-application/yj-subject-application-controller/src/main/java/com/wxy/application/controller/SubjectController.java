package com.wxy.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.application.converter.SubjectInfoDtoConverter;
import com.wxy.application.converter.SubjectOptionDtoConverter;
import com.wxy.application.dto.SubjectInfoDto;
import com.wxy.subject.common.entity.Result;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.domain.service.SubjectInfoDomainService;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: 题目相关功能控制器
 * @author: 32115
 * @create: 2024-05-15 16:14
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;

    /**
     * @author: 32115
     * @description: 添加题目
     * @date: 2024/5/15
     * @param: subjectInfoDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    public Result<Boolean> addSubjectInfo(@RequestBody SubjectInfoDto subjectInfoDto) {

            // 参数校验
            Preconditions.checkNotNull(subjectInfoDto.getSubjectName(), "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDto.getSubjectDifficult(), "题目难易度不能为空");
            Preconditions.checkNotNull(subjectInfoDto.getSubjectType(), "题目类型不能为空");
            Preconditions.checkNotNull(subjectInfoDto.getSubjectScore(), "题目分数不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDto
                    .getCategoryIds()), "题目所属分类id不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDto
                    .getLabelIds()), "题目所属标签id不能为空");
            // SubjectInfoDto 转换为 SubjectInfoBO
            SubjectInfoBO subjectInfoBO = SubjectInfoDtoConverter
                    .CONVERTER.converterDtoToBo(subjectInfoDto);
            // SubjectOptionsDto 转换为 SubjectOptionsBO
            subjectInfoBO.setOptionBoList(SubjectOptionDtoConverter
                    .CONVERTER.converterDtoToBo(subjectInfoDto.getOptionList()));
            // 添加题目
            return subjectInfoDomainService
                    .addSubject(subjectInfoBO) ? Result.success(true) : Result.error();

    }
}
