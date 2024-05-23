package com.wxy.subject.domain.service.impl;

import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.domain.converter.SubjectCategoryBOConverter;
import com.wxy.subject.domain.converter.SubjectLabelBOConverter;
import com.wxy.subject.domain.entity.SubjectCategoryBO;
import com.wxy.subject.domain.entity.SubjectLabelBO;
import com.wxy.subject.domain.service.SubjectCategoryDomainService;
import com.wxy.subject.infra.entity.SubjectCategory;
import com.wxy.subject.infra.entity.SubjectLabel;
import com.wxy.subject.infra.entity.SubjectMapping;
import com.wxy.subject.infra.service.SubjectCategoryService;
import com.wxy.subject.infra.service.SubjectLabelService;
import com.wxy.subject.infra.service.SubjectMappingService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryDomainServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:00
 */
@Service
@Slf4j
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private ThreadPoolExecutor labelThreadPool;

    /**
     * @author: 32115
     * @description: 新增分类
     * @date: 2024/5/14
     * @param: subjectCategoryBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean add(SubjectCategoryBO subjectCategoryBO) {
        // BO转category实体类
        SubjectCategory subjectCategory = SubjectCategoryBOConverter
                .CONVERTER.convertCategoryBoToCategory(subjectCategoryBO);
        // 保存分类信息
        return subjectCategoryService.insertCategory(subjectCategory);
    }

    /**
     * @author: 32115
     * @description: 删除分类 逻辑删除
     * @date: 2024/5/15
     * @param: subjectCategoryBO
     * @return: boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean deleteCategory(SubjectCategoryBO subjectCategoryBO) {
        // bo转实体类
        SubjectCategory subjectCategory = SubjectCategoryBOConverter
                .CONVERTER.convertCategoryBoToCategory(subjectCategoryBO);
        // 删除分类信息 并返回
        return subjectCategoryService.deleteCategory(subjectCategory);
    }

    /**
     * @author: 32115
     * @description: 修改分类
     * @date: 2024/5/15
     * @param: subjectCategoryBO
     * @return: boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean updateCategory(SubjectCategoryBO subjectCategoryBO) {
        // bo转实体类
        SubjectCategory subjectCategory = SubjectCategoryBOConverter
                .CONVERTER.convertCategoryBoToCategory(subjectCategoryBO);
        // 更新分类信息 并返回
        return subjectCategoryService.updateCategory(subjectCategory);
    }

    /**
     * @author: 32115
     * @description: 根据分类id查询分类标签
     * @date: 2024/5/15
     * @param: subjectCategoryBO
     * @return: List<SubjectCategoryBO>
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public List<SubjectCategoryBO> getLabelByCategoryId(SubjectCategoryBO subjectCategoryBO) {
        // 根据一级分类id查询二级分类
        SubjectCategory subjectCategory = new SubjectCategory();
        subjectCategory.setParentId(subjectCategoryBO.getId());
        List<SubjectCategory> subjectCategoryList =
                subjectCategoryService.getPrimaryCategoryList(subjectCategory);
        // category转bo
        List<SubjectCategoryBO> subjectCategoryBOList = SubjectCategoryBOConverter.CONVERTER
                .convertCategoryToBO(subjectCategoryList);
        Map<Long, List<SubjectLabelBO>> map = new HashMap<>();
        // 使用stream流异步查询二级分类下的标签
        // 这段代码的功能是通过异步方式，为每个SubjectCategoryBO对象获取标签列表，并将其设置到对应的SubjectCategoryBO对象中。
        /*
         * 遍历subjectCategoryBOList列表
         * 对于每个SubjectCategoryBO对象
         * 使用CompletableFuture.supplyAsync方法异步执行getLabelBOList(categoryBO)方法
         * 获取标签列表，并将其放入CompletableFuture中。
         * 将所有的CompletableFuture对象保存到completableFutureList列表中
         **/
        List<CompletableFuture<Map<Long, List<SubjectLabelBO>>>> completableFutureList =
                subjectCategoryBOList.stream().map(categoryBO ->
                        CompletableFuture.supplyAsync(() ->
                            getLabelBOList(categoryBO), labelThreadPool)
        ).toList();
        /*
         * 遍历completableFutureList列表
         * 使用future.get()方法获取CompletableFuture中的结果
         * 如果结果不为空，则将其加入到map中
         **/
        completableFutureList.forEach(future -> {
            try {
                Map<Long, List<SubjectLabelBO>> labelBOMap = future.get();
                if (!CollectionUtils.isEmpty(labelBOMap)) map.putAll(labelBOMap);
            } catch (Exception e) {
                log.error("异步查询分类标签失败", e);
            }
        });
        /*
         * 遍历subjectCategoryBOList列表
         * 如果map中存在对应SubjectCategoryBO对象的标签列表
         * 则将其设置到SubjectCategoryBO对象中
         **/
        subjectCategoryBOList.forEach(categoryBO -> {
            if (!CollectionUtils.isEmpty(map.get(categoryBO.getId()))) {
                categoryBO.setSubjectLabelBOList(map.get(categoryBO.getId()));
            }
        });
        // 返回结果
        return subjectCategoryBOList;
    }

    /**
     * @author: 32115
     * @description: 获取分类标签列表
     * @date: 2024/5/23
     * @param: categoryBO
     * @return: Map<Long, List < SubjectLabelBO>>
     */
    private Map<Long, List<SubjectLabelBO>> getLabelBOList(SubjectCategoryBO categoryBO) {
        // 创建map
        Map<Long, List<SubjectLabelBO>> labelMap = new HashMap<>();
        // 根据分类id在subject_mapping表中查询该分类和标签的映射关系
        List<SubjectMapping> subjectMappingList = subjectMappingService
                .getMappingByCategoryId(categoryBO.getId());
        // 如果分类下没有标签信息 则返回空列表
        if (CollectionUtils.isEmpty(subjectMappingList)) return null;
        // 提取labelId
        List<Long> labelIdList = subjectMappingList.stream().map(SubjectMapping::getLabelId).toList();
        // 根据labelId查询标签信息
        List<SubjectLabel> subjectLabelList = subjectLabelService.getLabelById(labelIdList);
        // label转bo
        List<SubjectLabelBO> subjectLabelBOList = SubjectLabelBOConverter.CONVERTER
                .converterLabelToBo(subjectLabelList);
        labelMap.put(categoryBO.getId(), subjectLabelBOList);
        return labelMap;
    }

    /**
     * @author: 32115
     * @description: 查询一级分类
     * @date: 2024/5/14
     * @param: subjectCategoryBO
     * @return: List<SubjectCategoryBO>
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public List<SubjectCategoryBO> queryPrimaryCategory(SubjectCategoryBO subjectCategoryBO) {
        // bo转category
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.CONVERTER
                .convertCategoryBoToCategory(subjectCategoryBO);
        // 查询一级分类
        List<SubjectCategory> subjectCategoryList =
                subjectCategoryService.getPrimaryCategoryList(subjectCategory);
        // category转bo
        // 将SubjectCategory转换为SubjectCategoryBO
        List<SubjectCategoryBO> boList = SubjectCategoryBOConverter.CONVERTER
                .convertCategoryToBO(subjectCategoryList);
        // 查询分类下的二级分类的数量
        boList.forEach(bo -> {
            // 获取二级分类数量
            bo.setCount(subjectCategoryService.getSecondCategoryCount(bo.getId()));
        });
        // 返回结果
        return boList;
    }
}
