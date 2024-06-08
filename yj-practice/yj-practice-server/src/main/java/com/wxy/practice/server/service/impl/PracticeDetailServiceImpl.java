package com.wxy.practice.server.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.auth.api.entity.AuthUserDto;
import com.wxy.practice.api.enums.SubjectTypeEnum;
import com.wxy.practice.api.req.*;
import com.wxy.practice.api.vo.*;
import com.wxy.practice.server.aop.AopLogAnnotations;
import com.wxy.practice.server.dto.PracticeSubjectDTO;
import com.wxy.practice.server.entity.*;
import com.wxy.practice.server.enums.AnswerStatusEnum;
import com.wxy.practice.server.enums.PracticeSetEnum;
import com.wxy.practice.server.enums.SubjectIsAnswerEnum;
import com.wxy.practice.server.mapper.PracticeDetailMapper;
import com.wxy.practice.server.rpc.feign.AuthUserRpc;
import com.wxy.practice.server.rpc.feign.SubjectRpc;
import com.wxy.practice.server.service.PracticeDetailService;
import com.wxy.practice.server.service.PracticeInfoService;
import com.wxy.practice.server.service.PracticeSetDetailService;
import com.wxy.practice.server.service.PracticeSetService;
import com.wxy.practice.server.utils.DateUtils;
import com.wxy.practice.server.utils.ThreadLocalUtil;
import com.wxy.subject.api.entity.SubjectInfoDto;
import com.wxy.subject.api.entity.SubjectLabelDto;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.wxy.practice.server.entity.table.PracticeDetailTableDef.PRACTICE_DETAIL;

/**
 * @program: YunJuClub-Flex
 * @description: PracticeDetailServiceImpl
 * @author: 32115
 * @create: 2024-06-05 15:18
 */
