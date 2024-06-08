package com.wxy.practice.server.mapper;

import com.mybatisflex.core.BaseMapper;
import com.wxy.practice.server.entity.PracticeDetail;
import com.wxy.practice.server.entity.PracticeRank;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeDetail
 * @author: 32115
 * @create: 2024-06-05 15:16
 */
@Mapper
public interface PracticeDetailMapper extends BaseMapper<PracticeDetail> {

    // 获取练习排名
    List<PracticeRank> getPracticeCount();
}
