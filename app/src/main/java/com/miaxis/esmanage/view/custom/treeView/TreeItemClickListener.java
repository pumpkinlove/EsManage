package com.miaxis.esmanage.view.custom.treeView;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.miaxis.esmanage.adapter.TreeListAdapter;

import java.util.ArrayList;
import java.util.List;

public class TreeItemClickListener implements AdapterView.OnItemClickListener {

    private TreeListAdapter treeViewAdapter;

    public TreeItemClickListener(TreeListAdapter treeViewAdapter) {
        this.treeViewAdapter = treeViewAdapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//点击的item代表的元素
        TreeNode treeNode = (TreeNode) treeViewAdapter.getItem(position);
        //树中顶层的元素
        List<TreeNode> topNodes = treeViewAdapter.getTopNodeList();
        //元素的数据源
        List<TreeNode> allNodes = treeViewAdapter.getNodeList();

        //点击没有子项的item直接返回
        if (!treeNode.hasChildren()) {
            return;
        }

        if (treeNode.isExpanded()) {
            treeNode.setExpand(false);
            //删除节点内部对应子节点数据，包括子节点的子节点...
            ArrayList<TreeNode> elementsToDel = new ArrayList<>();
            for (int i = position + 1; i < topNodes.size(); i++) {
                if (treeNode.getNodeLevel() >= topNodes.get(i).getNodeLevel())
                    break;
                elementsToDel.add(topNodes.get(i));
            }
            topNodes.removeAll(elementsToDel);
            treeViewAdapter.notifyDataSetChanged();
        } else {
            treeNode.setExpand(true);
            //从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
            int i = 1;//注意这里的计数器放在for外面才能保证计数有效
            for (TreeNode e : allNodes) {
                if (TextUtils.equals(e.getParentNodeCode(), treeNode.getNodeCode())) {
                    e.setExpand(false);
                    topNodes.add(position + i, e);
                    i ++;
                }
            }
            treeViewAdapter.notifyDataSetChanged();
        }
    }
}
