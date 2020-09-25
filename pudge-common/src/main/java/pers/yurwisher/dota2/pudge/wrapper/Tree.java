package pers.yurwisher.dota2.pudge.wrapper;


import cn.hutool.core.collection.CollectionUtil;
import pers.yurwisher.dota2.pudge.function.TreeNodeParser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yq
 * @date 2019/07/23 11:33
 * @description 树
 * @since V1.0.0
 */
public class Tree<ID,T extends TreeNode<ID,T>> {

    private ID rootId;

    public Tree(ID rootId) {
        this.rootId = rootId;
    }

    public List<T> build(List<T> list){
        if(CollectionUtil.isNotEmpty(list)){
            //按父菜单分组
            Map<ID,List<T>> map = list.stream().peek(n ->{
                if(n.getPid() == null){
                    n.setPid(rootId);
                }
            }).collect(Collectors.groupingBy(TreeNode::getPid));
            //顶级菜单
            List<T> baseMenus = map.get(rootId);
            return loop(baseMenus,map);
        }
        return null;
    }

    public List<T> build(List<T> list, TreeNodeParser<T> parser){
        if(CollectionUtil.isNotEmpty(list)){
            //按父菜单分组
            Map<ID,List<T>> map = list.stream().peek(n ->{
                if(n.getPid() == null){
                    n.setPid(rootId);
                }
                parser.parse(n);
            }).collect(Collectors.groupingBy(TreeNode::getPid));
            //顶级菜单
            List<T> baseMenus = map.get(rootId);
            return loop(baseMenus,map);
        }
        return null;
    }

    private  List<T> loop(List<T> list,Map<ID,List<T>> map){
        for (T node : list){
            node.setChildren(map.get(node.getId()));
            if(CollectionUtil.isNotEmpty(node.getChildren())){
                loop(node.getChildren(),map);
            }
        }
        return list;
    }

}
