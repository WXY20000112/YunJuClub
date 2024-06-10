package com.wxy.circle.server.controller;

import com.google.common.base.Preconditions;
import com.wxy.circle.api.common.Result;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.service.SensitiveWordsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 * 圈子信息 前端控制器
 * </p>
 *
 * @author ChickenWing
 * @since 2024/05/16
 */
@Slf4j
@RestController
@RequestMapping("/sensitive/words")
public class SensitiveWordsController {

    @Resource
    private SensitiveWordsService sensitiveWordsService;

    /**
     * 新增敏感词
     */
    @RequestMapping(value = "/save")
    @AopLogAnnotations
    public Result<Boolean> save(@RequestParam("words") String words,
                                @RequestParam("type") Integer type) {
        try {
            // 参数校验
            Preconditions.checkArgument(StringUtils.isNotBlank(words), "参数不能为空！");
            // 添加
            return Result.success(sensitiveWordsService.saveSensitiveWords(words, type));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("新增敏感词异常！错误原因{}", e.getMessage(), e);
            return Result.error("新增敏感词异常！");
        }
    }

    /**
     * 删除敏感词
     */
    @RequestMapping(value = "/remove")
    @AopLogAnnotations
    public Result<Boolean> remove(@RequestParam("id") Long id) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(id), "参数不能为空！");
            // 删除
            return Result.success(sensitiveWordsService.deleteById(id));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除敏感词异常！错误原因{}", e.getMessage(), e);
            return Result.error("删除敏感词异常！");
        }
    }

}
