package com.wxy.circle.server.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.circle.api.req.GetShareMessageReq;
import com.wxy.circle.api.vo.ShareMessageVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.ShareMessage;
import com.wxy.circle.server.mapper.ShareMessageMapper;
import com.wxy.circle.server.rpc.feign.AuthUserRpc;
import com.wxy.circle.server.service.ShareMessageService;
import com.wxy.circle.server.utils.ThreadLocalUtil;
import com.wxy.circle.server.websocket.YunJuSocket;
import jakarta.annotation.Resource;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.wxy.circle.server.entity.table.ShareMessageTableDef.SHARE_MESSAGE;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMessageServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:33
 */
@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class ShareMessageServiceImpl
        extends ServiceImpl<ShareMessageMapper, ShareMessage>
        implements ShareMessageService {

    @Resource
    private ShareMessageMapper shareMessageMapper;

    @Resource
    private YunJuSocket yunJuSocket;

    @Resource
    private AuthUserRpc authUserRpc;

    /**
     * @author: 32115
     * @description: 添加回复消息并推送
     * @date: 2024/6/10
     * @param: loginId
     * @param: createdBy
     * @param: id
     * @return: void
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public void reply(String fromId, String toId, Long targetId) {
        JSONObject message = new JSONObject();
        // 1=评论 2=回复
        message.put("msgType", "COMMENT_REPLY");
        message.put("msg", String.format("%s 回复了你的评论，快来看看把！",
                authUserRpc.getUserInfo(fromId).getNickName()));
        message.put("targetId", targetId);
        ShareMessage shareMessage =
                getShareMessage(fromId, toId, message);
        // 保存到数据库
        this.save(shareMessage);
    }

    private ShareMessage getShareMessage(String fromId, String toId, JSONObject message) {
        // 封装要保存到数据库的信息
        ShareMessage shareMessage = new ShareMessage();
        shareMessage.setFromId(fromId);
        shareMessage.setToId(toId);
        shareMessage.setContent(message.toJSONString());
        shareMessage.setIsRead(2);
        // 推送消息
        YunJuSocket socket = yunJuSocket.getYunJuSocket(toId);
        if (Objects.nonNull(socket)) {
            yunJuSocket.sendMessage(shareMessage.getContent(), socket.getSession());
        }
        return shareMessage;
    }

    /**
     * @author: 32115
     * @description: 添加评论消息并推送
     * @date: 2024/6/10
     * @param: loginId
     * @param: createdBy
     * @param: id
     * @return: void
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public void comment(String fromId, String toId, Long targetId) {
        JSONObject message = new JSONObject();
        // 1=评论 2=回复
        // 封装消息体
        message.put("msgType", "COMMENT");
        message.put("msg", "评论了你的内容，快来看看把");
        message.put("targetId", targetId);
        // 封装要保存到数据库的信息
        ShareMessage shareMessage =
                getShareMessage(fromId, toId, message);
        // 保存到数据库
        this.save(shareMessage);
    }

    /**
     * @author: 32115
     * @description: 分页查询消息
     * @date: 2024/6/10
     * @param: req
     * @return: Page<ShareMessageVO>
     */
    @Override
    @AopLogAnnotations
    public Page<ShareMessageVO> getShareMessagePage(GetShareMessageReq req) {
        // 使用上下文工具类获取当前对象的代理类@EnableAspectJAutoProxy (exposeProxy = true)
        // 然后通过下面方法获取代理对象，然后再调用 可以避免方法自调用造成的Transactional事务失效
        ShareMessageServiceImpl proxy = (ShareMessageServiceImpl) AopContext.currentProxy();
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SHARE_MESSAGE.DEFAULT_COLUMNS)
                .from(SHARE_MESSAGE)
                .where(SHARE_MESSAGE.TO_ID.eq(ThreadLocalUtil.getLoginId()))
                .and(SHARE_MESSAGE.IS_READ.eq(req.getIsRead()));
        Page<ShareMessage> shareMessagePage =
                shareMessageMapper.paginate(req.getPageInfo().getPageNo(),
                        req.getPageInfo().getPageSize(), queryWrapper);
        // 获取查询到的数据
        List<ShareMessage> shareMessageList = shareMessagePage.getRecords();
        // 如果数据不为空就把未读状态的数据改成已读 因为都去查询消息了肯定就是已读了
        if (!CollectionUtils.isEmpty(shareMessageList) && req.getIsRead() == 2){
            // 提取查询到的所有消息id
            List<Long> idList = shareMessageList
                    .stream().map(ShareMessage::getId).toList();
            // 批量修改成已读
            proxy.updateShareMessageIsRead(idList);
        }
        // 遍历将数据封装成要返回的类型
        List<ShareMessageVO> shareMessageVOList = shareMessageList.stream()
                .map(shareMessage -> {
                    ShareMessageVO shareMessageVO = new ShareMessageVO();
                    shareMessageVO.setId(shareMessage.getId());
                    shareMessageVO.setContent(JSON.parseObject(shareMessage.getContent()));
                    shareMessageVO.setCreatedTime(shareMessage.getCreatedTime());
                    return shareMessageVO;
                }).toList();
        // 封装分页参数
        Page<ShareMessageVO> shareMessageVOPage = new Page<>();
        shareMessageVOPage.setRecords(shareMessageVOList);
        shareMessageVOPage.setPageNumber(shareMessagePage.getPageNumber());
        shareMessageVOPage.setTotalPage(shareMessagePage.getTotalPage());
        shareMessageVOPage.setPageSize(shareMessagePage.getPageSize());
        shareMessageVOPage.setTotalRow(shareMessagePage.getTotalRow());
        return shareMessageVOPage;
    }

    /**
     * @author: 32115
     * @description: 批量修改已读
     * @date: 2024/6/10
     * @param: idList
     * @return: void
     */
    @Transactional
    public void updateShareMessageIsRead(List<Long> idList) {
        UpdateChain.of(ShareMessage.class)
                .set(ShareMessage::getIsRead, 1)
                .where(ShareMessage::getId).in(idList)
                .update();
    }

    /**
     * @author: 32115
     * @description: 查询是否存在未读消息
     * @date: 2024/6/10
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    public Boolean existUnReadMessage() {
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SHARE_MESSAGE.DEFAULT_COLUMNS)
                .from(SHARE_MESSAGE)
                .where(SHARE_MESSAGE.TO_ID.eq(ThreadLocalUtil.getLoginId()))
                .and(SHARE_MESSAGE.IS_READ.eq(2));
        return this.exists(queryWrapper);
    }
}
