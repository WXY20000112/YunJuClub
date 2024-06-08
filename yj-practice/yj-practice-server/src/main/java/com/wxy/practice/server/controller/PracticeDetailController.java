package com.wxy.practice.server.controller;

import com.alibaba.fastjson2.JSON;
import com.google.common.base.Preconditions;
import com.wxy.practice.api.common.Result;
import com.wxy.practice.api.req.*;
import com.wxy.practice.api.vo.RankVO;
import com.wxy.practice.api.vo.ReportVO;
import com.wxy.practice.api.vo.ScoreDetailVO;
import com.wxy.practice.api.vo.SubjectDetailVO;
import com.wxy.practice.server.aop.AopLogAnnotations;
import com.wxy.practice.server.service.PracticeDetailService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeDetailController
 * @author: 32115
 * @create: 2024-06-07 11:07
 */
@RestController
@RequestMapping("/practice/detail")
@Slf4j
public class PracticeDetailController {

    @Resource
    private PracticeDetailService practiceDetailService;

    /**
     * @author: 32115
     * @description: 提交题目详情信息
     * @date: 2024/6/7
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/submitSubject")
    @AopLogAnnotations
    public Result<Boolean> submitSubjectDetail(@RequestBody SubmitSubjectDetailReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getPracticeId()), "练习id不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSubjectId()), "题目id不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSubjectType()), "题目类型不能为空！");
            Preconditions.checkArgument(!StringUtils.isBlank(req.getTimeUse()), "用时不能为空！");
            // 调用service提交信息并返回结果
            return practiceDetailService.submitSubjectDetail(req) ?
                    Result.success(true) : Result.error("提交失败");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("练习提交题目异常！错误原因{}", e.getMessage(), e);
            return Result.error("练习提交题目异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 用户提交试卷接口
     * @date: 2024/6/8
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/submit")
    public Result<Boolean> submitPractice(@RequestBody SubmitPracticeDetailReq req){
        try {
            // 参数校验
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSetId()), "套题id不能为空！");
            Preconditions.checkArgument(!StringUtils.isBlank(req.getSubmitTime()), "交卷时间不能为空！");
            Preconditions.checkArgument(!StringUtils.isBlank(req.getTimeUse()), "用时不能为空！");
            // 调用service提交信息并返回结果
            return practiceDetailService.submitPracticeDetail(req) ?
                    Result.success(true) : Result.error("提交失败");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("提交练题情况异常！错误原因{}", e.getMessage(), e);
            return Result.error("提交练题情况异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 答案解析部分 获取每题得分情况
     * @date: 2024/6/8
     * @param: req
     * @return: Result<List < ScoreDetailVO>>
     */
    @RequestMapping("/getScoreDetail")
    @AopLogAnnotations
    public Result<List<ScoreDetailVO>> getScoreDetail(@RequestBody GetScoreDetailReq req) {
        try {
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getPracticeId()), "练习id不能为空！");
            List<ScoreDetailVO> list = practiceDetailService.getScoreDetail(req);
            return Result.success(list);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("每题得分异常！错误原因{}", e.getMessage(), e);
            return Result.error("每题得分异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 获取题目答题详情
     * @date: 2024/6/8
     * @param: req
     * @return: Result<SubjectDetailVO>
     */
    @RequestMapping("/getSubjectDetail")
    @AopLogAnnotations
    public Result<SubjectDetailVO> getSubjectDetail(@RequestBody GetSubjectDetailReq req) {
        try {
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSubjectId()), "题目id不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getSubjectType()), "题目类型不能为空！");
            SubjectDetailVO subjectDetailVO = practiceDetailService.getSubjectDetail(req);
            if (log.isInfoEnabled()) {
                log.info("答案详情出参{}", JSON.toJSONString(subjectDetailVO));
            }
            return Result.success(subjectDetailVO);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("答案详情异常！错误原因{}", e.getMessage(), e);
            return Result.error("答案详情异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 生成用户评估报告
     * @date: 2024/6/8
     * @param: req
     * @return: Result<ReportVO>
     */
    @RequestMapping("/getReport")
    @AopLogAnnotations
    public Result<ReportVO> getReport(@RequestBody GetReportReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!Objects.isNull(req.getPracticeId()), "练习id不能为空！");
            ReportVO reportVO = practiceDetailService.getReport(req);
            return Result.success(reportVO);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("获取评估报告异常！错误原因{}", e.getMessage(), e);
            return Result.error("获取评估报告异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 获取练习榜
     * @date: 2024/6/8
     * @return: Result<List < RankVO>>
     */
    @RequestMapping("/getPracticeRankList")
    @AopLogAnnotations
    public Result<List<RankVO>> getPracticeRankList() {
        try {
            List<RankVO> list = practiceDetailService.getPracticeRankList();
            return Result.success(list);
        } catch (Exception e) {
            log.error("练习榜报错！错误原因{}", e.getMessage(), e);
            return Result.error("练习榜异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 放弃练习
     * @date: 2024/6/8
     * @param: practiceId
     * @return: Result<Boolean>
     */
    @RequestMapping("/giveUp")
    @AopLogAnnotations
    public Result<Boolean> giveUp(@RequestParam("practiceId") Long practiceId) {
        try {
            // 参数校验
            Preconditions.checkArgument(!Objects.isNull(practiceId), "练习id不能为空！");
            // 调用service
            return practiceDetailService.giveUp(practiceId) ?
                    Result.success(true) : Result.error("放弃练习失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("放弃练习异常！错误原因{}", e.getMessage(), e);
            return Result.error("放弃练习异常！");
        }
    }
}
