package com.wxy.practice.server.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.practice.api.enums.SubjectTypeEnum;
import com.wxy.practice.api.vo.*;
import com.wxy.practice.server.aop.AopLogAnnotations;
import com.wxy.practice.server.config.PracticeConfig;
import com.wxy.practice.server.dto.PracticeSubjectDTO;
import com.wxy.practice.server.entity.PracticeSet;
import com.wxy.practice.server.entity.PracticeSetDetail;
import com.wxy.practice.server.enums.PracticeTypeEnum;
import com.wxy.practice.server.mapper.PracticeSetMapper;
import com.wxy.practice.server.rpc.feign.SubjectCategoryRpc;
import com.wxy.practice.server.service.PracticeSetDetailService;
import com.wxy.practice.server.service.PracticeSetService;
import com.wxy.subject.api.entity.SubjectCategoryDto;
import com.wxy.subject.api.entity.SubjectInfoDto;
import com.wxy.subject.api.entity.SubjectLabelDto;
import jakarta.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetServiceImpl
 * @author: 32115
 * @create: 2024-06-02 17:27
 */
@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class PracticeSetServiceImpl extends
        ServiceImpl<PracticeSetMapper, PracticeSet>
        implements PracticeSetService {

    @Resource
    private SubjectCategoryRpc subjectCategoryRpc;

    @Resource
    private PracticeConfig practiceConfig;

    @Resource
    private PracticeSetDetailService practiceSetDetailService;

    /**
     * @author: 32115
     * @description: 开始练习 生成套卷并将套卷信息添加进数据库
     * @date: 2024/6/4
     * @param: practiceSubjectDTO
     * @return: PracticeSetVO
     */
    @Override
    @AopLogAnnotations
    public PracticeSetVO addPracticeSet(PracticeSubjectDTO practiceSubjectDTO) {
        // 创建要返回的对象
        PracticeSetVO practiceSetVO = new PracticeSetVO();
        // 获取套卷题目信息
        List<PracticeSubjectDetailVO> practiceSubjectList
                = getPracticeSubjectList(practiceSubjectDTO);
        if (CollectionUtils.isEmpty(practiceSubjectList)) return practiceSetVO;
        // 使用上下文工具类获取当前对象的代理类@EnableAspectJAutoProxy (exposeProxy = true)
        // 然后通过下面方法获取代理对象，然后再调用 可以避免方法自调用造成的Transactional事务失效
        PracticeSetServiceImpl proxy = (PracticeSetServiceImpl) AopContext.currentProxy();
        proxy.saveInfoToDB(practiceSubjectDTO, practiceSubjectList, practiceSetVO);
        return practiceSetVO;
    }

    /**
     * @author: 32115
     * @description: 保存套卷和套卷下题目信息
     * @date: 2024/6/5
     * @param: practiceSubjectDTO
     * @param: practiceSubjectList
     * @param: practiceSetVO
     * @return: void
     */
    @Transactional
    public void saveInfoToDB(PracticeSubjectDTO practiceSubjectDTO,
                              List<PracticeSubjectDetailVO> practiceSubjectList,
                              PracticeSetVO practiceSetVO) {
        // 创建PracticeSet对象 封装相关数据准备写入数据库
        PracticeSet practiceSet = new PracticeSet();
        // 设置套卷类型为专项练习
        practiceSet.setSetType(PracticeTypeEnum.SPECIAL_PRACTICE.getCode());

        // 接下来组装套卷名称
        // 套卷名称格式：当用户选择的分类多于2个时：分类名称1、分类名称2+“等专项练习”
        // 当用户选择的分类小于等于2个时：分类名称1、分类名称2+“专项练习”
        // 首先获取分类id 从assembleIds字段中进行分离 并使用Set集合特性进行去重
        Set<Long> categoryIdSet = practiceSubjectDTO.getAssembleIds()
                .stream().map(assembleId -> {
                    String categoryId = assembleId.split("-")[0];
                    return Long.valueOf(categoryId);
                }).collect(Collectors.toSet());
        // 使用StringBuffer拼接套卷名称
        StringBuilder setName = new StringBuilder();
        int i = 1;
        for (Long categoryId : categoryIdSet) {
            if (i > 2) break;
            // 调用rpc方法获取分类信息
            SubjectCategoryDto subjectCategoryDto = subjectCategoryRpc
                    .getCategoryById(categoryId);
            // 拼接套卷名称
            setName.append(subjectCategoryDto.getCategoryName());
            setName.append("、");
            i++;
        }
        setName.deleteCharAt(setName.length() - 1);
        // 拼接套卷名称
        if (i == 2){
            setName.append("专项练习");
        } else {
            setName.append("等专项练习");
        }
        // 设置套卷名称
        practiceSet.setSetName(setName.toString());

        // 根据标签id 调用rpc方法获取标签信息
        SubjectLabelDto subjectLabelDto = subjectCategoryRpc
                .getLabelById(Long.valueOf(practiceSubjectDTO
                        .getAssembleIds().getFirst().split("-")[1]));
        // 设置一级分类id
        practiceSet.setPrimaryCategoryId(subjectLabelDto.getCategoryId());
        // 将套卷信息写入数据库
        this.save(practiceSet);

        // 接下来创建PracticeSetDetail对象 封装套卷下对应的题目信息并写入数据库
        practiceSubjectList.forEach(practiceSubjectDetailVO -> {
            PracticeSetDetail practiceSetDetail = new PracticeSetDetail();
            practiceSetDetail.setSetId(practiceSet.getId());
            practiceSetDetail.setSubjectId(practiceSubjectDetailVO.getSubjectId());
            practiceSetDetail.setSubjectType(practiceSubjectDetailVO.getSubjectType());
            // 将套卷下对应的题目信息写入数据库
            practiceSetDetailService.save(practiceSetDetail);
        });
        practiceSetVO.setSetId(practiceSet.getId());
    }

    /**
     * @author: 32115
     * @description: 获取套卷题目信息
     * @date: 2024/6/4
     * @param: practiceSubjectDTO
     * @return: List<PracticeSubjectDetailVO>
     */
    private List<PracticeSubjectDetailVO> getPracticeSubjectList(PracticeSubjectDTO practiceSubjectDTO) {
        // 创建返回集合
        List<PracticeSubjectDetailVO> practiceSubjectList = new ArrayList<>();
        // 用于存储已出过题的id 防止出的题目重复
        List<Long> excludeSubjectIds = new ArrayList<>();

        // 封装查询不同类型的题目需要的查询条件
        // 查询单选题目
        practiceSubjectDTO.setSubjectCount(practiceConfig.getRadioSubjectCount());
        practiceSubjectDTO.setSubjectType(SubjectTypeEnum.RADIO.getCode());
        // 调用getSubjectList方法根据传入的条件查询单选题信息  多选、判断类似
        getSubjectList(practiceSubjectDTO, practiceSubjectList, excludeSubjectIds);

        // 重新设置查询条件查询多选题
        practiceSubjectDTO.setSubjectCount(practiceConfig.getMultipleSubjectCount());
        practiceSubjectDTO.setSubjectType(SubjectTypeEnum.MULTIPLE.getCode());
        getSubjectList(practiceSubjectDTO, practiceSubjectList, excludeSubjectIds);

        // 重新设置查询条件查询判断题
        practiceSubjectDTO.setSubjectCount(practiceConfig.getJudgeSubjectCount());
        practiceSubjectDTO.setSubjectType(SubjectTypeEnum.JUDGE.getCode());
        getSubjectList(practiceSubjectDTO, practiceSubjectList, excludeSubjectIds);

        // 计算总题目数量
        int totalSubjectCount = practiceConfig.getRadioSubjectCount()
                + practiceConfig.getMultipleSubjectCount()
                + practiceConfig.getJudgeSubjectCount();
        // 判断题目是否已经出够
        // 如果已经够了就将题目信息列表返回
        if (totalSubjectCount == practiceSubjectList.size()) return practiceSubjectList;
        // 如果题目数量不够就继续查询单选题目 用单选题补足剩余的数量
        // 计算还差几道题
        Integer remainSubjectCount = totalSubjectCount - practiceSubjectList.size();
        // 查询数据库补足剩余题目
        practiceSubjectDTO.setSubjectCount(remainSubjectCount);
        practiceSubjectDTO.setSubjectType(SubjectTypeEnum.RADIO.getCode());
        getSubjectList(practiceSubjectDTO, practiceSubjectList, excludeSubjectIds);
        // 返回题目信息列表
        return practiceSubjectList;
    }

    /**
     * @author: 32115
     * @description: 根据查询条件查询题目信息
     * @date: 2024/6/4
     * @param: practiceSubjectDTO
     * @param: practiceSubjectList
     * @param: excludeSubjectIds
     * @return: void
     */
    private void getSubjectList(PracticeSubjectDTO practiceSubjectDTO,
                                List<PracticeSubjectDetailVO> practiceSubjectList,
                                List<Long> excludeSubjectIds) {
        // 设置需要排除的题目id 防止重复出题
        practiceSubjectDTO.setExcludeSubjectIds(excludeSubjectIds);
        // 调用rpc接口查询题目信息
        List<SubjectInfoDto> subjectInfoDtoList =
                subjectCategoryRpc.getSubjectInfoList(practiceSubjectDTO);
        // 如果查询结果为空就返回
        if (CollectionUtils.isEmpty(subjectInfoDtoList)) return;
        // 遍历题目信息集合 封装PracticeSubjectDetailVO对象信息
        subjectInfoDtoList.forEach(subjectInfoDto -> {
            // 创建PracticeSubjectDetailVO对象
            PracticeSubjectDetailVO practiceSubjectDetailVO = new PracticeSubjectDetailVO();
            // 设置属性
            practiceSubjectDetailVO.setSubjectId(subjectInfoDto.getId());
            practiceSubjectDetailVO.setSubjectType(subjectInfoDto.getSubjectType());
            // 将已经查到的题目id添加到排除列表中
            excludeSubjectIds.add(subjectInfoDto.getId());
            // 将对象添加到集合中
            practiceSubjectList.add(practiceSubjectDetailVO);
        });
    }

    /**
     * @author: 32115
     * @description: 获取专项练习内容
     * @date: 2024/6/2
     * @return: List<SpecialPracticeVO>
     */
    @Override
    @AopLogAnnotations
    public List<SpecialPracticeVO> getSpecialPracticeList() {
        // 创建SpecialPracticeVO集合 封装一级分类以及二级分类需要的数据
        List<SpecialPracticeVO> specialPracticeVOList = new ArrayList<>();

        // 查出所有题目类型为1，2，3的题目所属的二级分类信息
        List<SubjectCategoryDto> secondCategoryList =
                subjectCategoryRpc.getCategoryBySubjectType();
        // 如果没有查到数据就返回空集合
        if (CollectionUtils.isEmpty(secondCategoryList)) return specialPracticeVOList;

        secondCategoryList.forEach(subjectCategoryDto -> {
            SpecialPracticeVO specialPracticeVO = new SpecialPracticeVO();
            // 设置一级分类id 一级分类id就是刚才查出来的二级分类的父id
            specialPracticeVO.setPrimaryCategoryId(subjectCategoryDto.getParentId());
            // 根据二级分类的父id调用feign接口去查询一级分类信息
            SubjectCategoryDto resultSubjectCategory =
                    subjectCategoryRpc.getCategoryById(subjectCategoryDto.getParentId());
            // 设置一级分类名称
            specialPracticeVO.setPrimaryCategoryName(resultSubjectCategory.getCategoryName());

            // 创建SpecialPracticeCategoryVO集合用于封装二级分类以及分类下标签的信息
            List<SpecialPracticeCategoryVO> specialPracticeCategoryVOList = new ArrayList<>();
            // 创建SpecialPracticeCategoryVO对象封装二级分类信息
            SpecialPracticeCategoryVO specialPracticeCategoryVO = new SpecialPracticeCategoryVO();
            // 设置二级分类id
            specialPracticeCategoryVO.setCategoryId(subjectCategoryDto.getId());
            // 设置二级分类名称
            specialPracticeCategoryVO.setCategoryName(subjectCategoryDto.getCategoryName());

            // 接下来根据二级分类id和题目类型查询二级分类下的标签信息
            List<SubjectLabelDto> subjectLabelDtoList =
                    subjectCategoryRpc.getLabelByCategoryId(subjectCategoryDto.getId());
            // 如果没有查询到数据就return
            if (CollectionUtils.isEmpty(subjectLabelDtoList)) return;

            // 创建SpecialPracticeLabelVO集合用于封装标签的信息
            List<SpecialPracticeLabelVO> specialPracticeLabelVOList = new ArrayList<>();
            subjectLabelDtoList.forEach(subjectLabelDto -> {
                // 创建SpecialPracticeLabelVO对象封装标签信息
                SpecialPracticeLabelVO specialPracticeLabelVO = new SpecialPracticeLabelVO();
                // 设置标签id
                specialPracticeLabelVO.setId(subjectLabelDto.getId());
                // 设置标签名称
                specialPracticeLabelVO.setLabelName(subjectLabelDto.getLabelName());
                // 设置标签id和二级分类id拼接
                specialPracticeLabelVO.setAssembleId(subjectCategoryDto.getId() + "-" + subjectLabelDto.getId());
                // 将SpecialPracticeLabelVO对象添加到集合中
                specialPracticeLabelVOList.add(specialPracticeLabelVO);
            });
            // 将SpecialPracticeLabelVO集合添加到SpecialPracticeCategoryVO对象中
            specialPracticeCategoryVO.setLabelList(specialPracticeLabelVOList);
            // 将SpecialPracticeCategoryVO对象添加到集合中
            specialPracticeCategoryVOList.add(specialPracticeCategoryVO);

            // 将SpecialPracticeCategoryVO集合添加到SpecialPracticeVO对象中
            specialPracticeVO.setCategoryList(specialPracticeCategoryVOList);
            // 将SpecialPracticeVO对象添加到集合中
            specialPracticeVOList.add(specialPracticeVO);
        });
        // 返回集合
        return specialPracticeVOList;
    }
}
