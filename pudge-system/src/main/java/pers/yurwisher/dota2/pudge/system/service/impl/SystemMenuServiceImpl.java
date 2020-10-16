package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.constants.CacheConstant;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.entity.SystemMenu;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemMenuMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemMenuFo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.ButtonNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuMeta;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuTreeNode;
import pers.yurwisher.dota2.pudge.system.service.CustomRedisCacheService;
import pers.yurwisher.dota2.pudge.system.service.ISystemButtonService;
import pers.yurwisher.dota2.pudge.system.service.ISystemMenuService;
import pers.yurwisher.dota2.pudge.wrapper.Tree;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements ISystemMenuService {

    private final ISystemButtonService systemButtonService;
    private final CustomRedisCacheService customRedisCacheService;

    /**
     * 新增
     *
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemMenuFo fo) {
        //菜单若为iFrame  path必须以http/https开头
        if (fo.getIFrame()) {
            if (!HttpUtil.isHttp(fo.getPath()) && HttpUtil.isHttps(fo.getPath())) {
                throw new SystemCustomException(SystemCustomTipEnum.MENU_I_FRAME_PATH_PREFIX_ERROR);
            }
        }
        //非根节点 component 不可为空
        if (fo.getPid() != null && StrUtil.isEmpty(fo.getComponent())) {
            throw new SystemCustomException(SystemCustomTipEnum.MENU_COMPONENT_NOT_BE_NULL);
        }
        SystemMenu systemMenu = new SystemMenu();
        BeanUtils.copyProperties(fo, systemMenu);
        baseMapper.insert(systemMenu);
        //删除相关缓存
        this.deleteCache();
    }

    /**
     * 更新
     *
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, SystemMenuFo fo) {
        if (id.equals(fo.getPid())) {
            throw new SystemCustomException(SystemCustomTipEnum.MENU_PID_NOT_ID);
        }
        //菜单若为iFrame  path必须以http/https开头
        if (fo.getIFrame()) {
            if (!HttpUtil.isHttp(fo.getPath()) && HttpUtil.isHttps(fo.getPath())) {
                throw new SystemCustomException(SystemCustomTipEnum.MENU_I_FRAME_PATH_PREFIX_ERROR);
            }
        }
        //非根节点 component 不可为空
        if (fo.getPid() != null && StrUtil.isEmpty(fo.getComponent())) {
            throw new SystemCustomException(SystemCustomTipEnum.MENU_COMPONENT_NOT_BE_NULL);
        }
        SystemMenu systemMenu = baseMapper.selectById(id);
        Assert.notNull(systemMenu);
        BeanUtils.copyProperties(fo, systemMenu);
        baseMapper.updateById(systemMenu);
        //删除相关缓存
        this.deleteCache();
    }


    @Override
    public List<SystemMenu> findAllByUserId(Long userId) {
        return baseMapper.getUserMenus(userId);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MenuTreeNode> tree() {
        CurrentUser currentUser = JwtUser.current();
        return customRedisCacheService.cacheRoundPlus(CacheConstant.MaName.SYSTEM_USER_TREE, currentUser.getUsername(), () -> {
            //菜单
            List<MenuTreeNode> menuTreeNodeList;
            //按钮
            List<ButtonNode> buttonNodeList;
            if (currentUser.isAdmin()) {
                menuTreeNodeList = baseMapper.getAllMenuTreeNodes();
                buttonNodeList = systemButtonService.getAllButtonNodes();
            } else {
                menuTreeNodeList = baseMapper.getUserMenuTreeNodes(currentUser.getId());
                buttonNodeList = systemButtonService.getUserButtonNodes(currentUser.getId());
            }
            return this.buildTree(menuTreeNodeList, buttonNodeList);
        });
    }

    private List<MenuTreeNode> buildTree(List<MenuTreeNode> menuTreeNodeList, List<ButtonNode> buttonNodeList) {
        Map<Long, List<ButtonNode>> buttonMap = null;
        if (CollectionUtil.isNotEmpty(buttonNodeList)) {
            buttonMap = buttonNodeList.stream().collect(Collectors.groupingBy(ButtonNode::getPid));
        }
        //构建树
        Map<Long, List<ButtonNode>> finalButtonMap = buttonMap;
        return new Tree<Long, MenuTreeNode>(-1L).build(menuTreeNodeList, node -> {
            //将按钮挂载到菜单下
            hangButtonToMenu(node, finalButtonMap);
            if (StrUtil.isNotBlank(node.getComponent()) && "Layout".equalsIgnoreCase(node.getComponent())) {
                node.setRedirect("noRedirect");
            }
        });
    }

    private void hangButtonToMenu(MenuTreeNode node, Map<Long, List<ButtonNode>> buttonMap) {
        MenuMeta menuMeta = node.getMeta();
        if (menuMeta != null) {
            List<ButtonNode> buttons = CollectionUtil.isNotEmpty(buttonMap) ? buttonMap.get(node.getId()) : ListUtil.empty();
            if (CollectionUtil.isNotEmpty(buttons)) {
                menuMeta.setButtons(buttons.stream().collect(Collectors.groupingBy(ButtonNode::getPosition)));
            }
        }
    }

    @Override
    public List<MenuAndButtonTreeNode> wholeTree() {
        return customRedisCacheService.cacheRound(CacheConstant.Key.SYSTEM_WHOLE_TREE,()->{
            List<MenuAndButtonTreeNode> nodes = baseMapper.getAllNodes();
            return new Tree<Long, MenuAndButtonTreeNode>(-1L).build(nodes);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<MenuAndButtonTreeNode> nodes) {
        if (CollectionUtil.isNotEmpty(nodes)) {
            Map<Boolean, List<MenuAndButtonTreeNode>> map = nodes.stream().collect(Collectors.groupingBy(MenuAndButtonTreeNode::getWhetherButton));
            //菜单
            List<MenuAndButtonTreeNode> menus = map.get(false);
            if (CollectionUtil.isNotEmpty(menus)) {
                baseMapper.deleteBatchIds(menus.stream().map(MenuAndButtonTreeNode::getId).collect(Collectors.toList()));
            }
            //按钮
            List<MenuAndButtonTreeNode> buttons = map.get(true);
            if (CollectionUtil.isNotEmpty(buttons)) {
                systemButtonService.removeByIds(buttons.stream().map(MenuAndButtonTreeNode::getId).collect(Collectors.toList()));
            }
        }
        //删除相关缓存
        this.deleteCache();
    }

    private void deleteCache(){
        //删除所有用户菜单缓存
        customRedisCacheService.batchDelete(CacheConstant.MaName.SYSTEM_USER_TREE);
        //删除完整树缓存
        customRedisCacheService.deleteCache(CacheConstant.Key.SYSTEM_WHOLE_TREE);
    }
}
