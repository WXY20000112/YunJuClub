package com.wxy.application.controller;

import com.google.common.base.Preconditions;
import com.wxy.application.converter.SubjectCategoryDtoConverter;
import com.wxy.application.converter.SubjectLabelDtoConverter;
import com.wxy.application.dto.SubjectCategoryDto;
import com.wxy.application.dto.SubjectLabelDto;
import com.wxy.subject.common.entity.Result;
import com.wxy.subject.domain.entity.SubjectCategoryBO;
import com.wxy.subject.domain.service.SubjectCategoryDomainService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: YunJuClub-Flex
 * @description: 题目分类控制层
 * @author: 32115
 * @create: 2024-05-13 14:01
 */
@RestController
@RequestMapping("/subject/category")
public class SubjectCategoryController {

    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * @author: 32115
     * @description: 新增分类
     * @date: 2024/5/14
     * @param: subjectCategoryDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectCategoryDto subjectCategoryDto) {
        try {
            // 使用guava做参数校验
            Preconditions.checkNotNull(
                    subjectCategoryDto.getCategoryType(),"分类类型不能为空");
            Preconditions.checkNotNull(
                    subjectCategoryDto.getParentId(),"分类所属父类id不能为空");
            Preconditions.checkNotNull(
                    subjectCategoryDto.getCategoryName(),"分类名称不能为空");
            // 调用SubjectCategoryDto将SubjectCategoryDto转化成SubjectCategoryBO
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryDtoToBo(subjectCategoryDto);
            // 调用业务层新增分类
            return subjectCategoryDomainService
                    .add(subjectCategoryBO) ? Result.success(true) : Result.error();
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 查询一级分类
     * @date: 2024/5/14 
     * @param: subjectCategoryDto
     * @return: Result<List < SubjectCategoryDto>>
     */
    @RequestMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDto>> queryPrimaryCategory(@RequestBody SubjectCategoryDto subjectCategoryDto) {
        try {
            // dto转bo
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryDtoToBo(subjectCategoryDto);
            // 调用业务层查询一级分类
            List<SubjectCategoryBO> subjectCategoryBOList =
                    subjectCategoryDomainService.queryPrimaryCategory(subjectCategoryBO);
            // 将SubjectCategoryBO转化为SubjectCategoryDto并返回
            List<SubjectCategoryDto> subjectCategoryDtoList = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryBOToDto(subjectCategoryBOList);
            return Result.success(subjectCategoryDtoList);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 查询二级分类
     * @date: 2024/5/14
     * @param: subjectCategoryDto
     * @return: Result<List < SubjectCategoryDto>>
     */
    @RequestMapping("/queryCategoryByPrimary")
    public Result<List<SubjectCategoryDto>> queryCategoryByPrimary(@RequestBody SubjectCategoryDto subjectCategoryDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectCategoryDto.getParentId(), "分类所属父类id不能为空");
            // dto转bo
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryDtoToBo(subjectCategoryDto);
            // 调用业务层查询一级分类
            List<SubjectCategoryBO> subjectCategoryBOList =
                    subjectCategoryDomainService.queryPrimaryCategory(subjectCategoryBO);
            // 将SubjectCategoryBO转化为SubjectCategoryDto并返回
            List<SubjectCategoryDto> subjectCategoryDtoList = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryBOToDto(subjectCategoryBOList);
            return Result.success(subjectCategoryDtoList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 根据分类id查询分类和标签
     * @date: 2024/5/15
     * @param: subjectCategoryDto
     * @return: Result<List < SubjectCategoryDto>>
     */
    @RequestMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDto>> getLabelByPrimary(@RequestBody SubjectCategoryDto subjectCategoryDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectCategoryDto.getId(), "分类id不能为空");
            // dto转bo
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryDtoToBo(subjectCategoryDto);
            // 调用业务层查询一级分类
            List<SubjectCategoryBO> subjectCategoryBOList =
                    subjectCategoryDomainService.getLabelByCategoryId(subjectCategoryBO);
            // 使用stream流将boLabelList转化为dtoLabelList
            List<SubjectCategoryDto> subjectCategoryDtoList = subjectCategoryBOList.stream().map(categoryBO -> {
                // 将SubjectCategoryBO转化为SubjectCategoryDto并返回
                SubjectCategoryDto categoryDto = SubjectCategoryDtoConverter
                        .CONVERTER.convertCategoryBoToDto(categoryBO);
                // 将boLabelList转化为dtoLabelList
                List<SubjectLabelDto> subjectLabelDtoList = SubjectLabelDtoConverter
                        .CONVERTER.converterBoToDto(categoryBO.getSubjectLabelBOList());
                // 设置dtoLabelList
                categoryDto.setSubjectLabelDtoList(subjectLabelDtoList);
                // 返回
                return categoryDto;
            }).collect(Collectors.toList());
            // 返回
            return Result.success(subjectCategoryDtoList);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 修改分类
     * @date: 2024/5/15
     * @param: subjectCategoryDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    public Result<Boolean> updateCategory(@RequestBody SubjectCategoryDto subjectCategoryDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectCategoryDto.getId(), "分类id不能为空");
            // dto转bo
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryDtoToBo(subjectCategoryDto);
            // 调用业务层新增分类
            return subjectCategoryDomainService
                    .updateCategory(subjectCategoryBO) ? Result.success() : Result.error();
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }

    /**
     * @author: 32115
     * @description: 根据id删除分类信息 逻辑删除
     * @date: 2024/5/15
     * @param: subjectCategoryDto
     * @return: Result<Boolean>
     */
    @RequestMapping("/delete")
    public Result<Boolean> deleteCategory(@RequestBody SubjectCategoryDto subjectCategoryDto){
        try {
            // 参数校验
            Preconditions.checkNotNull(subjectCategoryDto.getId(), "分类id不能为空");
            // dto转bo
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDtoConverter
                    .CONVERTER.convertCategoryDtoToBo(subjectCategoryDto);
            // 调用业务层删除分类
            return subjectCategoryDomainService
                    .deleteCategory(subjectCategoryBO) ? Result.success() : Result.error();
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}
