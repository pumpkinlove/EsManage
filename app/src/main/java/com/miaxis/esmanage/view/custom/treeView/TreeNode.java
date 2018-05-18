package com.miaxis.esmanage.view.custom.treeView;

import java.util.List;

public interface TreeNode extends Comparable {

    String getParentNodeCode();
    String getNodeCode();

    boolean isExpanded();
    void setExpand(boolean isExpand);

    boolean hasChildren();

    List<TreeNode> getChildrenList();
    void setChildrenList(List<TreeNode> treeNodeList);

    int getNodeLevel();

    String getLabel();

}
