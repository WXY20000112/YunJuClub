package com.wxy.subject.api.feign;

import com.wxy.subject.api.entity.SubjectCategoryDto;
import com.wxy.subject.api.entity.SubjectInfoDto;
import com.wxy.subject.api.entity.SubjectLabelDto;
import com.wxy.subject.api.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryFeignService
 * @author: 32115
 * @create: 2024-06-03 12:40
 */
@FeignClient("yj-subject")
public interface SubjectFeignService {

    @RequestMapping("/subject/category/getSecondCategoryBySubjectType")
    Result<List<SubjectCategoryDto>> getCategoryBySubjectType();

    @RequestMapping("/subject/category/getSubjectCategoryById")
    Result<SubjectCategoryDto> getCategoryById(@RequestParam("parentId") Long parentId);

    @RequestMapping("/subject/label/getLabelByCategoryId")
    Result<List<SubjectLabelDto>> getLabelByCategoryId(@RequestParam("categoryId") Long categoryId);

    @RequestMapping("/subject/getSubjectInfoList")
    Result<List<SubjectInfoDto>> getSubjectInfoList(@RequestBody SubjectInfoDto subjectInfoDto);

    @RequestMapping("/subject/label/getLabelById")
    Result<SubjectLabelDto> getLabelById(@RequestParam("id") Long id);

    @RequestMapping("/subject/getSubjectInfoById")
    Result<SubjectInfoDto> getSubjectInfoById(@RequestBody SubjectInfoDto subjectInfoDto);

    @RequestMapping("/subject/label/getLabelIdsBySubjectId")
    Result<List<Long>> getLabelBySubjectId(@RequestParam("subjectId") Long subjectId);
}
