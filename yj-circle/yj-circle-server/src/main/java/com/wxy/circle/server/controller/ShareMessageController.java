package com.wxy.circle.server.controller;

import com.google.common.base.Preconditions;
import com.mybatisflex.core.paginate.Page;
import com.wxy.circle.api.common.Result;
import com.wxy.circle.api.req.GetShareMessageReq;
import com.wxy.circle.api.vo.ShareMessageVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.service.ShareMessageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: ShareMessageController
 * @author: 32115
 * @create: 2024-06-10 16:40
 */
@RestController
@RequestMapping("/share/message")
@Slf4j
public class ShareMessageController {

    @Resource
    private ShareMessageService shareMessageService;

    /**
     * @author: 32115
     * @description: 查询是否存在未读消息
     * @date: 2024/6/10
     * @return: Result<Boolean>
     */
    @RequestMapping("/unRead")
    @AopLogAnnotations
    public Result<Boolean> existUnReadMessage(){
        try {
            return Result.success(shareMessageService.existUnReadMessage());
        } catch (Exception e) {
            log.error("消息异常！错误原因{}", e.getMessage(), e);
            return Result.error("消息异常！");
        }
    }

    /**
     * @author: 32115
     * @description: 分页查询消息
     * @date: 2024/6/10
     * @param: req
     * @return: Result<Page < ShareMessageVO>>
     */
    @RequestMapping("/getMessages")
    @AopLogAnnotations
    public Result<Page<ShareMessageVO>> getShareMessagePage(@RequestBody GetShareMessageReq req){
        try {
            // 参数校验
            Preconditions.checkArgument(Objects.nonNull(req), "参数不能为空");
            // 查询
            Page<ShareMessageVO> shareMessageVOPage =
                    shareMessageService.getShareMessagePage(req);
            // 返回
            return Result.success(shareMessageVOPage);
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        } catch (Exception e) {
            log.error("消息异常！错误原因{}", e.getMessage(), e);
            return Result.error("消息异常！");
        }
    }
}
