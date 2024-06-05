package com.wxy.subject.infra.mapper;

import com.mybatisflex.core.BaseMapper;
import com.wxy.subject.infra.entity.SubjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoMapper
 * @author: 32115
 * @create: 2024-05-13 11:48
 */
@Mapper
public interface SubjectInfoMapper extends BaseMapper<SubjectInfo> {

    // 获取题目信息
    List<SubjectInfo> getSubjectInfoList(
            @Param("subjectCount") Integer subjectCount,
            @Param("subjectType")Integer subjectType,
            @Param("excludeSubjectIds")List<Long> excludeSubjectIds,
            @Param("assembleIds")List<String> assembleIds
    );
}
