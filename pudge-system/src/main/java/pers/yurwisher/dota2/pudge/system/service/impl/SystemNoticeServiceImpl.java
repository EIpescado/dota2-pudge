package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.system.entity.SystemNotice;
import pers.yurwisher.dota2.pudge.system.mapper.SystemNoticeMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemNoticeFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemNoticeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemNoticeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemNoticeVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemNoticeService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import java.time.LocalTime;


/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告
 * @since V1.0.0
 */
@Service
public class SystemNoticeServiceImpl extends BaseServiceImpl<SystemNoticeMapper, SystemNotice> implements ISystemNoticeService {

    /**
     * 新增
     *
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemNoticeFo fo) {
        SystemNotice systemNotice = new SystemNotice();
        BeanUtils.copyProperties(fo, systemNotice, "startDate", "endDate");
        systemNotice.setStartDate(PudgeUtil.getStartOfDay(fo.getStartDate()));
        systemNotice.setEndDate(PudgeUtil.getEndOfDay(fo.getEndDate()));
        baseMapper.insert(systemNotice);
    }

    /**
     * 更新
     *
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SystemNoticeFo fo) {
        SystemNotice systemNotice = baseMapper.selectById(id);
        Assert.notNull(systemNotice);
        BeanUtils.copyProperties(fo, systemNotice, "startDate", "endDate");
        systemNotice.setStartDate(PudgeUtil.getStartOfDay(fo.getStartDate()));
        systemNotice.setEndDate(PudgeUtil.getEndOfDay(fo.getEndDate()));
        baseMapper.updateById(systemNotice);
    }

    /**
     * 列表
     *
     * @param qo 查询参数
     * @return 分页对象
     */
    @Override
    @SuppressWarnings("unchecked")
    public PageR<SystemNoticeTo> list(SystemNoticeQo qo) {
        return super.toPageR(baseMapper.list(super.toPage(qo), qo));
    }


    /**
     * 详情
     *
     * @param id 主键
     * @return SystemNoticeVo
     */
    @Override
    public SystemNoticeVo get(Long id) {
        return baseMapper.get(id);
    }

    /**
     * 删除
     *
     * @param id 主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        baseMapper.deleteById(id);
    }

}
