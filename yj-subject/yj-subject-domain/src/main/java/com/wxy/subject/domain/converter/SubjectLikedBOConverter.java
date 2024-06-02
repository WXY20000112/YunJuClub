package com.wxy.subject.domain.converter;

import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.domain.entity.SubjectLikedBO;
import com.wxy.subject.infra.entity.SubjectLiked;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedBOConverter
 * @author: 32115
 * @create: 2024-06-01 11:30
 */
@Mapper
public interface SubjectLikedBOConverter {

    SubjectLikedBOConverter CONVERTER = Mappers.getMapper(SubjectLikedBOConverter.class);

    // SubjectLikedBO 转换 SubjectLiked
    SubjectLiked converterBoToEntity(SubjectLikedBO subjectLikedBO);

    // SubjectLiked 转换 SubjectLikedBO
    Page<SubjectLikedBO> converterEntityPageToBoPage(Page<SubjectLiked> subjectLikedPage);
}
