package com.wxy.subject.application.controller;

import com.google.common.base.Preconditions;
import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.application.converter.SubjectInfoDtoConverter;
import com.wxy.subject.application.converter.SubjectOptionDtoConverter;
import com.wxy.subject.application.dto.SubjectInfoDto;
import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.common.entity.Result;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.domain.service.SubjectInfoDomainService;
import com.wxy.subject.infra.es.entity.SubjectInfoElasticsearch;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: 题目相关功能控制器
 * @author: 32115
 * @create: 2024-05-15 16:14
 */
@RestController
@RequestMapping("/subject")
@Slf4j
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
    @AopLogAnnotations
    public Result<Boolean> addSubjectInfo(@RequestBody SubjectInfoDto subjectInfoDto) {
        try {
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
            subjectInfoBO.setOptionList(SubjectOptionDtoConverter
                    .CONVERTER.converterDtoToBo(subjectInfoDto.getOptionList()));
            // 添加题目
            return subjectInfoDomainService
                    .addSubject(subjectInfoBO) ? Result.success(true) : Result.error();
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 分页查询题目列表
     * @date: 2024/5/16
     * @param: subjectInfoDto
     * @return: Result<Page < SubjectInfoDto>>
     */
    @RequestMapping("/getSubjectPage")
    @AopLogAnnotations
    public Result<Page<SubjectInfoDto>> getSubjectPageList(@RequestBody SubjectInfoDto subjectInfoDto) {
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectInfoDto.getCategoryId(), "分类id不能为空");
            Preconditions.checkNotNull(subjectInfoDto.getLabelId(), "标签id不能为空");
            // DTO 转换 BO
            SubjectInfoBO subjectInfoBO = SubjectInfoDtoConverter
                    .CONVERTER.converterDtoToBo(subjectInfoDto);
            // 查询题目列表
            Page<SubjectInfoBO> subjectInfoBOPage = subjectInfoDomainService
                    .getSubjectPageList(subjectInfoBO);
            // BO 转换 DTO
            Page<SubjectInfoDto> subjectInfoDtoPage = SubjectInfoDtoConverter
                    .CONVERTER.converterBoPageToDtoPage(subjectInfoBOPage);
            return Result.success(subjectInfoDtoPage);
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 查询题目详情
     * @date: 2024/5/16
     * @param: subjectInfoDto
     * @return: Result<SubjectInfoDto>
     */
    @RequestMapping("/querySubjectInfo")
    @AopLogAnnotations
    public Result<SubjectInfoDto> getSubjectInfo(@RequestBody SubjectInfoDto subjectInfoDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectInfoDto.getId(), "id不能为空");
            // DTO 转换 BO
            SubjectInfoBO subjectInfoBO = SubjectInfoDtoConverter
                    .CONVERTER.converterDtoToBo(subjectInfoDto);
            // 调用domain层service进行查询
            SubjectInfoBO boResult = subjectInfoDomainService.getSubjectInfo(subjectInfoBO);
            // BO 转换 DTO
            SubjectInfoDto dtoResult = SubjectInfoDtoConverter
                    .CONVERTER.converterBoToDto(boResult);
            // 返回结果
            return Result.success(dtoResult);
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 全文检索查询
     * @date: 2024/5/30
     * @param: subjectInfoDto
     * @return: Result<Page < SubjectInfoElasticsearch>>
     */
    @RequestMapping("/getSubjectPageBySearch")
    @AopLogAnnotations
    public Result<Page<SubjectInfoElasticsearch>> getSubjectPageByElasticsearch(@RequestBody SubjectInfoDto subjectInfoDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectInfoDto.getKeyWord(), "关键字不能为空");
            // DTO 转换 BO
            SubjectInfoBO subjectInfoBO = SubjectInfoDtoConverter
                    .CONVERTER.converterDtoToBo(subjectInfoDto);
            // 调用domain层service进行查询
            Page<SubjectInfoElasticsearch> subjectInfoElasticsearchPage = subjectInfoDomainService
                    .getSubjectPageByElasticsearch(subjectInfoBO);
            // 返回结果
            return Result.success(subjectInfoElasticsearchPage);
        } catch (Exception e) {
            log.error("Exception:", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 获取题目贡献榜
     * @date: 2024/5/31
     * @return: Result<SubjectInfoDto>
     */
    @RequestMapping("/getContributeList")
    @AopLogAnnotations
    public Result<List<SubjectInfoDto>> getContributeList(){
        try {
            // 调用domain层service进行查询
            List<SubjectInfoBO> boList = subjectInfoDomainService.getContributeList();
            // BO 转换 DTO
            List<SubjectInfoDto> dtoList = SubjectInfoDtoConverter
                    .CONVERTER.converterBoListToDtoList(boList);
            // 返回结果
            return Result.success(dtoList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.getContributeList.error:{}", e.getMessage(), e);
            return Result.error("获取贡献榜失败");
        }
    }
}
