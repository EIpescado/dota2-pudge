package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.system.entity.Menu;
import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.pojo.fo.MenuFo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.MenuVo;

import java.util.List;


/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
public interface IMenuService extends BaseService<Menu> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(MenuFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,MenuFo fo);


     /**
     * 删除
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 获取用户所有菜单
     * @param userId 用户ID
     * @return 菜单集合
     */
    List<Menu> findAllByUserId(Long userId);
}
