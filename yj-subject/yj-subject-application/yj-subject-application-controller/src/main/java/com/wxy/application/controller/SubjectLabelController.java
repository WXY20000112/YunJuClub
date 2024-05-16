package com.wxy.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.application.converter.SubjectLabelDtoConverter;
import com.wxy.application.dto.SubjectLabelDto;
import com.wxy.subject.common.entity.Result;
import com.wxy.subject.domain.entity.SubjectLabelBO;
import com.wxy.subject.domain.service.SubjectLabelDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: 分类标签controller
 * @author: 32115
 * @create: 2024-05-16 14:45
 */
@RestController
@RequestMapping("/subject/label")
@Slf4j
public class SubjectLabelController {

    @Resource
    private SubjectLabelDomainService subjectLabelDomainService;

    /**
     * @author: 32115
     * @description: 新增分类标签
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    public Result<Boolean> addLabel(@RequestBody SubjectLabelDto subjectLabelDto) {
        log.info("SubjectLabelController.addLabel.SubjectLabelDto:{}", subjectLabelDto);
        // 参数校验
        Preconditions.checkNotNull(subjectLabelDto.getLabelName(), "标签名称不能为空");
        // Dto 转 BO
        SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                .CONVERTER.converterDtoToBo(subjectLabelDto);
        // 调用业务层
        return subjectLabelDomainService
                .addLabel(subjectLabelBO) ? Result.success() : Result.error();
    }

    /**
     * @author: 32115
     * @description: 删除分类标签 逻辑删除
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/delete")
    public Result<Boolean> deleteLabel(@RequestBody SubjectLabelDto subjectLabelDto) {
        // 参数校验
        Preconditions.checkNotNull(subjectLabelDto.getId(), "标签id不能为空");
        // Dto 转 BO
        SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                .CONVERTER.converterDtoToBo(subjectLabelDto);
        // 调用业务层
        return subjectLabelDomainService
                .deleteLabel(subjectLabelBO) ? Result.success() : Result.error();
    }

    /**
     * @author: 32115
     * @description: 修改分类标签
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    public Result<Boolean> updateLabel(@RequestBody SubjectLabelDto subjectLabelDto) {
        // 参数校验
        Preconditions.checkNotNull(subjectLabelDto.getId(), "标签id不能为空");
        // Dto 转 BO
        SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                .CONVERTER.converterDtoToBo(subjectLabelDto);
        // 调用业务层
        return subjectLabelDomainService
                .updateLabel(subjectLabelBO) ? Result.success() : Result.error();
    }

    /**
     * @author: 32115
     * @description: 根据分类id查询分类标签
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<List < SubjectLabelDto>>
     */
    @RequestMapping("/queryLabelByCategoryId")
    public Result<List<SubjectLabelDto>> getLabelByCategoryId(@RequestBody SubjectLabelDto subjectLabelDto) {
        log.info("SubjectLabelController.getLabelByCategoryId.SubjectLabelDto:{}", subjectLabelDto);
        // 参数校验
        Preconditions.checkNotNull(subjectLabelDto.getCategoryId(), "分类id不能为空");
        // Dto 转 BO
        SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                .CONVERTER.converterDtoToBo(subjectLabelDto);
        // 调用业务层
        List<SubjectLabelBO> subjectLabelBOList = subjectLabelDomainService
                .getLabelByCategoryId(subjectLabelBO);
        // BO 转 Dto
        List<SubjectLabelDto> subjectLabelDtoList = SubjectLabelDtoConverter
                .CONVERTER.converterBoToDto(subjectLabelBOList);
        // 返回结果
        return Result.success(subjectLabelDtoList);
    }
}
