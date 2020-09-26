package pers.yurwisher.dota2.pudge.system.service;

import pers.yurwisher.dota2.pudge.base.BaseService;
import pers.yurwisher.dota2.pudge.system.entity.SystemButton;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemButtonFo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.ButtonNode;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemButtonVo;

import java.util.List;


/**
 * @author yq
 * @date 2020-09-26 11:35:17
 * @description 按钮
 * @since V1.0.0
 */
public interface ISystemButtonService extends BaseService<SystemButton> {

    /**
     * 新增
     * @param fo 参数
     */
    void create(SystemButtonFo fo);

    /**
     * 更新
     * @param id 主键
     * @param fo 参数
     */
    void update(Long id,SystemButtonFo fo);

    /**
    * 详情
    * @param id 主键
    * @return SystemButtonVo
    */
    SystemButtonVo get(Long id);

    /**
     * 指定用户所有按钮
     * @param userId 用户ID
     * @return 按钮集合
     */
    List<ButtonNode> getUserButtonNodes(Long userId);

    /**
     * 系统所有按钮
     * @return 全部按钮
     */
    List<ButtonNode> getAllButtonNodes();
}
