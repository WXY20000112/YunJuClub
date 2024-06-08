package com.wxy.practice.server.rpc.feign;

import com.wxy.practice.server.dto.PracticeSubjectDTO;
import com.wxy.subject.api.entity.SubjectCategoryDto;
import com.wxy.subject.api.entity.SubjectInfoDto;
import com.wxy.subject.api.entity.SubjectLabelDto;
import com.wxy.subject.api.feign.SubjectFeignService;
import com.wxy.subject.api.response.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: AuthUserRpc
 * @author: 32115
 * @create: 2024-05-23 18:15
 */
@Component
public class SubjectRpc {

    @Resource
    private SubjectFeignService subjectFeignService;

    /**
     * @author: 32115
     * @description: feign调用远程接口 根据题目类型获取二级分类
     * @date: 2024/6/3
     * @return: List<SubjectCategoryDto>
     */
    public List<SubjectCategoryDto> getCategoryBySubjectType() {
        // 调用远程接口
        Result<List<SubjectCategoryDto>> result =
                subjectFeignService.getCategoryBySubjectType();
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return Collections.emptyList();
        // 返回数据
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 根据id 获取一级分类
     * @date: 2024/6/3
     * @param: parentId
     * @return: SubjectCategoryDto
     */
    public SubjectCategoryDto getCategoryById(Long parentId) {
        // 调用远程接口
        Result<SubjectCategoryDto> result =
                subjectFeignService.getCategoryById(parentId);
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return new SubjectCategoryDto();
        // 返回数据
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 根据分类id查询标签信息
     * @date: 2024/6/3
     * @param: id
     * @return: List<SubjectLabelDto>
     */
    public List<SubjectLabelDto> getLabelByCategoryId(Long categoryId) {
        // 调用远程接口
        Result<List<SubjectLabelDto>> result =
                subjectFeignService.getLabelByCategoryId(categoryId);
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return Collections.emptyList();
        // 返回数据
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 查询题目信息
     * @date: 2024/6/4
     * @param: practiceSubjectDTO
     * @return: List<SubjectInfoDto>
     */
    public List<SubjectInfoDto> getSubjectInfoList(PracticeSubjectDTO practiceSubjectDTO) {
        // 将查询条件封装到SubjectInfoDto中
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.setSubjectCount(practiceSubjectDTO.getSubjectCount());
        subjectInfoDto.setExcludeSubjectIds(practiceSubjectDTO.getExcludeSubjectIds());
        subjectInfoDto.setSubjectType(practiceSubjectDTO.getSubjectType());
        subjectInfoDto.setAssembleIds(practiceSubjectDTO.getAssembleIds());
        // 调用远程接口
        Result<List<SubjectInfoDto>> result =
                subjectFeignService.getSubjectInfoList(subjectInfoDto);
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return Collections.emptyList();
        // 返回数据
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 根据id查询标签信息
     * @date: 2024/6/5
     * @param: s
     * @return: SubjectLabelDto
     */
    public SubjectLabelDto getLabelById(Long id) {
        // 调用远程接口
        Result<SubjectLabelDto> result =
                subjectFeignService.getLabelById(id);
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return new SubjectLabelDto();
        // 返回数据
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 根据id查询题目信息
     * @date: 2024/6/6
     * @param: subjectId
     * @return: SubjectInfoDto
     */
    public SubjectInfoDto getSubjectInfoById(PracticeSubjectDTO practiceSubjectDTO) {
        // 将查询条件封装到SubjectInfoDto中
        SubjectInfoDto subjectInfoDto = new SubjectInfoDto();
        subjectInfoDto.setId(practiceSubjectDTO.getSubjectId());
        subjectInfoDto.setSubjectType(practiceSubjectDTO.getSubjectType());
        // 调用远程接口
        Result<SubjectInfoDto> result =
                subjectFeignService.getSubjectInfoById(subjectInfoDto);
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return new SubjectInfoDto();
        // 返回数据
        return result.getData();
    }

    /**
     * @author: 32115
     * @description: 根据题目id查询标签id
     * @date: 2024/6/8
     * @param: subjectId
     * @return: List<Long>
     */
    public List<Long> getLabelIdsBySubjectId(Long subjectId) {
        // 调用远程接口
        Result<List<Long>> result =
                subjectFeignService.getLabelBySubjectId(subjectId);
        // 判断远程调用是否成功 不成功返回空集合
        if (result.getCode() != 200) return Collections.emptyList();
        // 返回数据
        return result.getData();
    }
}
