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
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.entity.SystemMenu;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.SystemMenuMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemMenuFo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.ButtonNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuMeta;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuTreeNode;
import pers.yurwisher.dota2.pudge.system.service.ISystemButtonService;
import pers.yurwisher.dota2.pudge.system.service.ISystemMenuService;
import pers.yurwisher.dota2.pudge.wrapper.Tree;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    /**
     * 新增
     *
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SystemMenuFo fo) {
        //名称不可重复
        if (super.haveFieldValueEq(SystemMenu::getTitle, fo.getTitle())) {
            throw new SystemCustomException(SystemCustomTipEnum.MENU_NAME_REPEAT);
        }
        //菜单若为iFrame  path必须以http/https开头
        if (fo.getIFrame()) {
            if (!HttpUtil.isHttp(fo.getPath()) && HttpUtil.isHttps(fo.getPath())) {
                throw new SystemCustomException(SystemCustomTipEnum.MENU_I_FRAME_PATH_PREFIX_ERROR);
            }
        }
        SystemMenu systemMenu = new SystemMenu();
        BeanUtils.copyProperties(fo, systemMenu);
        baseMapper.insert(systemMenu);
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
        if(id.equals(fo.getPid())){
            throw new SystemCustomException(SystemCustomTipEnum.MENU_PID_NOT_ID);
        }
        //菜单若为iFrame  path必须以http/https开头
        if (fo.getIFrame()) {
            if (!HttpUtil.isHttp(fo.getPath()) && HttpUtil.isHttps(fo.getPath())) {
                throw new SystemCustomException(SystemCustomTipEnum.MENU_I_FRAME_PATH_PREFIX_ERROR);
            }
        }
        //名称不可重复
        SystemMenu x = super.getOneByFieldValueEq(SystemMenu::getTitle,fo.getTitle());
        if(x != null && !x.getId().equals(id)){
            throw new SystemCustomException(SystemCustomTipEnum.MENU_NAME_REPEAT);
        }
        SystemMenu systemMenu = baseMapper.selectById(id);
        Assert.notNull(systemMenu);
        BeanUtils.copyProperties(fo, systemMenu);
        baseMapper.updateById(systemMenu);
        //todo 清除缓存
    }


    @Override
    public List<SystemMenu> findAllByUserId(Long userId) {
        return baseMapper.getUserMenus(userId);
    }

    @Override
    public Object tree(Long userId) {
        ////菜单
        //List<MenuTreeNode> menuTreeNodeList = baseMapper.getUserMenuTreeNodes(userId);
        ////按钮
        //List<ButtonNode> buttonNodeList = systemButtonService.getUserButtonNodes(userId);
        //return this.buildTree(menuTreeNodeList,buttonNodeList);
        return this.wholeTree();
    }

    private List<MenuTreeNode> buildTree(List<MenuTreeNode> menuTreeNodeList,List<ButtonNode> buttonNodeList){
        Map<Long,List<ButtonNode>> buttonMap = null;
        if (CollectionUtil.isNotEmpty(buttonNodeList)) {
            buttonMap = buttonNodeList.stream().collect(Collectors.groupingBy(ButtonNode::getPid));
        }
        //构建树
        Map<Long, List<ButtonNode>> finalButtonMap = buttonMap;
        List<MenuTreeNode> treeNodes = new Tree<Long,MenuTreeNode>(-1L).build(menuTreeNodeList, node ->{
            //将按钮挂载到菜单下
            hangButtonToMenu(node, finalButtonMap);
            if(StrUtil.isNotBlank(node.getComponent()) && "Layout".equalsIgnoreCase(node.getComponent())){
                node.setRedirect("noRedirect");
            }
        });
        return treeNodes;
    }

    private void hangButtonToMenu(MenuTreeNode node,Map<Long,List<ButtonNode>> buttonMap){
        MenuMeta menuMeta = node.getMeta();
        if(menuMeta != null){
            List<ButtonNode> buttons = CollectionUtil.isNotEmpty(buttonMap) ? buttonMap.get(node.getId()): ListUtil.empty();
            if(CollectionUtil.isNotEmpty(buttons)){
                menuMeta.setButtons(buttons.stream().collect(Collectors.groupingBy(ButtonNode::getPosition)));
            }
        }
    }

    @Override
    public Object wholeTree() {
        //所有菜单
        List<MenuTreeNode> menuTreeNodeList = baseMapper.getAllMenuTreeNodes();
        //所有按钮
        List<ButtonNode> buttonNodeList = systemButtonService.getAllButtonNodes();
        return this.buildTree(menuTreeNodeList,buttonNodeList);
    }
}
