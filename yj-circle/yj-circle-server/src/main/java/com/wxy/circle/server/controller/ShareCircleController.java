package com.wxy.circle.server.controller;

import com.google.common.base.Preconditions;
import com.wxy.circle.api.common.Result;
import com.wxy.circle.api.req.RemoveShareCircleReq;
import com.wxy.circle.api.req.SaveShareCircleReq;
import com.wxy.circle.api.req.UpdateShareCircleReq;
import com.wxy.circle.api.vo.ShareCircleVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.ShareCircle;
import com.wxy.circle.server.service.ShareCircleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCircleController
 * @author: 32115
 * @create: 2024-06-09 16:36
 */
@RestController
@RequestMapping("/share/circle")
@Slf4j
public class ShareCircleController {

    @Resource
    private ShareCircleService shareCircleService;

    /**
     * @author: 32115
     * @description: 新增圈子
     * @date: 2024/6/9
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/save")
    @AopLogAnnotations
    public Result<Boolean> save(@RequestBody SaveShareCircleReq req){
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getCircleName()), "圈子名称不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getIcon()), "圈子图标不能为空！");
            // 如果父级id不是-1 判断用户传入的父级id是否存在
            if (req.getParentId() != -1){
                // 查询父级信息
                ShareCircle shareCircle =
                        shareCircleService.getById(req.getParentId());
                Preconditions.checkArgument(Objects.nonNull(shareCircle), "父级分类不能为空");
            }
            // 调用service保存圈子信息
            return shareCircleService.insertShareCircle(req) ?
                    Result.success() : Result.error("新增圈子失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("新增圈子异常！错误原因{}", e.getMessage(), e);
            return Result.error("新增圈子异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 修改圈子
     * @date: 2024/6/9
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/update")
    @AopLogAnnotations
    public Result<Boolean> update(@RequestBody UpdateShareCircleReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getCircleName()), "圈子名称不能为空！");
            // 如果父级id不是-1并且不为空 判断用户传入的父级id是否存在
            if (Objects.nonNull(req.getParentId()) && req.getParentId() != -1) {
                // 查询父级信息
                ShareCircle circle = shareCircleService.getById(req.getParentId());
                Preconditions.checkArgument(Objects.nonNull(circle), "父级不存在！");
            }
            return shareCircleService.updateCircle(req) ?
                    Result.success(true) : Result.error("修改圈子失败！");

        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("修改圈子异常！错误原因{}", e.getMessage(), e);
            return Result.error("修改圈子异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 删除圈子
     * @date: 2024/6/9
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/remove")
    @AopLogAnnotations
    public Result<Boolean> remove(@RequestBody RemoveShareCircleReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getId()), "圈子ID不能为空！");
            // 调用service删除圈子信息
            return shareCircleService.removeCircle(req) ?
                    Result.success(true) : Result.error("删除圈子失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除圈子异常！错误原因{}", e.getMessage(), e);
            return Result.error("删除圈子异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 查询圈子
     * @date: 2024/6/9
     * @return: Result<List < ShareCircleVO>>
     */
    @RequestMapping("/list")
    @AopLogAnnotations
    public Result<List<ShareCircleVO>> listResult() {
        try {
            // 调用service查询圈子信息
            List<ShareCircleVO> result =
                    shareCircleService.getShareCircleList();
            // 返回结果
            return Result.success(result);
        } catch (Exception e) {
            log.error("圈子查询异常！错误原因{}", e.getMessage(), e);
            return Result.error("圈子查询异常！");
        }
    }
}
