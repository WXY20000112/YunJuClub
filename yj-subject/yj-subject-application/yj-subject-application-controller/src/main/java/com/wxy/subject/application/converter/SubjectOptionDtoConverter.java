package com.wxy.subject.application.converter;

import com.wxy.subject.application.dto.SubjectOptionDto;
import com.wxy.subject.domain.entity.SubjectOptionBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectOptionDto 与 SubjectOptionBO 转换器
 * @author: 32115
 * @create: 2024-05-15 16:45
 */
@Mapper
public interface SubjectOptionDtoConverter {

    SubjectOptionDtoConverter CONVERTER = Mappers.getMapper(SubjectOptionDtoConverter.class);

    // dto 转 bo
    List<SubjectOptionBO> converterDtoToBo(List<SubjectOptionDto> subjectOptionDtoList);
}
