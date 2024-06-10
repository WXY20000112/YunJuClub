package com.wxy.circle.server.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.circle.api.req.GetShareMomentReq;
import com.wxy.circle.api.req.RemoveShareMomentReq;
import com.wxy.circle.api.req.SaveMomentCircleReq;
import com.wxy.circle.api.vo.ShareMomentVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.ShareMoment;
import com.wxy.circle.server.mapper.ShareMomentMapper;
import com.wxy.circle.server.rpc.feign.AuthUserRpc;
import com.wxy.circle.server.service.ShareMomentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wxy.circle.server.entity.table.ShareMomentTableDef.SHARE_MOMENT;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMomentServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:35
 */
@Service
public class ShareMomentServiceImpl
        extends ServiceImpl<ShareMomentMapper, ShareMoment>
        implements ShareMomentService {

    @Resource
    private ShareMomentMapper shareMomentMapper;

    @Resource
    private AuthUserRpc authUserRpc;

    /**
     * @author: 32115
     * @description: 减少回复数量
     * @date: 2024/6/10
     * @param: momentId
     * @param: count
     * @return: void
     */
    @Override
    public void decrReplyCount(Long momentId, int count) {
        UpdateChain.of(ShareMoment.class)
                .setRaw(ShareMoment::getReplyCount, "`reply_count` - "+ count)
                .where(SHARE_MOMENT.ID.eq(momentId))
                .update();
    }

    /**
     * @author: 32115
     * @description: 删除圈子内容
     * @date: 2024/6/9
     * @param: req
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean removeMoment(RemoveShareMomentReq req) {
        return shareMomentMapper.deleteById(req.getId()) > 0;
    }

    /**
     * @author: 32115
     * @description: 分页获取圈子内容
     * @date: 2024/6/9
     * @param: req
     * @return: Page<ShareMomentVO>
     */
    @Override
    @AopLogAnnotations
    public Page<ShareMomentVO> getMoments(GetShareMomentReq req) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SHARE_MOMENT.DEFAULT_COLUMNS)
                .from(SHARE_MOMENT)
                .where(SHARE_MOMENT.CIRCLE_ID.eq(req.getCircleId()))
                .orderBy(SHARE_MOMENT.CIRCLE_ID.desc());
        // 进行分页查询
        Page<ShareMoment> shareMomentPage =
                shareMomentMapper.paginate(req.getPageInfo().getPageNo(),
                        req.getPageInfo().getPageSize(), queryWrapper);
        // 获取查询到的数据
        List<ShareMoment> shareMomentList = shareMomentPage.getRecords();
        // 提取发布用户即createdBy字段 并进行去重 一次性查询用户信息 减少远程调用次数
        List<String> userNameList = shareMomentList.stream()
                .map(ShareMoment::getCreatedBy).distinct().toList();
        // 调用远程服务一次性查询
        Map<String, AuthUserDto> userInfoMap =
                authUserRpc.getUserInfoList(userNameList);
        // 将查询到的数据转化为ShareMomentVO
        List<ShareMomentVO> shareMomentVOList = shareMomentList.stream().map(shareMoment -> {
            // 封装返回对象信息
            ShareMomentVO shareMomentVO = new ShareMomentVO();
            shareMomentVO.setId(shareMoment.getId());
            shareMomentVO.setCircleId(shareMoment.getCircleId());
            shareMomentVO.setContent(shareMoment.getContent());
            // 如果图片列表不为空就进行设置
            if (Objects.nonNull(shareMoment.getPicUrls())){
                shareMomentVO.setPicUrlList(
                        JSONArray.parseArray(shareMoment.getPicUrls(), String.class));
            }
            shareMomentVO.setReplyCount(shareMoment.getReplyCount());
            shareMomentVO.setCreatedTime(shareMoment.getCreatedTime().getTime());
            // 调用rpc获取发布内容的用户信息
            AuthUserDto authUserDto = userInfoMap
                    .getOrDefault(shareMoment.getCreatedBy(), new AuthUserDto());
            // 设置用户信息
            shareMomentVO.setUserName(authUserDto.getNickName());
            shareMomentVO.setUserAvatar(authUserDto.getAvatar());
            return shareMomentVO;
        }).toList();
        // 封装要返回的分页对象
        Page<ShareMomentVO> shareMomentVOPage = new Page<>();
        shareMomentVOPage.setRecords(shareMomentVOList);
        shareMomentVOPage.setTotalPage(shareMomentPage.getTotalPage());
        shareMomentVOPage.setTotalRow(shareMomentPage.getTotalRow());
        shareMomentVOPage.setPageNumber(shareMomentPage.getPageNumber());
        shareMomentVOPage.setPageSize(shareMomentPage.getPageSize());
        return shareMomentVOPage;
    }

    /**
     * @author: 32115
     * @description: 发布圈子内容
     * @date: 2024/6/9
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean insertShareMoment(SaveMomentCircleReq req) {
        // 封装要保存的信息
        ShareMoment shareMoment = new ShareMoment();
        shareMoment.setCircleId(req.getCircleId());
        shareMoment.setContent(req.getContent());
        // 如果图片列表不为空 就将图片信息转为string进行存储
        if (!CollectionUtils.isEmpty(req.getPicUrlList())){
            shareMoment.setPicUrls(JSON.toJSONString(req.getPicUrlList()));
        }
        // 刚发布的内容回复数初始化为0
        shareMoment.setReplyCount(0);
        return this.save(shareMoment);
    }
}
