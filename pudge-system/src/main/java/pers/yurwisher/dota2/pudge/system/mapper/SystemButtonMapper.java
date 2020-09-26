package pers.yurwisher.dota2.pudge.system.mapper;

import pers.yurwisher.dota2.pudge.system.entity.SystemButton;
import pers.yurwisher.dota2.pudge.base.CommonMapper;
import pers.yurwisher.dota2.pudge.system.pojo.tree.ButtonNode;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemButtonVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-26 11:35:17
 * @description 按钮 Mapper
 * @since V1.0.0
 */
public interface SystemButtonMapper extends CommonMapper<SystemButton> {

    /**
    * 详情
    * @param id ID
    * @return 详情
    */
    SystemButtonVo get(@Param("id")Long id);


    /**
     * 获取用户所有按钮node
     * @param userId 用户ID
     * @return buttons
     */
    List<ButtonNode> getUserButtonNodes(@Param("userId")Long userId);

    /**
     * 所有按钮
     * @return 按钮集合
     */
    List<ButtonNode> getAllButtonNodes();
}
