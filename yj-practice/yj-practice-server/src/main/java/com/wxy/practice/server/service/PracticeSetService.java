package com.wxy.practice.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.practice.api.req.GetPracticeSubjectsReq;
import com.wxy.practice.api.vo.PracticeSetVO;
import com.wxy.practice.api.vo.PracticeSubjectListVO;
import com.wxy.practice.api.vo.SpecialPracticeVO;
import com.wxy.practice.server.dto.PracticeSubjectDTO;
import com.wxy.practice.server.entity.PracticeSet;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetService
 * @author: 32115
 * @create: 2024-06-02 17:26
 */
public interface PracticeSetService extends IService<PracticeSet> {

    // 获取专项练习列表
    List<SpecialPracticeVO> getSpecialPracticeList();

    // 开始练习 生成套卷并将套卷信息添加进数据库
    PracticeSetVO addPracticeSet(PracticeSubjectDTO practiceSubjectDTO);

    // 获取套卷以及套卷下题目信息
    PracticeSubjectListVO getSubjectList(GetPracticeSubjectsReq req);
}
