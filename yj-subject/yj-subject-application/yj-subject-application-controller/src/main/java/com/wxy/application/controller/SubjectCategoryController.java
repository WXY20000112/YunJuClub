package com.wxy.application.controller;

import com.wxy.subject.domain.entity.SubjectCategoryBO;
import com.wxy.subject.domain.service.SubjectCategoryDomainService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: YunJuClub-Flex
 * @description: 题目分类控制层
 * @author: 32115
 * @create: 2024-05-13 14:01
 */
@RestController
@RequestMapping("/subject/category")
public class SubjectCategoryController {

    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    @PostMapping("/test")
    public Boolean test(@RequestBody SubjectCategoryBO subjectCategoryBO) {
        return subjectCategoryDomainService.add(subjectCategoryBO);
    }
}
