package com.wxy.practice.server.controller;

import com.google.common.base.Preconditions;
import com.wxy.practice.api.common.Result;
import com.wxy.practice.api.req.GetPracticeSubjectListReq;
import com.wxy.practice.api.vo.PracticeSetVO;
import com.wxy.practice.api.vo.SpecialPracticeVO;
import com.wxy.practice.server.aop.AopLogAnnotations;
import com.wxy.practice.server.dto.PracticeSubjectDTO;
import com.wxy.practice.server.service.PracticeSetService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeSetController
 * @author: 32115
 * @create: 2024-06-02 17:29
 */
@RestController
@RequestMapping("/practice/set")
@Slf4j
public class PracticeSetController {

    @Resource
    private PracticeSetService practiceSetService;

    /**
     * @author: 32115
     * @description: 获取专项练习内容
     * @date: 2024/6/2
     * @return: Result<List < SpecialPracticeVO>>
     */
    @RequestMapping("/getSpecialPracticeContent")
    @AopLogAnnotations
    public Result<List<SpecialPracticeVO>> getSpecialPracticeList() {
        try {
            // 获取专项练习内容
            List<SpecialPracticeVO> specialPracticeVOList =
                    practiceSetService.getSpecialPracticeList();
            // 返回结果
            return Result.success(specialPracticeVOList);
        } catch (Exception e) {
            log.error("PracticeSetController.getSpecialPracticeList.error:{}", e.getMessage(), e);
            return Result.error("获取专项练习内容失败");
        }
    }

    /**
     * @author: 32115
     * @description: 开始练习 生成套卷并将套卷信息添加进数据库
     * @date: 2024/6/4 
     * @param: req
     * @return: Result<PracticeSetVO>
     */
    @RequestMapping("/addPractice")
    @AopLogAnnotations
    public Result<PracticeSetVO> addPracticeSet(@RequestBody GetPracticeSubjectListReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(req.getAssembleIds()), "标签ids不能为空！");
            // 创建PracticeSubjectDto对象 封装assembleIds参数
            PracticeSubjectDTO practiceSubjectDTO = new PracticeSubjectDTO();
            practiceSubjectDTO.setAssembleIds(req.getAssembleIds());
            // 调用service层方法
            PracticeSetVO practiceSetVO = practiceSetService.addPracticeSet(practiceSubjectDTO);
            // 返回结果
            return Result.success(practiceSetVO);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("获取练习题目列表异常！错误原因{}", e.getMessage(), e);
            return Result.error("获取练习题目列表异常！");
        }
    }
}
