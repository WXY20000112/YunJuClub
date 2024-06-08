package com.wxy.subject.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.subject.application.converter.SubjectLabelDtoConverter;
import com.wxy.subject.application.dto.SubjectLabelDto;
import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.common.entity.Result;
import com.wxy.subject.domain.entity.SubjectLabelBO;
import com.wxy.subject.domain.service.SubjectLabelDomainService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @AopLogAnnotations
    public Result<Boolean> addLabel(@RequestBody SubjectLabelDto subjectLabelDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectLabelDto.getLabelName(), "标签名称不能为空");
            // Dto 转 BO
            SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                    .CONVERTER.converterDtoToBo(subjectLabelDto);
            // 调用业务层
            return subjectLabelDomainService
                    .addLabel(subjectLabelBO) ? Result.success() : Result.error();
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 删除分类标签 逻辑删除
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/delete")
    @AopLogAnnotations
    public Result<Boolean> deleteLabel(@RequestBody SubjectLabelDto subjectLabelDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectLabelDto.getId(), "标签id不能为空");
            // Dto 转 BO
            SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                    .CONVERTER.converterDtoToBo(subjectLabelDto);
            // 调用业务层
            return subjectLabelDomainService
                    .deleteLabel(subjectLabelBO) ? Result.success() : Result.error();
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 修改分类标签
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    @AopLogAnnotations
    public Result<Boolean> updateLabel(@RequestBody SubjectLabelDto subjectLabelDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectLabelDto.getId(), "标签id不能为空");
            // Dto 转 BO
            SubjectLabelBO subjectLabelBO = SubjectLabelDtoConverter
                    .CONVERTER.converterDtoToBo(subjectLabelDto);
            // 调用业务层
            return subjectLabelDomainService
                    .updateLabel(subjectLabelBO) ? Result.success() : Result.error();
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 根据分类id查询分类标签
     * @date: 2024/5/16
     * @param: subjectLabelDto
     * @return: Result<List < SubjectLabelDto>>
     */
    @RequestMapping("/queryLabelByCategoryId")
    @AopLogAnnotations
    public Result<List<SubjectLabelDto>> getLabelByCategoryId(@RequestBody SubjectLabelDto subjectLabelDto) {
        try {
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
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: feign远程调用 根据分类id和题目类型查询标签
     * @date: 2024/6/3
     * @param: categoryId
     * @return: Result<List < SubjectLabelDto>>
     */
    @RequestMapping("/getLabelByCategoryId")
    @AopLogAnnotations
    public Result<List<SubjectLabelDto>> getLabelByCategoryId(@RequestParam("categoryId") Long categoryId){
        try {
            // 参数校验
            Preconditions.checkNotNull(categoryId, "分类id不能为空");
            // 调用业务层
            List<SubjectLabelBO> subjectLabelBOList =
                    subjectLabelDomainService.getLabelByCategoryIdAndSubjectType(categoryId);
            // BO 转 Dto
            List<SubjectLabelDto> subjectLabelDtoList = SubjectLabelDtoConverter
                    .CONVERTER.converterBoToDto(subjectLabelBOList);
            // 返回结果
            return Result.success(subjectLabelDtoList);
        } catch (Exception e) {
            log.error("FeignClient.getLabelByCategoryId.error:{}:", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 根据标签id查询标签
     * @date: 2024/6/5
     * @param: id
     * @return: Result<SubjectLabelDto>
     */
    @RequestMapping("/getLabelById")
    @AopLogAnnotations
    Result<SubjectLabelDto> getLabelById(@RequestParam("id") Long id){
        try {
            // 调用业务层
            SubjectLabelBO subjectLabelBO = subjectLabelDomainService.getLabelById(id);
            // BO 转 Dto
            return Result.success(SubjectLabelDtoConverter
                    .CONVERTER.converterLabelBoToLabelDto(subjectLabelBO));
        } catch (Exception e) {
            log.error("FeignClient.getLabelById.error:{}:", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: feign调用 根据题目id查询标签id
     * @date: 2024/6/8
     * @param: subjectId
     * @return: Result<List < Long>>
     */
    @RequestMapping("/getLabelIdsBySubjectId")
    @AopLogAnnotations
    Result<List<Long>> getLabelBySubjectId(@RequestParam("subjectId") Long subjectId){
        try {
            // 调用业务层
            return Result.success(subjectLabelDomainService.getLabelIdsBySubjectId(subjectId));
        } catch (Exception e) {
            log.error("FeignClient.getLabelBySubjectId.error:{}:", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
