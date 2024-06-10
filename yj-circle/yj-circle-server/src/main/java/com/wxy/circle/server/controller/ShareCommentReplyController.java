package com.wxy.circle.server.controller;

import com.google.common.base.Preconditions;
import com.wxy.circle.api.common.Result;
import com.wxy.circle.api.req.GetShareCommentReq;
import com.wxy.circle.api.req.RemoveShareCommentReq;
import com.wxy.circle.api.req.SaveShareCommentReplyReq;
import com.wxy.circle.api.vo.ShareCommentReplyVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.ShareMoment;
import com.wxy.circle.server.sensitive.WordFilter;
import com.wxy.circle.server.service.ShareCommentReplyService;
import com.wxy.circle.server.service.ShareMomentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCommentReplyController
 * @author: 32115
 * @create: 2024-06-10 09:16
 */
@RestController
@RequestMapping("/share/comment")
@Slf4j
public class ShareCommentReplyController {

    @Resource
    private ShareCommentReplyService shareCommentReplyService;

    @Resource
    private ShareMomentService shareMomentService;

    @Resource
    private WordFilter wordFilter;

    /**
     * @author: 32115
     * @description: 发布评论
     * @date: 2024/6/10
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/save")
    @AopLogAnnotations
    public Result<Boolean> save(@RequestBody SaveShareCommentReplyReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getReplyType()), "类型不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getMomentId()), "内容ID不能为空！");
            // 根据momentId查询该圈子内容是否存在
            ShareMoment moment = shareMomentService.getById(req.getMomentId());
            // 如果圈子帖子信息为空 就做出错误提示
            Preconditions.checkArgument(Objects.nonNull(moment), "帖子信息不存在！");
            Preconditions.checkArgument((Objects.nonNull(req.getContent()) ||
                    Objects.nonNull(req.getPicUrlList())), "内容不能为空！");
            // 检查敏感词
            wordFilter.check(req.getContent());
            // 发布评论
            return shareCommentReplyService.insertShareCommentReply(req) ?
                    Result.success() : Result.error("发布评论失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("发布评论异常！错误原因{}", e.getMessage(), e);
            return Result.error("发布评论异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 删除评论
     * @date: 2024/6/10
     * @param: req
     * @return: Result<Boolean>
     */
    @RequestMapping("/remove")
    @AopLogAnnotations
    public Result<Boolean> remove(@RequestBody RemoveShareCommentReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getReplyType()), "类型不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getId()), "内容ID不能为空！");
            // 删除评论
            return shareCommentReplyService.removeComment(req) ?
                    Result.success() : Result.error("删除评论失败！");
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("删除评论内容异常！错误原因{}", e.getMessage(), e);
            return Result.error("删除评论内容异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 查询帖子评论列表
     * @date: 2024/6/10
     * @param: req
     * @return: Result<List < ShareCommentReplyVO>>
     */
    @RequestMapping("/list")
    @AopLogAnnotations
    public Result<List<ShareCommentReplyVO>> list(@RequestBody GetShareCommentReq req) {
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(req.getId()), "内容ID不能为空！");
            // 查询评论列表
            List<ShareCommentReplyVO> result = shareCommentReplyService.listComment(req);
            // 返回结果
            return Result.success(result);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("获取帖子评论内容异常！错误原因{}", e.getMessage(), e);
            return Result.error("获取帖子评论内容异常！");
        }
    }
}
