package com.wxy.subject.domain.converter;

import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.infra.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoBO与SubjectInfo转换器
 * @author: 32115
 * @create: 2024-05-15 16:56
 */
@Mapper
public interface SubjectInfoBOConverter {

    SubjectInfoBOConverter CONVERTER = Mappers.getMapper(SubjectInfoBOConverter.class);

    // SubjectInfoBO转SubjectInfo
    SubjectInfo converterBoToInfo(SubjectInfoBO subjectInfoBO);
}
