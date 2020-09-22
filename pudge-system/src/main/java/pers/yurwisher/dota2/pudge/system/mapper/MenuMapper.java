package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.Menu;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单 Mapper
 * @since V1.0.0
 */
public interface MenuMapper extends CommonMapper<Menu> {


    /**
    * 详情
    * @param id ID
    * @return 详情
    */
    MenuVo get(@Param("id")Long id);

    /**
     * 获取用户所有权限
     * @param userId 用户ID
     * @return 菜单权限
     */
    List<Menu> getUserMenus(@Param("userId")Long userId);
}
