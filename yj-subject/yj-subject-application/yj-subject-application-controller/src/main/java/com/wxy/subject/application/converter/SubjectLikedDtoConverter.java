package com.wxy.subject.application.converter;

import com.wxy.subject.application.dto.SubjectLikedDto;
import com.wxy.subject.domain.entity.SubjectLikedBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedDtoConverter
 * @author: 32115
 * @create: 2024-05-31 15:40
 */
@Mapper
public interface SubjectLikedDtoConverter {

    SubjectLikedDtoConverter CONVERTER = Mappers.getMapper(SubjectLikedDtoConverter.class);

    // SubjectLikedDto 转成 SubjectLikedBO
    SubjectLikedBO converterDtoToBo(SubjectLikedDto subjectLikedDto);
}
