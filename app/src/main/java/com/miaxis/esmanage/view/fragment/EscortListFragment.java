package com.miaxis.esmanage.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.miaxis.esmanage.R;
import com.miaxis.esmanage.adapter.CompanySelAdapter;
import com.miaxis.esmanage.app.EsManageApp;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.view.custom.treeView.TreeNode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EscortListFragment extends BaseFragment {

    @BindView(R.id.lv_escort)
    ListView lvEscort;
    @BindView(R.id.srl_escort)
    SwipeRefreshLayout srlEscort;
    @BindView(R.id.sp_company_1)
    Spinner spCompany1;
    @BindView(R.id.sp_company_2)
    Spinner spCompany2;
    @BindView(R.id.sp_company_3)
    Spinner spCompany3;

    private CompanySelAdapter adapter;
    private List<TreeNode> allCompanyList;
    private List<TreeNode> topCompanyList;

    public EscortListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_escort_list;
    }

    @Override
    protected void initData() {
        allCompanyList = new ArrayList<>();
        List<Company> allCompany = EsManageApp.getInstance().getDaoSession().getCompanyDao().loadAll();
        allCompanyList.addAll(allCompany);
        topCompanyList = new ArrayList<>();
        int rootLevel = Integer.MAX_VALUE;
        for (TreeNode node : allCompany) {
            if (node.getNodeLevel() < rootLevel) {
                rootLevel = node.getNodeLevel();
            }
        }
        for (TreeNode node : allCompany) {
            if (node.getNodeLevel() == rootLevel) {
                topCompanyList.add(node);
            }
            List<TreeNode> cList = new ArrayList<>();
            for (TreeNode c : allCompany) {
                if (TextUtils.equals(c.getParentNodeCode(), node.getNodeCode())) {
                    cList.add(c);
                }
            }
            node.setChildrenList(cList);
        }
        adapter = new CompanySelAdapter(topCompanyList, getContext());

    }

    @Override
    protected void initView() {
        spCompany1.setAdapter(adapter);
        spCompany1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
