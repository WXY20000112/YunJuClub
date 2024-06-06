package com.wxy.subject.domain.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.wxy.subject.common.aop.AopLogAnnotations;
import com.wxy.subject.common.constant.SubjectConstant;
import com.wxy.subject.common.utils.IdWorkerUtil;
import com.wxy.subject.common.utils.RedisUtil;
import com.wxy.subject.common.utils.ThreadLocalUtil;
import com.wxy.subject.domain.converter.SubjectInfoBOConverter;
import com.wxy.subject.domain.entity.SubjectFactoryBO;
import com.wxy.subject.domain.entity.SubjectInfoBO;
import com.wxy.subject.domain.handler.SubjectTypeFactory;
import com.wxy.subject.domain.handler.SubjectTypeHandler;
import com.wxy.subject.domain.service.SubjectInfoDomainService;
import com.wxy.subject.domain.service.SubjectLikedDomainService;
import com.wxy.subject.infra.entity.SubjectInfo;
import com.wxy.subject.infra.entity.SubjectLabel;
import com.wxy.subject.infra.entity.SubjectMapping;
import com.wxy.subject.infra.es.entity.SubjectInfoElasticsearch;
import com.wxy.subject.infra.es.service.SubjectInfoElasticsearchService;
import com.wxy.subject.infra.rpc.entity.AuthUser;
import com.wxy.subject.infra.rpc.feign.AuthUserRpc;
import com.wxy.subject.infra.service.SubjectInfoService;
import com.wxy.subject.infra.service.SubjectLabelService;
import com.wxy.subject.infra.service.SubjectMappingService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @program: YunJuClub-Flex
 * @description: SubjectInfoDomainServiceImpl实现类
 * @author: 32115
 * @create: 2024-05-13 12:01
 */
