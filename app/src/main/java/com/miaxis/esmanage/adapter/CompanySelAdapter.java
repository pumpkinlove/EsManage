package com.miaxis.esmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miaxis.esmanage.R;
import com.miaxis.esmanage.entity.Company;

import java.util.List;

public class CompanySelAdapter extends BaseAdapter {

    private List<Company> companyList;
    private Context mContext;
    private LayoutInflater inflater;

    public CompanySelAdapter(List<Company> companyList, Context mContext) {
        this.companyList = companyList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (companyList != null) {
            return companyList.size();
        }
        return 0;
    }

    @Override
    public Company getItem(int position) {
        return companyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_company, null);
            holder.treeText = convertView.findViewById(R.id.tv_company_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.treeText.setText(companyList.get(position).getLabel());
        return convertView;

    }

    static class ViewHolder{
        TextView treeText;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }
}
