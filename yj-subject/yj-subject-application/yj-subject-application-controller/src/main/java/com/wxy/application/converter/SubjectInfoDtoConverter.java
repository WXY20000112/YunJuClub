package com.wxy.application.converter;

import com.wxy.application.dto.SubjectInfoDto;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoDto与SubjectInfoBO转换器
 * @author: 32115
 * @create: 2024-05-15 16:36
 */
@Mapper
public interface SubjectInfoDtoConverter {

    SubjectInfoDtoConverter CONVERTER = Mappers.getMapper(SubjectInfoDtoConverter.class);

    // SubjectInfoDto 转换 SubjectInfoBO
    SubjectInfoBO converterDtoToBo(SubjectInfoDto subjectInfoDto);

    // SubjectInfoBO 转换 SubjectInfoDto
    List<SubjectInfoDto> converterBoToDto(List<SubjectInfoBO> subjectInfoBOList);
}
