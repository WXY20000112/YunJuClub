package com.wxy.practice.server.service;

import com.mybatisflex.core.service.IService;
import com.wxy.practice.api.req.*;
import com.wxy.practice.api.vo.RankVO;
import com.wxy.practice.api.vo.ReportVO;
import com.wxy.practice.api.vo.ScoreDetailVO;
import com.wxy.practice.api.vo.SubjectDetailVO;
import com.wxy.practice.server.entity.PracticeDetail;

import java.util.List;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeDetailService
 * @author: 32115
 * @create: 2024-06-05 15:17
 */
public interface PracticeDetailService extends IService<PracticeDetail> {

    // 根据练习id和题目id查询练习详情
    PracticeDetail getPracticeDetailByPracticeIdAndSubjectId(
            Long practiceId, Long subjectId, String loginId);

    // 提交题目详情
    Boolean submitSubjectDetail(SubmitSubjectDetailReq req);

    // 提交练习详情
    Boolean submitPracticeDetail(SubmitPracticeDetailReq req);

    // 根据练习id查询练习详情
    List<PracticeDetail> getByPracticeId(Long practiceId);

    // 获取每题得分情况
    List<ScoreDetailVO> getScoreDetail(GetScoreDetailReq req);

    // 获取题目答题详情
    SubjectDetailVO getSubjectDetail(GetSubjectDetailReq req);

    // 获取练习报告
    ReportVO getReport(GetReportReq req);

    // 获取练习排名
    List<RankVO> getPracticeRankList();

    // 放弃练习
    Boolean giveUp(Long practiceId);
}
