package com.wxy.application.converter;

import com.wxy.application.dto.SubjectCategoryDto;
import com.wxy.subject.domain.entity.SubjectCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: Dto与Bo实体类转换
 * @author: 32115
 * @create: 2024-05-14 17:29
 */
@Mapper
public interface SubjectCategoryDtoConverter {

    // 创建一个静态的转换器
    SubjectCategoryDtoConverter CONVERTER = Mappers.getMapper(SubjectCategoryDtoConverter.class);

    // Dto转BO
    SubjectCategoryBO convertCategoryDtoToBo(SubjectCategoryDto subjectCategoryDto);

    // BO转Dto
    List<SubjectCategoryDto> convertCategoryBOToDto(List<SubjectCategoryBO> subjectCategoryBOList);

    // Bo转Dto
    SubjectCategoryDto convertCategoryBoToDto(SubjectCategoryBO subjectCategoryBO);
}
