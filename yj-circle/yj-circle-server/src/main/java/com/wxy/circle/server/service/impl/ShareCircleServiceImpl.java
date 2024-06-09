package com.wxy.circle.server.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.wxy.circle.api.req.RemoveShareCircleReq;
import com.wxy.circle.api.req.SaveShareCircleReq;
import com.wxy.circle.api.req.UpdateShareCircleReq;
import com.wxy.circle.api.vo.ShareCircleVO;
import com.wxy.circle.server.aop.AopLogAnnotations;
import com.wxy.circle.server.entity.ShareCircle;
import com.wxy.circle.server.mapper.ShareCircleMapper;
import com.wxy.circle.server.service.ShareCircleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wxy.circle.server.entity.table.ShareCircleTableDef.SHARE_CIRCLE;

/**
 * @program: YunJuClub-Flex
 * @description: ShareCircleServiceImpl
 * @author: 32115
 * @create: 2024-06-09 16:29
 */
@Service
public class ShareCircleServiceImpl
        extends ServiceImpl<ShareCircleMapper, ShareCircle>
        implements ShareCircleService {

    @Resource
    private ShareCircleMapper shareCircleMapper;

    /**
     * @author: 32115
     * @description: 获取圈子列表
     * @date: 2024/6/9
     * @return: List<ShareCircleVO>
     */
    @Override
    public List<ShareCircleVO> getShareCircleList() {
        // 首先构建查询条件查询所有圈子分类 即parentId为-1
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SHARE_CIRCLE.DEFAULT_COLUMNS)
                .from(SHARE_CIRCLE)
                .where(SHARE_CIRCLE.PARENT_ID.eq(-1L));
        List<ShareCircle> firstShareCircleList =
                shareCircleMapper.selectListByQuery(queryWrapper);
        // 遍历封装返回对象并查询圈子分类下的圈子
        return firstShareCircleList.stream().map(shareCircle -> {
            // 封装圈子分类信息
            ShareCircleVO shareCircleVO = new ShareCircleVO();
            shareCircleVO.setId(shareCircle.getId());
            shareCircleVO.setCircleName(shareCircle.getCircleName());
            shareCircleVO.setIcon(shareCircle.getIcon());
            // 根据圈子分类查询出分类下的圈子
            QueryWrapper queryWrapper1 = QueryWrapper.create()
                    .select(SHARE_CIRCLE.DEFAULT_COLUMNS)
                    .from(SHARE_CIRCLE)
                    .where(SHARE_CIRCLE.PARENT_ID.eq(shareCircle.getId()));
            // 查询
            List<ShareCircle> child =
                    shareCircleMapper.selectListByQuery(queryWrapper1);
            // 封装children信息
            List<ShareCircleVO> children = child.stream()
                    .map(childCircle -> {
                        ShareCircleVO childCircleVO = new ShareCircleVO();
                        childCircleVO.setId(childCircle.getId());
                        childCircleVO.setCircleName(childCircle.getCircleName());
                        childCircleVO.setIcon(childCircle.getIcon());
                        return childCircleVO;
                    }).toList();
            shareCircleVO.setChildren(children);
            return shareCircleVO;
        }).toList();
    }

    /**
     * @author: 32115
     * @description: 删除圈子
     * @date: 2024/6/9
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean removeCircle(RemoveShareCircleReq req) {
        return shareCircleMapper.deleteById(req.getId()) > 0;
    }

    /**
     * @author: 32115
     * @description: 更新圈子
     * @date: 2024/6/9
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean updateCircle(UpdateShareCircleReq req) {
        return UpdateChain.of(ShareCircle.class)
                .set(ShareCircle::getCircleName, req.getCircleName())
                .set(ShareCircle::getIcon, req.getIcon())
                .set(ShareCircle::getParentId,req.getParentId())
                .where(ShareCircle::getId).eq(req.getId())
                .update();
    }

    /**
     * @author: 32115
     * @description: 保存圈子
     * @date: 2024/6/9
     * @param: req
     * @return: Boolean
     */
    @Override
    @AopLogAnnotations
    @Transactional
    public Boolean insertShareCircle(SaveShareCircleReq req) {
        // 封装要保存的信息
        ShareCircle shareCircle = new ShareCircle();
        shareCircle.setParentId(req.getParentId());
        shareCircle.setCircleName(req.getCircleName());
        shareCircle.setIcon(req.getIcon());
        // 保存信息
        return this.save(shareCircle);
    }
}
