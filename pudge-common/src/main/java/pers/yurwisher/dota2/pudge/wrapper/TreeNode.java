package pers.yurwisher.dota2.pudge.wrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yq
 * @date 2020/09/25 16:18
 * @description 树节点
 * @since V1.0.0
 */
public class TreeNode<ID,T extends TreeNode>  {

    private ID id;

    private ID pid;

    private List<T> children = new ArrayList<>();

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getPid() {
        return pid;
    }

    public void setPid(ID pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
