package com.wxy.subject.domain.converter;

import com.wxy.subject.domain.entity.SubjectCategoryBO;
import com.wxy.subject.infra.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub
 * @description: 将SubjectCategoryB0转化成SubjectCategory
 * @author: 32115
 * @create: 2024-03-17 21:13
 */

@Mapper
public interface SubjectCategoryBOConverter {

    SubjectCategoryBOConverter CONVERTER = Mappers.getMapper(SubjectCategoryBOConverter.class);

    // BO转category
    SubjectCategory convertCategoryBoToCategory(SubjectCategoryBO subjectCategoryBO);

    // categoryList转BOList
    List<SubjectCategoryBO> convertCategoryToBO(List<SubjectCategory> subjectCategoryList);

    // category转BO
    SubjectCategoryBO convertEntityToBO(SubjectCategory subjectCategory);
}
