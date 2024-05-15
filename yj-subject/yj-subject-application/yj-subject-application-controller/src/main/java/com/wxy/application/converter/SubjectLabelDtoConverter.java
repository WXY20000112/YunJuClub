package com.wxy.application.converter;

import com.wxy.application.dto.SubjectLabelDto;
import com.wxy.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub
 * @description: Dto与BO转换
 * @author: 32115
 * @create: 2024-03-18 15:31
 */
@Mapper
public interface SubjectLabelDtoConverter {

    SubjectLabelDtoConverter CONVERTER = Mappers.getMapper(SubjectLabelDtoConverter.class);

    // Dto转BO
    SubjectLabelBO converterDtoToBo(SubjectLabelDto subjectLabelDto);

    // Bo转Dto
    List<SubjectLabelDto> converterBoToDto(List<SubjectLabelBO> subjectLabelBOList);
}
