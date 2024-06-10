package com.wxy.circle.server.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.circle.api.req.GetShareCommentReq;
import com.wxy.circle.api.req.RemoveShareCommentReq;
import com.wxy.circle.api.req.SaveShareCommentReplyReq;
import com.wxy.circle.api.vo.ShareCommentReplyVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.converter.ShareCommentReplyConverter;
import com.wxy.circle.server.dto.ShareCommentReplyDto;
import com.wxy.circle.server.entity.ShareCommentReply;
import com.wxy.circle.server.entity.ShareMoment;
import com.wxy.circle.server.mapper.ShareCommentReplyMapper;
import com.wxy.circle.server.rpc.feign.AuthUserRpc;
import com.wxy.circle.server.service.ShareCommentReplyService;
import com.wxy.circle.server.service.ShareMomentService;
import com.wxy.circle.server.utils.ThreadLocalUtil;
import com.wxy.circle.server.utils.TreeUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.wxy.circle.server.entity.table.ShareCommentReplyTableDef.SHARE_COMMENT_REPLY;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCommentReplyServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:31
 */
@Service
public class ShareCommentReplyServiceImpl
        extends ServiceImpl<ShareCommentReplyMapper, ShareCommentReply>
        implements ShareCommentReplyService {

    @Resource
    private ShareMomentService shareMomentService;

    @Resource
    private ShareCommentReplyMapper shareCommentReplyMapper;

    @Resource
    private AuthUserRpc authUserRpc;

    /**
     * @author: 32115
     * @description: 获取评论列表
     * @date: 2024/6/10
     * @param: req
     * @return: List<ShareCommentReplyVO>
     */
    @Override
    @AopLogAnnotations
    public List<ShareCommentReplyVO> listComment(GetShareCommentReq req) {
        // 根据帖子id查询所有评论以及评论回复
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SHARE_COMMENT_REPLY.DEFAULT_COLUMNS)
                .from(SHARE_COMMENT_REPLY)
                .where(SHARE_COMMENT_REPLY.MOMENT_ID.eq(req.getId()));
        List<ShareCommentReply> shareCommentReplyList = this.list(queryWrapper);
        // 转为Dto
        List<ShareCommentReplyDto> shareCommentReplyDtoList =
                ShareCommentReplyConverter.CONVERTER.converterEntityListToDtoList(shareCommentReplyList);
        // 提取用户名
        List<String> userNameList = shareCommentReplyDtoList.stream()
                .map(ShareCommentReplyDto::getCreatedBy).distinct().toList();
        // 调用rpc查询用户信息
        Map<String, AuthUserDto> userInfoList = authUserRpc.getUserInfoList(userNameList);
        // 封装需要返回的信息
        List<ShareCommentReplyVO> shareCommentReplyVOList = shareCommentReplyDtoList.stream()
                .map(shareCommentReplyDto -> {
                    ShareCommentReplyVO shareCommentReplyVO = new ShareCommentReplyVO();
                    shareCommentReplyVO.setId(shareCommentReplyDto.getId());
                    shareCommentReplyVO.setMomentId(shareCommentReplyDto.getMomentId());
                    shareCommentReplyVO.setReplyType(shareCommentReplyDto.getReplyType());
                    shareCommentReplyVO.setContent(shareCommentReplyDto.getContent());
                    // 图片如果有就设置
                    if (Objects.nonNull(shareCommentReplyDto.getPicUrls())){
                        shareCommentReplyVO.setPicUrlList(JSONArray.parseArray(
                                shareCommentReplyDto.getPicUrls(), String.class));
                    }
                    // 如果是回复就设置
                    if (shareCommentReplyDto.getReplyType() == 2){
                        shareCommentReplyVO.setFromId(shareCommentReplyDto.getCreatedBy());
                        shareCommentReplyVO.setToId(shareCommentReplyDto.getToUser());
                    }
                    shareCommentReplyVO.setParentId(shareCommentReplyDto.getParentId());
                    AuthUserDto authUserDto = userInfoList.getOrDefault(
                            shareCommentReplyDto.getCreatedBy(), new AuthUserDto());
                    shareCommentReplyVO.setUserName(authUserDto.getNickName());
                    shareCommentReplyVO.setAvatar(authUserDto.getAvatar());
                    shareCommentReplyVO.setCreatedTime(shareCommentReplyDto.getCreatedTime().getTime());
                    return shareCommentReplyVO;
                }).toList();
        // 转换为树结构返回
        return TreeUtils.buildTree(shareCommentReplyVOList);
    }

    /**
     * @author: 32115
     * @description: 删除评论
     * @date: 2024/6/10
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean removeComment(RemoveShareCommentReq req) {
        // 查询要删除的评论或者回复信息
        ShareCommentReply shareCommentReply = this.getById(req.getId());
        // 根据momentId查询出该贴子关联的所有评论及回复信息
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SHARE_COMMENT_REPLY.DEFAULT_COLUMNS)
                .from(SHARE_COMMENT_REPLY)
                .where(SHARE_COMMENT_REPLY.MOMENT_ID.eq(shareCommentReply.getMomentId()));
        List<ShareCommentReply> shareCommentReplyList = this.list(queryWrapper);
        List<ShareCommentReplyDto> shareCommentReplyDtoList =
                ShareCommentReplyConverter.CONVERTER.converterEntityListToDtoList(shareCommentReplyList);
        // 创建一个新的列表存储处理过的树结构的数据
        List<ShareCommentReplyDto> newShareCommentReplyList = new ArrayList<>();
        // 将查到的结果转为树结构
        List<ShareCommentReplyDto> treeShareCommentReplyList =
                TreeUtils.buildTree(shareCommentReplyDtoList);
        for (ShareCommentReplyDto reply : treeShareCommentReplyList) {
            TreeUtils.findAll(newShareCommentReplyList, reply, req.getId());
        }
        // 提取评论和回复id 并利用Set集合特性去重
        Set<Long> idSet = newShareCommentReplyList.stream()
                .map(ShareCommentReplyDto::getId)
                .collect(Collectors.toSet());
        QueryWrapper deleteWrapper = QueryWrapper.create()
                .select()
                .from(SHARE_COMMENT_REPLY)
                .where(SHARE_COMMENT_REPLY.ID.in(idSet))
                .and(SHARE_COMMENT_REPLY.MOMENT_ID.eq(shareCommentReply.getMomentId()));
        // 根据查询条件批量删除 并返回受影响的行数 即删除的数据个数
        int count = shareCommentReplyMapper.deleteByQuery(deleteWrapper);
        // 根据id减少相应帖子下的评论数量
        shareMomentService.decrReplyCount(shareCommentReply.getMomentId(), count);
        return true;
    }

    /**
     * @author: 32115
     * @description: 发布评论
     * @date: 2024/6/10
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean insertShareCommentReply(SaveShareCommentReplyReq req) {
        // 创建要保存的实体
        ShareCommentReply shareCommentReply = new ShareCommentReply();
        // 查询帖子信息
        ShareMoment shareMoment = shareMomentService.getById(req.getMomentId());
        // 封装帖子信息
        shareCommentReply.setMomentId(req.getMomentId());
        shareCommentReply.setReplyType(req.getReplyType());
        if (req.getReplyType() == 1){
            // 如果是评论信息 将parentId设为-1作为标识
            // 因为评论下面有对这条评论的回复 所以评论可以理解为一级分类
            shareCommentReply.setParentId(-1L);
            shareCommentReply.setToId(req.getTargetId());
            shareCommentReply.setToUser(ThreadLocalUtil.getLoginId());
            // 判断当前发布这条评论的用户是不是作者
            shareCommentReply.setToUserAuthor(
                    shareMoment.getCreatedBy().equals(
                            ThreadLocalUtil.getLoginId()) ? 1 : 0);
        } else {
            // 如果发布的是回复信息 将parentId设为评论的id
            shareCommentReply.setParentId(req.getTargetId());
            shareCommentReply.setReplyId(req.getTargetId());
            // 回复用户设置为当前登录用户
            shareCommentReply.setReplyUser(ThreadLocalUtil.getLoginId());
            // 判断当前发布这条评论的用户是不是作者
            shareCommentReply.setReplayAuthor(
                    shareMoment.getCreatedBy().equals(
                            ThreadLocalUtil.getLoginId()) ? 1 : 0);
        }
        // 设置回复内容
        shareCommentReply.setContent(req.getContent());
        // 如果图片不为空就设置
        if (!CollectionUtils.isEmpty(req.getPicUrlList())){
            shareCommentReply.setPicUrls(JSON.toJSONString(req.getPicUrlList()));
        }
        // 发布评论前将帖子评论数+1
        shareMoment.setReplyCount(shareMoment.getReplyCount() + 1);
        shareMomentService.updateById(shareMoment);
        return this.save(shareCommentReply);
    }
}
