package com.wxy.circle.server.controller;

import com.google.common.base.Preconditions;
import com.mybatisflex.core.paginate.Page;
import com.wxy.circle.api.common.Result;
import com.wxy.circle.api.req.GetShareMomentReq;
import com.wxy.circle.api.req.RemoveShareMomentReq;
import com.wxy.circle.api.req.SaveMomentCircleReq;
import com.wxy.circle.api.vo.ShareMomentVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.ShareCircle;
import com.wxy.circle.server.service.ShareCircleService;
import com.wxy.circle.server.service.ShareMomentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMomentController
 * @author: 32115
 * @create: 2024-06-09 18:05
 */
@RestController
@RequestMapping("/share/moment")
@Slf4j
public class ShareMomentController {

    @Resource
    private ShareMomentService shareMomentService;

    @Resource
    private ShareCircleService shareCircleService;

    /**
     * @author: 32115
     * @description: 发布圈子内容
     * @date: 2024/6/9
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/save")
    @AopLogAnnotations
    public Result<Boolean> save(@RequestBody SaveMomentCircleReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getCircleId()), "圈子ID不能为空！");
            // 查询圈子是否存在
            ShareCircle data = shareCircleService.getById(req.getCircleId());
            Preconditions.checkArgument((Objects.nonNull(data) && data.getParentId() != -1), "非法圈子ID！");
            Preconditions.checkArgument((Objects.nonNull(req.getContent()) ||
                    Objects.nonNull(req.getPicUrlList())), "圈子内容或图片不能为空！");
            return shareMomentService.insertShareMoment(req) ?
                    Result.success() : Result.error("发布内容失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("发布内容异常！错误原因{}", e.getMessage(), e);
            return Result.error("发布内容异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 分页查询圈子内容
     * @date: 2024/6/9
     * @param: req
     * @return: Result<PageResult < ShareMomentVO>>
     */
    @RequestMapping("/getMoments")
    @AopLogAnnotations
    public Result<Page<ShareMomentVO>> getMoments(@RequestBody GetShareMomentReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(!Objects.isNull(req), "参数不能为空！");
            // 查询
            Page<ShareMomentVO> result = shareMomentService.getMoments(req);
            // 返回
            return Result.success(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("圈子内容异常！错误原因{}", e.getMessage(), e);
            return Result.error("圈子内容异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 删除圈子内容
     * @date: 2024/6/9
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/remove")
    @AopLogAnnotations
    public Result<Boolean> remove(@RequestBody RemoveShareMomentReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getId()), "内容ID不能为空！");
            // 删除
            return shareMomentService.removeMoment(req) ?
                    Result.success() : Result.error("删除失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除圈子内容异常！错误原因{}", e.getMessage(), e);
            return Result.error("删除圈子内容异常！");
        }
    }
}
