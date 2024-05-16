package com.wxy.subject.infra.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.subject.common.enums.CategoryTypeEnum;
import com.wxy.subject.common.enums.IsDeletedEnum;
import com.wxy.subject.infra.entity.SubjectCategory;
import com.wxy.subject.infra.mapper.SubjectCategoryMapper;
import com.wxy.subject.infra.service.SubjectCategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wxy.subject.infra.entity.table.SubjectCategoryTableDef.SUBJECT_CATEGORY;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectCategoryServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:00
 */
@Service
public class SubjectCategoryServiceImpl
        extends ServiceImpl<SubjectCategoryMapper, SubjectCategory>
        implements SubjectCategoryService {

    @Resource
    private SubjectCategoryMapper subjectCategoryMapper;

    /**
     * @author: 32115
     * @description: 根据id查询分类
     * @date: 2024/5/16
     * @param: categoryId
     * @return: SubjectCategory
     */
    @Override
    public SubjectCategory getCategoryById(Long categoryId) {
        return this.getById(categoryId);
    }

    /**
     * @author: 32115
     * @description: 删除分类 逻辑删除
     * @date: 2024/5/15
     * @param: subjectCategory
     * @return: boolean
     */
    @Override
    public Boolean deleteCategory(SubjectCategory subjectCategory) {
        return subjectCategoryMapper.delete(subjectCategory) > 0;
    }

    /**
     * @author: 32115
     * @description: 添加分类
     * @date: 2024/5/15
     * @param: subjectCategory
     * @return: Boolean
     */
    @Override
    public Boolean insertCategory(SubjectCategory subjectCategory) {
        return this.save(subjectCategory);
    }

    /**
     * @author: 32115
     * @description: 更新分类
     * @date: 2024/5/15
     * @param: subjectCategory
     * @return: boolean
     */
    @Override
    public Boolean updateCategory(SubjectCategory subjectCategory) {
        return this.updateById(subjectCategory);
    }

    /**
     * @author: 32115
     * @description: 获取二级分类数量
     * @date: 2024/5/14
     * @param: id
     * @return: Integer
     */
    @Override
    public Integer getSecondCategoryCount(Long id) {
        // 构造查询条件 查询parent_id为id的记录
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(SUBJECT_CATEGORY)
                .where(SUBJECT_CATEGORY.PARENT_ID.eq(id))
                .and(SUBJECT_CATEGORY.IS_DELETED.eq(IsDeletedEnum.UN_DELETED.getCode()));
        return Math.toIntExact(this.count(queryWrapper));
    }

    /**
     * @author: 32115
     * @description: 获取一级分类列表
     * @date: 2024/5/14
     * @param: subjectCategory
     * @return: List<SubjectCategory>
     */
    @Override
    public List<SubjectCategory> getPrimaryCategoryList(SubjectCategory subjectCategory) {
        // 构造查询条件 查询的是一级分类 所以type值为1 只查询未删除的即is_deleted值为0
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(SUBJECT_CATEGORY)
                .where(SUBJECT_CATEGORY.IS_DELETED.eq(IsDeletedEnum.UN_DELETED.getCode()))
                .and(SUBJECT_CATEGORY.CATEGORY_TYPE.eq(CategoryTypeEnum.FIRST_CATEGORY.getCode())
                        .when(subjectCategory.getParentId() == null))
                .and(SUBJECT_CATEGORY.PARENT_ID.eq(subjectCategory.getParentId()));
        // 执行查询
        return this.list(queryWrapper);
    }
}
