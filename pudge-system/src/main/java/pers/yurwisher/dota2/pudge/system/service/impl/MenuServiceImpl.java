package pers.yurwisher.dota2.pudge.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.yurwisher.dota2.pudge.base.impl.BaseServiceImpl;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.entity.Menu;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.system.mapper.MenuMapper;
import pers.yurwisher.dota2.pudge.system.pojo.fo.MenuFo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.ButtonNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuMeta;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuTreeNode;
import pers.yurwisher.dota2.pudge.system.service.IMenuService;
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
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    /**
     * 新增
     *
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MenuFo fo) {
        //title名称不可重复
        if (super.haveFieldValueEq(Menu::getTitle, fo.getTitle())) {
            throw new SystemCustomException(SystemCustomTipEnum.MENU_TITLE_REPEAT);
        }
        //component name 不可重复
        if (super.haveFieldValueEq(Menu::getName, fo.getName())) {
            throw new SystemCustomException(SystemCustomTipEnum.MENU_COMPONENT_NAME_REPEAT);
        }
        //菜单若为iFrame  path必须以http/https开头
        if (fo.getIFrame()) {
            if (!HttpUtil.isHttp(fo.getPath()) && HttpUtil.isHttps(fo.getPath())) {
                throw new SystemCustomException(SystemCustomTipEnum.MENU_I_FRAME_PATH_PREFIX_ERROR);
            }
        }
        if (fo.getPid().equals(0L)) {
            fo.setPid(null);
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(fo, menu);
        baseMapper.insert(menu);
    }

    /**
     * 更新
     *
     * @param id 主键
     * @param fo 参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, MenuFo fo) {
        if(id.equals(fo.getPid())){
            throw new SystemCustomException(SystemCustomTipEnum.MENU_PID_NOT_ID);
        }
        //菜单若为iFrame  path必须以http/https开头
        if (fo.getIFrame()) {
            if (!HttpUtil.isHttp(fo.getPath()) && HttpUtil.isHttps(fo.getPath())) {
                throw new SystemCustomException(SystemCustomTipEnum.MENU_I_FRAME_PATH_PREFIX_ERROR);
            }
        }
        //title不可重复
        Menu x = super.getOneByFieldValueEq(Menu::getTitle,fo.getTitle());
        if(x != null && !x.getId().equals(id)){
            throw new SystemCustomException(SystemCustomTipEnum.MENU_TITLE_REPEAT);
        }
        //name 不可重复
        x = super.getOneByFieldValueEq(Menu::getName,fo.getName());
        if(x != null && !x.getId().equals(id)){
            throw new SystemCustomException(SystemCustomTipEnum.MENU_COMPONENT_NAME_REPEAT);
        }
        if (fo.getPid().equals(0L)) {
            fo.setPid(null);
        }
        Menu menu = baseMapper.selectById(id);
        Assert.notNull(menu);
        BeanUtils.copyProperties(fo, menu);
        baseMapper.updateById(menu);
        //todo 清除缓存
    }


    @Override
    public List<Menu> findAllByUserId(Long userId) {
        return baseMapper.getUserMenus(userId);
    }

    @Override
    public Object tree(Long userId) {
        //菜单
        List<MenuTreeNode> menuTreeNodeList = baseMapper.getUserMenuTreeNodes(userId);
        //按钮
        List<ButtonNode> buttonNodeList = baseMapper.getUserButtonNodes(userId);
        return this.buildTree(menuTreeNodeList,buttonNodeList);
    }

    private List<MenuTreeNode> buildTree(List<MenuTreeNode> menuTreeNodeList,List<ButtonNode> buttonNodeList){
        if (CollectionUtil.isNotEmpty(buttonNodeList)) {
            Map<Long,List<ButtonNode>> buttonMap = buttonNodeList.stream()
                    .peek(bn->bn.setPid(bn.getPid() == null ? 0L : bn.getPid()))
                    .collect(Collectors.groupingBy(ButtonNode::getPid));
            if(CollectionUtil.isNotEmpty(menuTreeNodeList)){
                //将按钮挂载到菜单下
                menuTreeNodeList.forEach(mn -> mn.setButtons(buttonMap.get(mn.getId())));
            }
        }
        List<MenuTreeNode> menuTreeNodes =  new Tree<Long,MenuTreeNode>(0L).build(menuTreeNodeList,node ->{
            //元数据
            MenuMeta menuMeta = new MenuMeta();
            menuMeta.setNoCache(!node.getCache());
            menuMeta.setIcon(node.getIcon());
            menuMeta.setTitle(node.getTitle());
            node.setMeta(menuMeta);
            //是否一级目录
            boolean pidIsNull = node.getPid() == null;
            //componentName 为空则赋值为title. 按钮需要
            if(StrUtil.isBlank(node.getName())){
                node.setName(node.getTitle());
            }
            // 一级目录需要加斜杠，不然会报警告
            if(pidIsNull){
                node.setPath(StrUtil.SLASH + node.getPath());
            }
            //非外链
            if(!node.getIFrame()){
                //一级目录 且无 component,默认为Layout
                if(pidIsNull && StrUtil.isBlank(node.getComponent())){
                    node.setComponent("Layout");
                }
            }
            //存在子节点
            if(CollectionUtil.isNotEmpty(node.getChildren())){
                node.setAlwaysShow(true);
                node.setRedirect("noredirect");
            }else if(pidIsNull){
                //一级目录无子菜单
                MenuTreeNode newNode = new MenuTreeNode();
                newNode.setMeta(menuMeta);
                // 非外链
                if(!node.getIFrame()){
                    newNode.setPath("index");
                    newNode.setName(node.getName());
                    newNode.setComponent(node.getComponent());
                } else {
                    newNode.setPath(node.getPath());
                }
                node.setMeta(null);
                node.setName(null);
                node.setComponent("Layout");
                node.setChildren(ListUtil.toList(newNode));
            }
        });
        return menuTreeNodes;
    }

    @Override
    public Object wholeTree() {
        return null;
    }
}
