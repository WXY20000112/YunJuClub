package com.wxy.subject.domain.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.common.constant.SubjectConstant;
import com.wxy.subject.common.enums.SubjectLikedStatusEnum;
import com.wxy.subject.common.utils.RedisUtil;
import com.wxy.subject.common.utils.ThreadLocalUtil;
import com.wxy.subject.domain.converter.SubjectLikedBOConverter;
import com.wxy.subject.domain.entity.SubjectLikedBO;
import com.wxy.subject.domain.service.SubjectLikedDomainService;
import com.wxy.subject.infra.entity.SubjectLiked;
import com.wxy.subject.infra.service.SubjectLikedService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectLikedDomainServiceImpl
 * @author: 32115
 * @create: 2024-05-31 15:24
 */
@Service
public class SubjectLikedDomainServiceImpl implements SubjectLikedDomainService {

    @Resource
    private SubjectLikedService subjectLikedService;

    @Resource
    private RedisUtil redisUtil;

    /**
     * @author: 32115
     * @description: 获取这个题目是否被当前用户点赞过
     * @date: 2024/5/31
     * @param: subjectId
     * @param: userId
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    public Boolean isLiked(String subjectId, String userId) {
        // 创建存储哪个用户那个个题目点的赞的key
        String detailKey = redisUtil.buildKey(
                ".",
                SubjectConstant.SUBJECT_LIKED_DETAIL_KEY,
                subjectId,
                userId
        );
        return redisUtil.exist(detailKey);
    }

    /**
     * @author: 32115
     * @description: 获取题目点赞信息
     * @date: 2024/6/1
     * @param: subjectLikedBO
     * @return: Page<SubjectLikedBO>
     */
    @Override
    @AopLogAnnotations
    public Page<SubjectLikedBO> getSubjectLikedPage(SubjectLikedBO subjectLikedBO) {
        // 获取用户题目点赞列表信息
        Page<SubjectLiked> subjectLikedPage = subjectLikedService
                .getSubjectLikedPage(subjectLikedBO.getPageNo(),
                        subjectLikedBO.getPageSize(), ThreadLocalUtil.getLoginId());
        // 返回数据
        return SubjectLikedBOConverter.CONVERTER
                .converterEntityPageToBoPage(subjectLikedPage);
    }

    /**
     * @author: 32115
     * @description: 同步题目点赞信息到数据库
     * @date: 2024/5/31
     * @return: void
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public void syncSubjectLiked() {
        // 获取redis中点赞信息
        Map<Object, Object> map = redisUtil
                .getHashAndDelete(SubjectConstant.SUBJECT_LIKED_HASH_KEY);
        // 如果为空直接返回
        if (MapUtils.isEmpty(map)) return;
        // 保存要新增的信息
        List<SubjectLiked> subjectInsertLikedList = new ArrayList<>();
        // 保存要更新的信息
        List<SubjectLiked> subjectUpdateLikedList = new ArrayList<>();
        // 遍历map
        map.forEach((key, value) -> {
            SubjectLiked subjectLiked = new SubjectLiked();
            // 将key根据“：”进行分割 得到subjectId和userId
            Long subjectId = Long.valueOf(key.toString().split(":")[0]);
            String userId = key.toString().split(":")[1];
            // 封装数据
            subjectLiked.setSubjectId(subjectId);
            subjectLiked.setLikeUserId(userId);
            subjectLiked.setStatus(Integer.valueOf(String.valueOf(value)));
            // 添加之前要判断一下这个数据是否已经存在
            // 如果存在表明这个用户之前已经对这个题目进行了点赞或者取消点赞操作 那么就不需要再新增了 只更新一下点赞状态就行了
            // 创建两个list 分别保存新增的数据和要更新的数据
            if (subjectLikedService.existSubjectLiked(subjectLiked)){
                // 修改的话只需要添加修改updateBy就行
                subjectLiked.setUpdateBy(userId);
                // 已经存在的话就放入要更新的list中
                subjectUpdateLikedList.add(subjectLiked);
            }else {
                // 新增的话需要添加updateBy和createBy
                subjectLiked.setCreatedBy(userId);
                subjectLiked.setUpdateBy(userId);
                // 如果不存在就放入要新增的list中
                subjectInsertLikedList.add(subjectLiked);
            }
        });
        // 批量保存
        subjectLikedService.insertSubjectLikedList(subjectInsertLikedList);
        // 批量更新
        subjectLikedService.updateSubjectLikedList(subjectUpdateLikedList);
    }

    /**
     * @author: 32115
     * @description: 获取这个题目被点赞的数量
     * @date: 2024/5/31
     * @param: subjectId
     * @return: Integer
     */
    @Override
    @AopLogAnnotations
    public Integer getLikedCount(String subjectId) {
        // 创建存储题目点赞数量的key
        String countKey = redisUtil.buildKey(
                ".",
                SubjectConstant.SUBJECT_LIKED_COUNT_KEY,
                subjectId
        );
        // 获取点赞数量
        Integer count = redisUtil.getHashCount(countKey);
        if (Objects.isNull(count) || count <= 0){
            return 0;
        }
        return count;
    }

    /**
     * @author: 32115
     * @description: 添加点赞信息
     * @date: 2024/5/31
     * @param: subjectLikedBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public void addSubjectLiked(SubjectLikedBO subjectLikedBO) {
        // 创建hashKey
        String hashKey = redisUtil.buildKey(
                ":",
                subjectLikedBO.getSubjectId().toString(),
                subjectLikedBO.getLikeUserId()
        );
        // 创建存储题目点赞数量的key
        String countKey = redisUtil.buildKey(
                ".",
                SubjectConstant.SUBJECT_LIKED_COUNT_KEY,
                subjectLikedBO.getSubjectId().toString()
        );
        // 创建存储哪个用户那个个题目点的赞的key
        String detailKey = redisUtil.buildKey(
                ".",
                SubjectConstant.SUBJECT_LIKED_DETAIL_KEY,
                subjectLikedBO.getSubjectId().toString(),
                subjectLikedBO.getLikeUserId()
        );
        // 将题目点赞的状态信息添加到redis
        // SubjectConstant.SUBJECT_LIKED_HASH_KEY 表示总的key
        // hashKey 表示SubjectConstant.SUBJECT_LIKED_HASH_KEY下的每条数据的key
        // subjectLikedBO.getStatus() 表示SubjectConstant.SUBJECT_LIKED_HASH_KEY下的每条数据的value
        redisUtil.setHash(
                SubjectConstant.SUBJECT_LIKED_HASH_KEY,
                hashKey,
                subjectLikedBO.getStatus()
        );
        // 在redis中添加该题目点赞的数量
        // 如果对这个题目做的是点赞操作 将题目的点赞数量+1并设置哪个用户那个个题目点的赞
        if (SubjectLikedStatusEnum.LIKED.getCode().equals(subjectLikedBO.getStatus())) {
            // 存储题目的点赞数量
            redisUtil.incrementHashCount(countKey, 1);
            // 设置哪个用户那个个题目点的赞
            redisUtil.set(detailKey, "1");
        } else {
            // 如果对这个题目做的是取消点赞操作 将题目的点赞数量-1并删除哪个用户那个个题目点的赞
            // 首先获取已经存储的题目被点赞的数量 判断是否等于零 避免出现负数
            if (Objects.isNull(redisUtil.getHashCount(countKey)) || redisUtil.getHashCount(countKey) <= 0){
                return;
            }
            // 如果有值并且不为零 就将数量-1并移除点赞信息
            redisUtil.incrementHashCount(countKey, -1);
            redisUtil.del(detailKey);
        }
    }
}
