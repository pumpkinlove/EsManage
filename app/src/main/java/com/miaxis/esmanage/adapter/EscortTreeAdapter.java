package com.miaxis.esmanage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.entity.Node;

import java.util.List;

public abstract class EscortTreeAdapter<T> extends BaseAdapter {
    private List<Node> vNodeList;
    private List<Node> allNodeList;

    private Context mContext;

    private int defaultExpandLevel;

    public EscortTreeAdapter(List<Node<Company>> companyList, List<Node<Escort>> escortList, Context mContext, int defaultExpandLevel) {
        this.mContext = mContext;
        this.defaultExpandLevel = defaultExpandLevel;
    }

    @Override
    public int getCount() {
        return vNodeList.size();
    }

    @Override
    public Node getItem(int position) {
        return vNodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = vNodeList.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        // 设置内边距
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        return convertView;
    }

    private OnTreeNodeClickListener onTreeNodeClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    public abstract View getConvertView(Node node, int position,
                                        View convertView, ViewGroup parent);
}