@Service
@EnableAspectJAutoProxy(exposeProxy = true)
public class PracticeDetailServiceImpl
            extends ServiceImpl<PracticeDetailMapper, PracticeDetail>
            implements PracticeDetailService {

    @Resource
    private PracticeInfoService practiceInfoService;

    @Resource
    private SubjectRpc subjectRpc;

    @Resource
    private PracticeSetDetailService practiceSetDetailService;

    @Lazy
    @Resource
    private PracticeSetService practiceSetService;

    @Resource
    private PracticeDetailMapper practiceDetailMapper;

    @Resource
    private AuthUserRpc authUserRpc;

    /**
     * @author: 32115
     * @description: 放弃练习
     * @date: 2024/6/8
     * @param: practiceId
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean giveUp(Long practiceId) {
        // 放弃练习即删除practice_info表的套卷练习记录和practice_detail表的题目练习记录
        practiceInfoService.deleteById(practiceId);
        // 构建查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select()
                .from(PRACTICE_DETAIL)
                .where(PRACTICE_DETAIL.PRACTICE_ID.eq(practiceId));
        practiceDetailMapper.deleteByQuery(queryWrapper);
        return true;
    }

    /**
     * @author: 32115
     * @description: 获取练习排行榜
     * @date: 2024/6/8
     * @return: List<RankVO>
     */
    @Override
    @AopLogAnnotations
    public List<RankVO> getPracticeRankList() {
        // 创建返回对象
        List<RankVO> rankVOList = new LinkedList<>();
        // 根据出题人分组使用count函数统计前五名的出题量
        List<PracticeRank> practiceRankList =
                practiceDetailMapper.getPracticeCount();
        if (CollectionUtils.isEmpty(practiceRankList)) return rankVOList;
        // 遍历封装需要返回的对象信息
        practiceRankList.forEach(practiceRank -> {
            RankVO rankVO = new RankVO();
            rankVO.setCount(practiceRank.getCount());
            // 调用rpc查询练题用户信息
            AuthUserDto authUserDto = authUserRpc
                    .getUserInfo(practiceRank.getCreatedBy());
            // 设置用户昵称和头像
            rankVO.setName(authUserDto.getNickName());
            rankVO.setAvatar(authUserDto.getAvatar());
            rankVOList.add(rankVO);
        });
        // 返回
        return rankVOList;
    }

    /**
     * @author: 32115
     * @description: 获取练习评估报告
     * @date: 2024/6/8
     * @param: req
     * @return: ReportVO
     */
    @Override
    @AopLogAnnotations
    public ReportVO getReport(GetReportReq req) {
        // 创建返回对象
        ReportVO reportVO = new ReportVO();
        // 查询练习记录
        PracticeInfo practiceInfo =
                practiceInfoService.getById(req.getPracticeId());
        // 查询套卷信息
        PracticeSet practiceSet =
                practiceSetService.getById(practiceInfo.getSetId());
        // 设置报告标题
        reportVO.setTitle(practiceSet.getSetName());
        // 根据练习id查询题目练习情况
        List<PracticeDetail> practiceDetailList = getByPracticeId(req.getPracticeId());
        if (CollectionUtils.isEmpty(practiceDetailList)) return reportVO;
        // 使用stream工具过滤出答对的题目
        List<PracticeDetail> correctPracticeDetailList = practiceDetailList
                .stream().filter(practiceDetail ->
                        practiceDetail.getAnswerStatus() == AnswerStatusEnum.CORRECT.getCode())
                .toList();
        // 设置练习报告正确题目数量
        reportVO.setCorrectSubject(
                correctPracticeDetailList.size() + "/" + practiceDetailList.size());
        List<ReportSkillVO> reportSkillVOS = new LinkedList<>();
        // 存储所有练习题所属标签中每个标签对应的题目数量
        Map<Long, Integer> totalMap = getSubjectLabelMap(practiceDetailList);
        // 存储所有正确题目所属标签中每个标签对应的题目数量
        Map<Long, Integer> correctMap = getSubjectLabelMap(correctPracticeDetailList);
        // 遍历map 封装reportSkillVOS信息
        totalMap.forEach((key, val) -> {
            ReportSkillVO skillVO = new ReportSkillVO();
            // 调用rpc根据id查询标签信息
            SubjectLabelDto labelDto = subjectRpc.getLabelById(key);
            // 获取该标签对应的正确的题目数量 如果没有就设为0
            Integer correctCount = correctMap.get(key);
            if (Objects.isNull(correctCount)) correctCount = 0;
            // 设置标签名称
            skillVO.setName(labelDto.getLabelName());
            // 初始化正确率为0.00
            BigDecimal rate = BigDecimal.ZERO;
            // 如果该标签对应的题目数量不为0 计算正确率
            if (!Objects.equals(val, 0)) {
                rate = new BigDecimal(correctCount.toString())
                        .divide(new BigDecimal(val.toString()), 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal(100));
            }
            // 设置分数
            skillVO.setStar(rate);
            // 添加到返回对象中
            reportSkillVOS.add(skillVO);
        });
        // 设置报告题目技能图谱
        reportVO.setSkill(reportSkillVOS);
        // 返回
        return reportVO;
    }

    /**
     * @author: 32115
     * @description: 将标签对应的提数量封装成map
     * @date: 2024/6/8
     * @param: practiceDetailList
     * @return: Map<Long, Integer>
     */
    private Map<Long, Integer> getSubjectLabelMap(List<PracticeDetail> practiceDetailList) {
        if (CollectionUtils.isEmpty(practiceDetailList)) return Collections.emptyMap();
        // 创建map
        Map<Long, Integer> map = new HashMap<>();
        // 遍历list查询标签id 并统计标签对应的题目数量
        practiceDetailList.forEach(practiceDetail -> {
            // 调用rpc根据题目id查询标签id
            List<Long> labelIdList = subjectRpc
                    .getLabelIdsBySubjectId(practiceDetail.getSubjectId());
            labelIdList.forEach(labelId -> {
                if (Objects.isNull(map.get(labelId))) {
                    // 如果map中不存在该标签id 则添加该标签id 并设置题目数量为1
                    map.put(labelId, 1);
                    return;
                }
                // 如果map中存在该标签id 则题目数量+1
                map.put(labelId, map.get(labelId) + 1);
            });
        });
        // 返回
        return map;
    }

    /**
     * @author: 32115
     * @description: 获取题目答题详情
     * @date: 2024/6/8
     * @param: req
     * @return: SubjectDetailVO
     */
    @Override
    @AopLogAnnotations
    public SubjectDetailVO getSubjectDetail(GetSubjectDetailReq req) {
        // 创建返回对象
        SubjectDetailVO subjectDetailVO = new SubjectDetailVO();

        // 调用rpc查询题目信息
        PracticeSubjectDTO practiceSubjectDTO = new PracticeSubjectDTO();
        practiceSubjectDTO.setSubjectId(req.getSubjectId());
        practiceSubjectDTO.setSubjectType(req.getSubjectType());
        SubjectInfoDto subjectInfoDto =
                subjectRpc.getSubjectInfoById(practiceSubjectDTO);
        // 创建list封装选项信息
        List<PracticeSubjectOptionVO> optionVOList = new ArrayList<>();
        // 存储正确答案type
        List<Integer> correctAnswer = new ArrayList<>();
        // 判断题结构不同 单独做处理
        if (req.getSubjectType() == SubjectTypeEnum.JUDGE.getCode()){
            Integer isCorrect = subjectInfoDto.getOptionList().getFirst().getIsCorrect();
            // 构建正确答案
            PracticeSubjectOptionVO correctOption = new PracticeSubjectOptionVO();
            correctOption.setOptionType(1);
            correctOption.setOptionContent("正确");
            correctOption.setIsCorrect(isCorrect == 1 ? 1 : 0);
            // 构建错误答案
            PracticeSubjectOptionVO errorOptionVO = new PracticeSubjectOptionVO();
            errorOptionVO.setOptionType(2);
            errorOptionVO.setOptionContent("错误");
            errorOptionVO.setIsCorrect(isCorrect == 0 ? 1 : 0);
            // 添加到list中
            optionVOList.add(correctOption);
            optionVOList.add(errorOptionVO);
            correctAnswer.add(isCorrect);
        } else {
            // 将单选和多选选项信息进行处理
            subjectInfoDto.getOptionList().forEach(option -> {
                PracticeSubjectOptionVO optionVO = new PracticeSubjectOptionVO();
                optionVO.setOptionType(option.getOptionType());
                optionVO.setOptionContent(option.getOptionContent());
                optionVO.setIsCorrect(option.getIsCorrect());
                optionVOList.add(optionVO);
                if (option.getIsCorrect() == 1) {
                    // 将正确答案添加到list中
                    correctAnswer.add(option.getOptionType());
                }
            });
        }

        // 封装返回对象
        subjectDetailVO.setOptionList(optionVOList);
        subjectDetailVO.setSubjectParse(subjectInfoDto.getSubjectParse());
        subjectDetailVO.setSubjectName(subjectInfoDto.getSubjectName());
        subjectDetailVO.setCorrectAnswer(correctAnswer);
        subjectDetailVO.setLabelNames(subjectInfoDto.getLabelNameList());

        // 查询用户的答案
        List<Integer> respondAnswer = new LinkedList<>();
        // 查询题目答题情况
        PracticeDetail practiceDetail =
                getPracticeDetailByPracticeIdAndSubjectId(
                        req.getPracticeId(), req.getSubjectId(), null);
        // 获取题目答案
        String answerContent = practiceDetail.getAnswerContent();
        if (StringUtils.isNotBlank(answerContent)) {
            String[] split = answerContent.split(",");
            for (String s : split) {
                respondAnswer.add(Integer.valueOf(s));
            }
        }
        subjectDetailVO.setRespondAnswer(respondAnswer);
        return subjectDetailVO;
    }

    /**
     * @author: 32115
     * @description: 获取每题得分情况
     * @date: 2024/6/8
     * @param: req
     * @return: List<ScoreDetailVO>
     */
    @Override
    @AopLogAnnotations
    public List<ScoreDetailVO> getScoreDetail(GetScoreDetailReq req) {
        // 根据practiceId查询当前用户已答的所有题目信息
        List<PracticeDetail> practiceDetailList = getByPracticeId(req.getPracticeId());
        // 如果为空返回空集合
        if (CollectionUtils.isEmpty(practiceDetailList)) return Collections.emptyList();
        // 封装要返回的数据
        return practiceDetailList.stream().map(practiceDetail -> {
            ScoreDetailVO scoreDetailVO = new ScoreDetailVO();
            scoreDetailVO.setSubjectId(practiceDetail.getSubjectId());
            scoreDetailVO.setSubjectType(practiceDetail.getSubjectType());
            scoreDetailVO.setIsCorrect(practiceDetail.getAnswerStatus());
            return scoreDetailVO;
        }).toList();
    }

    /**
     * @author: 32115
     * @description: 根据练习id查询题目信息
     * @date: 2024/6/8
     * @param: practiceId
     * @return: List<PracticeDetail>
     */
    @Override
    public List<PracticeDetail> getByPracticeId(Long practiceId) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_DETAIL)
                .where(PRACTICE_DETAIL.PRACTICE_ID.eq(practiceId));
        return this.list(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 提交练习详情
     * @date: 2024/6/8
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    public Boolean submitPracticeDetail(SubmitPracticeDetailReq req) {
        // 使用上下文工具类获取当前对象的代理类@EnableAspectJAutoProxy (exposeProxy = true)
        // 然后通过下面方法获取代理对象，然后再调用 可以避免方法自调用造成的Transactional事务失效
        PracticeDetailServiceImpl proxy = (PracticeDetailServiceImpl) AopContext.currentProxy();

        // 创建对象 保存需要修改或者插入的数据
        PracticeInfo practiceInfo = new PracticeInfo();
        // 设置套卷id
        practiceInfo.setId(req.getSetId());

        // 处理答题用时格式
        String hour = StringUtils.substring(req.getTimeUse(), 0, 2);
        String minute = StringUtils.substring(req.getTimeUse(), 2, 4);
        String second = StringUtils.substring(req.getTimeUse(), 4, 6);
        practiceInfo.setTimeUse(hour + ":" + minute + ":" + second);

        // 转换日期格式并设置提交时间
        practiceInfo.setSubmitTime(DateUtils.parseStrToDate(req.getSubmitTime()));
        // 设置练习已完成
        practiceInfo.setCompleteStatus(PracticeSetEnum.COMPLETE.getCode());

        // 计算答题的正确率
        Integer correctCount = getCorrectCount(req);
        // 在practice_set_detail表中查出该套卷下的所有题目
        List<PracticeSetDetail> practiceSetDetailList =
                practiceSetDetailService.getPracticeSetDetailList(req.getSetId());
        // 获取所有题目数量
        int totalCount = practiceSetDetailList.size();
        // 计算正确率并设置到practiceInfo对象中
        BigDecimal correctRate = new BigDecimal(correctCount)
                .divide(new BigDecimal(totalCount), 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100.00"));
        practiceInfo.setCorrectRate(correctRate);
        // 查询当前记录是否已存在 若存在说明不是第一次提交修改即可 否则新增
        PracticeInfo existPracticeInfo =
                practiceInfoService.getById(req.getPracticeId());
        if (Objects.isNull(existPracticeInfo)){
            proxy.savePracticeInfo(practiceInfo);
        } else {
            practiceInfo.setId(existPracticeInfo.getId());
            proxy.updatePractice(practiceInfo);
        }
        // 提交之后将套卷的热度加一
        practiceSetService.updatePracticeSetHeat(req.getSetId());

        // 接下来添加用户没有做过的题目信息
        // 分析：因为用户可以提前交卷 这样的话后面没有做的题目数据就不会添加到数据库
        // 之所以没有做的题添加不到数据库是因为我们答题记录保存方式采用的是一题提交一次
        // 就是用户每做完一道题点击下一题时都会发送请求将这一题的数据保存到数据 所以没做的题自然也不会保存到数据库
        // 因此我们就需要使用stream工具将用户已答的题目和套卷下所有题目求差集 这样就可以得到所有未作的题目 然后保存到数据库
        // 首先查询用户已答的题目
        List<PracticeDetail> practiceDetailList = getByPracticeId(req.getPracticeId());
        // 求差集
        List<PracticeSetDetail> differenceList = practiceSetDetailList.stream().filter(
                practiceSetDetail -> practiceDetailList.stream().noneMatch(
                        practiceDetail -> practiceDetail.getSubjectId()
                                .equals(practiceSetDetail.getSubjectId())))
                .toList();
        // 封装差集信息
        List<PracticeDetail> delPracticeDetailList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(differenceList)){
            differenceList.forEach(practiceSetDetail -> {
                PracticeDetail practiceDetail = new PracticeDetail();
                practiceDetail.setPracticeId(req.getPracticeId());
                practiceDetail.setSubjectType(practiceSetDetail.getSubjectType());
                practiceDetail.setSubjectId(practiceSetDetail.getSubjectId());
                practiceDetail.setAnswerStatus(SubjectIsAnswerEnum.UN_ANSWERED.getCode());
                practiceDetail.setAnswerContent("");
                delPracticeDetailList.add(practiceDetail);
            });
        }
        // 批量添加
        proxy.insertBatchPracticeDetail(delPracticeDetailList);
        return true;
    }

    /**
     * @author: 32115
     * @description: 批量添加题目信息
     * @date: 2024/6/8
     * @param: delPracticeDetailList
     * @return: void
     */
    @Transactional
    public void insertBatchPracticeDetail(List<PracticeDetail> delPracticeDetailList) {
        this.saveBatch(delPracticeDetailList);
    }

    /**
     * @author: 32115
     * @description: 修改套卷练习记录
     * @date: 2024/6/8
     * @param: practiceInfo
     * @return: void
     */
    @Transactional
    public void updatePractice(PracticeInfo practiceInfo) {
        practiceInfoService.updateById(practiceInfo);
    }

    /**
     * @author: 32115
     * @description: 保存套卷练习记录
     * @date: 2024/6/8
     * @param: practiceInfo
     * @return: void
     */
    @Transactional
    public void savePracticeInfo(PracticeInfo practiceInfo) {
        practiceInfoService.save(practiceInfo);
    }

    /**
     * @author: 32115
     * @description: 获取正确的题目数量
     * @date: 2024/6/8
     * @param: req
     * @return: Integer
     */
    private Integer getCorrectCount(SubmitPracticeDetailReq req) {
        // 在practice_detail表中查出答题状态为正确的题目数量
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_DETAIL)
                .where(PRACTICE_DETAIL.PRACTICE_ID.eq(req.getPracticeId()))
                .and(PRACTICE_DETAIL.ANSWER_STATUS.eq(1));
        return Math.toIntExact(this.count(queryWrapper));
    }

    /**
     * @author: 32115
     * @description: 提交题目详情
     * @date: 2024/6/7
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    public Boolean submitSubjectDetail(SubmitSubjectDetailReq req) {
        // 使用上下文工具类获取当前对象的代理类@EnableAspectJAutoProxy (exposeProxy = true)
        // 然后通过下面方法获取代理对象，然后再调用 可以避免方法自调用造成的Transactional事务失效
        PracticeDetailServiceImpl proxy = (PracticeDetailServiceImpl) AopContext.currentProxy();
        proxy.updatePracticeInfo(req);

        // 接下来处理需要更新到practice_detail表的信息
        PracticeDetail practiceDetail = new PracticeDetail();
        practiceDetail.setPracticeId(req.getPracticeId());
        practiceDetail.setSubjectId(req.getSubjectId());
        practiceDetail.setSubjectType(req.getSubjectType());
        // 创建answerContent字符串用于存储排序过后的用户提交的答案
        String answerContent = "";
        // 如果用户的答案不为空就对答案进行排序
        if (CollectionUtils.isNotEmpty(req.getAnswerContents())){
            // 使用Collections工具对用户答案进行排序
            List<Integer> answerContents = req.getAnswerContents();
            Collections.sort(answerContents);
            // 将排序后的用户答案使用逗号进行分隔
            answerContent = StringUtils.join(answerContents, ",");
        }
        // 将用户答案封装到practiceDetail对象中
        practiceDetail.setAnswerContent(answerContent);
        // 接下来在Subject表中将该道题目的正确答案查询出来
        PracticeSubjectDTO practiceSubjectDTO = new PracticeSubjectDTO();
        practiceSubjectDTO.setSubjectId(req.getSubjectId());
        practiceSubjectDTO.setSubjectType(req.getSubjectType());
        SubjectInfoDto subjectInfoDto =
                subjectRpc.getSubjectInfoById(practiceSubjectDTO);
        // 创建StringBuilder存储正确的答案
        String correctAnswer;
        // 如果是判断题只需要加入isCorrect字段即可 单选和多选因为有多个选项所以需要遍历链表
        if (req.getSubjectType().equals(SubjectTypeEnum.JUDGE.getCode())){
            correctAnswer = String.valueOf(
                    subjectInfoDto.getOptionList().getFirst().getIsCorrect());
        } else {
            List<Integer> optionTypes = new ArrayList<>();
            subjectInfoDto.getOptionList().forEach(option -> {
                // 在数据库中正确的答案被标记为1 所以只需要将正确的答案的optionType加入到链表中即可
                if (option.getIsCorrect().equals(1)){
                    optionTypes.add(option.getOptionType());
                }
            });
            // 对正确答案进行排序
            Collections.sort(optionTypes);
            correctAnswer = StringUtils.join(optionTypes, ",");
        }
        // 将正确答案和用户答案进行比对 如果正确就将用户这一题的答案状态设置为正确
        if (Objects.equals(correctAnswer, answerContent)){
            practiceDetail.setAnswerStatus(1);
        } else {
            practiceDetail.setAnswerStatus(0);
        }

        // 接下来查询这道题的练习情况在数据库中是否已存在
        // 如果已存在说明用户已经答过 这次提交是修改答案所以只需修改记录即可
        // 如果不存在说明用户第一次答这道题 所以需要新增一条记录
        PracticeDetail existPracticeDetail = existPracticeDetail(req);
        if (Objects.isNull(existPracticeDetail)){
            proxy.savePracticeDetail(practiceDetail);
        } else {
            // 根据id进行修改
            practiceDetail.setId(existPracticeDetail.getId());
            proxy.updatePracticeDetail(practiceDetail);
        }
        return true;
    }

    /**
     * @author: 32115
     * @description: 修改题目练习记录
     * @date: 2024/6/7
     * @param: practiceDetail
     * @return: void
     */
    @Transactional
    public void updatePracticeDetail(PracticeDetail practiceDetail) {
        this.updateById(practiceDetail);
    }

    /**
     * @author: 32115
     * @description: 新增题目练习记录
     * @date: 2024/6/7
     * @param: practiceDetail
     * @return: void
     */
    @Transactional
    public void savePracticeDetail(PracticeDetail practiceDetail) {
        this.save(practiceDetail);
    }

    /**
     * @author: 32115
     * @description: 提交题目时更新practice_info表
     * @date: 2024/6/7
     * @param: req
     * @return: void
     */
    @Transactional
    public void updatePracticeInfo(SubmitSubjectDetailReq req) {
        // 首先对答题用时的格式进行处理
        String timeUse = req.getTimeUse();
        if (timeUse.equals("0")) timeUse = "000000";
        // 将用时使用冒号分割
        String hour = timeUse.substring(0, 2);
        String minute = timeUse.substring(2, 4);
        String second = timeUse.substring(4, 6);
        // 封装需要更新的联系记录信息 提交题目是也要对练习记录表中的信息进行更新
        PracticeInfo practiceInfo = new PracticeInfo();
        practiceInfo.setId(req.getPracticeId());
        practiceInfo.setTimeUse(hour + ":" + minute + ":" + second);
        practiceInfo.setSubmitTime(new Date());
        // 根据id进行更新
        practiceInfoService.updateById(practiceInfo);
    }

    /**
     * @author: 32115
     * @description: 根据题目id 练习id 操作用户条件判断数据是否已存在
     * @date: 2024/6/7
     * @param: req
     * @return: PracticeDetail
     */
    private PracticeDetail existPracticeDetail(SubmitSubjectDetailReq req) {
        // 构造查询条件
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_DETAIL)
                .where(PRACTICE_DETAIL.PRACTICE_ID.eq(req.getPracticeId()))
                .and(PRACTICE_DETAIL.SUBJECT_ID.eq(req.getSubjectId()))
                .and(PRACTICE_DETAIL.CREATED_BY.eq(ThreadLocalUtil.getLoginId()));
        return this.getOne(queryWrapper);
    }

    /**
     * @author: 32115
     * @description: 根据练习id和题目id查询练习详情
     * @date: 2024/6/5
     * @param: practiceId
     * @param: subjectId
     * @param: loginId
     * @return: PracticeDetail
     */
    @Override
    public PracticeDetail getPracticeDetailByPracticeIdAndSubjectId(
            Long practiceId, Long subjectId, String loginId) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRACTICE_DETAIL.DEFAULT_COLUMNS)
                .from(PRACTICE_DETAIL)
                .where(PRACTICE_DETAIL.PRACTICE_ID.eq(practiceId))
                .and(PRACTICE_DETAIL.SUBJECT_ID.eq(subjectId))
                .and(PRACTICE_DETAIL.CREATED_BY.eq(loginId));
        return this.getOne(queryWrapper);
    }
}
