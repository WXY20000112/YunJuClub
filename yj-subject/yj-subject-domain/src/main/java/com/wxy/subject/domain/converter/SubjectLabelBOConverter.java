package com.wxy.subject.domain.converter;

import com.wxy.subject.domain.entity.SubjectLabelBO;
import com.wxy.subject.infra.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @program: YunJuClub
 * @description: BO与Label转化
 * @author: 32115
 * @create: 2024-03-18 15:41
 */

@Mapper
public interface SubjectLabelBOConverter {

    SubjectLabelBOConverter CONVERTER = Mappers.getMapper(SubjectLabelBOConverter.class);

    // BO转Label
    SubjectLabel converterBoToLabel(SubjectLabelBO subjectLabelBO);

    // label转Bo
    List<SubjectLabelBO> converterLabelToBo(List<SubjectLabel> subjectLabelList);
}
