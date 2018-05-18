package com.miaxis.esmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.esmanage.R;
import com.miaxis.esmanage.view.custom.treeView.TreeNode;

import java.util.List;

public class TreeListAdapter extends BaseAdapter {

    private List<TreeNode> nodeList;
    private List<TreeNode> topNodeList;

    private LayoutInflater inflater;
    private Context mContext;
    private int indentionBase = 20;

    public TreeListAdapter(List<TreeNode> nodeList, List<TreeNode> topNodeList, Context mContext) {
        this.nodeList = nodeList;
        this.topNodeList = topNodeList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return topNodeList.size();
    }

    @Override
    public Object getItem(int position) {
        return topNodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_company, null);
            holder.treeText = convertView.findViewById(R.id.tv_company_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TreeNode element = topNodeList.get(position);
        int level = element.getNodeLevel();
        holder.homeImg.setPadding(
                indentionBase * (level + 1),
                holder.homeImg.getPaddingTop(),
                holder.homeImg.getPaddingRight(),
                holder.homeImg.getPaddingBottom());
        holder.treeText.setText(element.getLabel());
        if (element.hasChildren() && !element.isExpanded()) {
            holder.homeImg.setImageResource(R.drawable.tab_icon_my_task);
            //这里要主动设置一下icon可见，因为convertView有可能是重用了"设置了不可见"的view，下同。
            holder.homeImg.setVisibility(View.VISIBLE);
        } else if (element.hasChildren() && element.isExpanded()) {
            holder.homeImg.setImageResource(R.drawable.tab_icon_my_task);
            holder.homeImg.setVisibility(View.VISIBLE);
        } else if (!element.hasChildren()) {
            holder.homeImg.setImageResource(R.drawable.tab_icon_my_task);
            holder.homeImg.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public List<TreeNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<TreeNode> nodeList) {
        this.nodeList = nodeList;
    }

    public List<TreeNode> getTopNodeList() {
        return topNodeList;
    }

    public void setTopNodeList(List<TreeNode> topNodeList) {
        this.topNodeList = topNodeList;
    }

    static class ViewHolder{
        ImageView homeImg;
        TextView treeText;
    }

}