@Service
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectTypeFactory subjectTypeFactory;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectInfoElasticsearchService subjectInfoElasticsearchService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthUserRpc authUserRpc;

    @Resource
    private SubjectLikedDomainService subjectLikedDomainService;

    /**
     * @author: 32115
     * @description: 根据题目id获取题目信息
     * @date: 2024/6/6
     * @param: subjectId
     * @return: SubjectInfoBO
     */
    @Override
    @AopLogAnnotations
    public SubjectInfoBO getSubjectInfoById(SubjectInfoBO subjectInfoBO) {
        return getSubjectInfoBO(subjectInfoBO);
    }

    /**
     * @author: 32115
     * @description: 查询题目和选项信息
     * @date: 2024/6/6
     * @param: subjectInfoBO
     * @return: SubjectInfoBO
     */
    private SubjectInfoBO getSubjectInfoBO(SubjectInfoBO subjectInfoBO) {
        // 首先查询题目主表信息
        SubjectInfo subjectInfo = subjectInfoService.getSubjectInfoById(subjectInfoBO.getId());
        // 根据题目类型获取对应的处理器
        SubjectTypeHandler handler = subjectTypeFactory.getHandler(subjectInfo.getSubjectType());
        // 获取题目选项信息
        SubjectFactoryBO subjectFactoryBO = handler.getBySubjectId(subjectInfo.getId());
        // 封装BO
        // 将factoryBo和Info转为Bo
        return SubjectInfoBOConverter.CONVERTER
                .convertInfoAndFactoryBoToBo(subjectFactoryBO, subjectInfo);
    }

    /**
     * @author: 32115
     * @description: 获取题目列表
     * @date: 2024/6/4
     * @param: subjectCount
     * @param: subjectType
     * @param: excludeSubjectIds
     * @return: List<SubjectInfoBO>
     */
    @Override
    @AopLogAnnotations
    public List<SubjectInfoBO> getSubjectInfoList(
            Integer subjectCount, Integer subjectType,
            List<Long> excludeSubjectIds, List<String> assembleIds) {
        // 调用service层获取题目列表
        List<SubjectInfo> subjectInfoList = subjectInfoService
                .getSubjectInfoList(subjectCount, subjectType, excludeSubjectIds, assembleIds);
        // entity -> bo
        return SubjectInfoBOConverter.CONVERTER
                .converterInfoListToBoList(subjectInfoList);
    }

    /**
     * @author: 32115
     * @description: 获取贡献列表
     * @date: 2024/5/31
     * @return: List<SubjectInfoBO>
     */
    @Override
    @AopLogAnnotations
    public List<SubjectInfoBO> getContributeList() {
        // Redis获取贡献列表
        Set<ZSetOperations.TypedTuple<String>> typedTuples = redisUtil
                .rankWithScore(
                        SubjectConstant.SUBJECT_RANK_KEY,
                        SubjectConstant.SUBJECT_RANK_START,
                        SubjectConstant.SUBJECT_RANK_END
                );
        // 如果没有查到结果就返回空列表
        if (CollectionUtils.isEmpty(typedTuples)) return Collections.emptyList();
        // 封装需要返回的信息
        return typedTuples.stream().map(
                typedTuple -> {
                    SubjectInfoBO subjectInfoBO = new SubjectInfoBO();
                    // 设置出题量
                    subjectInfoBO.setSubjectCount(Objects.requireNonNull(typedTuple.getScore()).intValue());
                    // 通过openfeign调用获取用户信息
                    AuthUser userInfo = authUserRpc.getUserInfo(typedTuple.getValue());
                    // 设置用户昵称
                    subjectInfoBO.setCreateUser(userInfo.getNickName());
                    // 设置用户头像
                    subjectInfoBO.setCreateUserAvatar(userInfo.getAvatar());
                    return subjectInfoBO;
                }
        ).toList();
    }

    /**
     * @author: 32115
     * @description: 全文检索
     * @date: 2024/5/30
     * @param: subjectInfoBO
     * @return: Page<SubjectInfoElasticsearch>
     */
    @Override
    @AopLogAnnotations
    public Page<SubjectInfoElasticsearch> getSubjectPageByElasticsearch(SubjectInfoBO subjectInfoBO) {
        // 封装参数
        SubjectInfoElasticsearch subjectInfoEs = new SubjectInfoElasticsearch();
        // 分页信息
        subjectInfoEs.setPageNo(subjectInfoBO.getPageNo());
        subjectInfoEs.setPageSize(subjectInfoBO.getPageSize());
        subjectInfoEs.setKeyWord(subjectInfoBO.getKeyWord());
        // 查询
        return subjectInfoElasticsearchService.getSubjectPageList(subjectInfoEs);
    }

    /**
     * @author: 32115
     * @description: 查询题目详情
     * @date: 2024/5/16
     * @param: subjectInfoBO
     * @return: SubjectInfoBO
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public SubjectInfoBO getSubjectInfo(SubjectInfoBO subjectInfoBO) {
        SubjectInfoBO boResult = getSubjectInfoById(subjectInfoBO);
        // 查询题目的标签信息
        List<SubjectLabel> subjectLabelList = subjectLabelService
                .getLabelBySubjectId(boResult.getId());
        // 封装labelNameList
        boResult.setLabelNameList(subjectLabelList.stream()
                .map(SubjectLabel::getLabelName).toList());
        // 从redis中查询点赞信息
        // 判断当前用户有没有点赞过这个题目
        boResult.setLiked(subjectLikedDomainService
                .isLiked(String.valueOf(subjectInfoBO.getId()), ThreadLocalUtil.getLoginId()));
        // 获取点赞数量
        boResult.setLikedCount(subjectLikedDomainService
                .getLikedCount(String.valueOf(subjectInfoBO.getId())));
        // 获取题目的上一题的id和下一题的id
        // 如果没有分类id或者标签id就返回
        if (subjectInfoBO.getCategoryId() == null || subjectInfoBO.getLabelId() == null)
            // 返回
            return boResult;
        // 查询上一题
        Long lastSubjectId = subjectInfoService.getLastSubjectId(
                subjectInfoBO.getCategoryId(),
                subjectInfoBO.getLabelId(),
                subjectInfoBO.getId(), 0
        );
        // 查下一题
        Long nextSubjectId = subjectInfoService.getLastSubjectId(
                subjectInfoBO.getCategoryId(),
                subjectInfoBO.getLabelId(),
                subjectInfoBO.getId(), 1
        );
        // 封装
        boResult.setNextSubjectId(nextSubjectId);
        boResult.setLastSubjectId(lastSubjectId);
        // 返回
        return boResult;
    }

    /**
     * @author: 32115
     * @description: 分页查询题目列表
     * @date: 2024/5/16
     * @param: subjectInfoBO
     * @return: Page<SubjectInfoBO>
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Page<SubjectInfoBO> getSubjectPageList(SubjectInfoBO subjectInfoBO) {
        // BO 转换成 info实体类
        SubjectInfo subjectInfo = SubjectInfoBOConverter
                .CONVERTER.converterBoToInfo(subjectInfoBO);
        // 调用方法进行分页查询
        Page<SubjectInfo> subjectInfoPage = subjectInfoService
                .getSubjectPageList(subjectInfoBO.getPageNo(), subjectInfoBO.getPageSize(),
                        subjectInfoBO.getCategoryId(), subjectInfoBO.getLabelId(), subjectInfo);
        // SubjectInfoPage 转换成 SubjectInfoBOPage
        return SubjectInfoBOConverter
                .CONVERTER.converterInfoPageToBoPage(subjectInfoPage);
    }

    /**
     * @author: 32115
     * @description: 添加题目
     * @date: 2024/5/15
     * @param: subjectInfoBO
     * @return: Boolean
     */
    @Override
    @Transactional
    @AopLogAnnotations
    public Boolean addSubject(SubjectInfoBO subjectInfoBO) {
        // Bo 转换成 info实体类
        SubjectInfo subjectInfo = SubjectInfoBOConverter
                .CONVERTER.converterBoToInfo(subjectInfoBO);
        // 先将主表信息插入
        Boolean infoResult = subjectInfoService.addSubjectInfo(subjectInfo);
        // 工厂+策略模式进行单选、多选、判断、简答的添加
        // 一个工厂 4中模式 根据传入的type值不同自动选择合适的映射类进行添加
        // 通过工厂类传入type获取对应handler 执行相应添加方法
        SubjectTypeHandler handler = subjectTypeFactory
                .getHandler(subjectInfoBO.getSubjectType());
        // 将主表添加后的id赋值给BO
        subjectInfoBO.setId(subjectInfo.getId());
        // 获取到handler后调用添加方法添加题目信息
        Boolean handlerResult = handler.addSubject(subjectInfoBO);
        // 接下来添加题目、分类、标签之间的关联关系
        List<SubjectMapping> subjectMappingList = new ArrayList<>();
        subjectInfoBO.getCategoryIds().forEach(categoryId ->
                subjectInfoBO.getLabelIds().forEach(labelId -> {
                    SubjectMapping subjectMapping = new SubjectMapping();
                    // 设置分类id、题目id、标签id
                    subjectMapping.setCategoryId(categoryId);
                    subjectMapping.setSubjectId(subjectInfo.getId());
                    subjectMapping.setLabelId(labelId);
                    subjectMappingList.add(subjectMapping);
                }));
        // 批量插入关联关系
        Boolean mappingResult = subjectMappingService.addSubjectMapping(subjectMappingList);

        // 同步es
        SubjectInfoElasticsearch subjectInfoElasticsearch = new SubjectInfoElasticsearch();
        subjectInfoElasticsearch.setDocId(new IdWorkerUtil(1, 1, 1).nextId());
        subjectInfoElasticsearch.setSubjectId(subjectInfo.getId());
        subjectInfoElasticsearch.setSubjectAnswer(subjectInfoBO.getSubjectAnswer());
        subjectInfoElasticsearch.setCreateTime(new Date().getTime());
        subjectInfoElasticsearch.setCreateUser(ThreadLocalUtil.getLoginId());
        subjectInfoElasticsearch.setSubjectName(subjectInfo.getSubjectName());
        subjectInfoElasticsearch.setSubjectType(subjectInfo.getSubjectType());
        Boolean insertEs = subjectInfoElasticsearchService.insert(subjectInfoElasticsearch);

        // 放入redis 计入排行榜
        redisUtil.addScore(SubjectConstant.SUBJECT_RANK_KEY, ThreadLocalUtil.getLoginId(), 1);

        return infoResult && handlerResult && mappingResult && insertEs;
    }
}
