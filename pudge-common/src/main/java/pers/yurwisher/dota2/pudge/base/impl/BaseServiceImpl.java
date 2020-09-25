package pers.yurwisher.dota2.pudge.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import pers.yurwisher.dota2.pudge.base.BasePageQo;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;

import java.util.List;


/**
 * @author yq
 * @date 2018/11/15 11:39
 * @description 基础实现
 * @since V1.0.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected  LambdaQueryWrapper<T> buildLambdaQueryWrapper(){
        return new LambdaQueryWrapper<>();
    }

    @Autowired
    private Validator validator;

    /**
     * 自定义分页参数转 mybatis-plus分页参数
     */
    protected Page toPage(BasePageQo qo){
        Page page = new Page();
        page.setSize(qo.getSize());
        page.setCurrent(qo.getPage());
        return page;
    }

    /**
     * mybatis-plus 分页结果转自定义结果
     */
    @SuppressWarnings("unchecked")
    protected PageR toPageR(IPage page){
        PageR pageR = new PageR<>();
        pageR.setPages(page.getPages());
        pageR.setTotal(page.getTotal());
        pageR.setRows(page.getRecords());
        return pageR;
    }

    @Override
    public boolean haveFieldValueEq(SFunction<T, ?> function, Object value) {
       Integer count = baseMapper.selectCount(Wrappers.<T>lambdaQuery().eq(function,value));
       return count == null || count == 0;
    }

    @Override
    public T getOneByFieldValueEq(SFunction<T, ?> function, Object value) {
        return baseMapper.selectOne(Wrappers.<T>lambdaQuery().eq(function,value));
    }

    @Override
    public List<T> getByFieldValueEq(SFunction<T, ?> function, Object value) {
        return baseMapper.selectList(Wrappers.<T>lambdaQuery().eq(function,value));
    }
}
