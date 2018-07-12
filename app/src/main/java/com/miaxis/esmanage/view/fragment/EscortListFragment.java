package com.miaxis.esmanage.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.MaterialDialog;
import com.miaxis.esmanage.R;
import com.miaxis.esmanage.adapter.CompanySelAdapter;
import com.miaxis.esmanage.adapter.EscortListAdapter;
import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Escort;
import com.miaxis.esmanage.presenter.IEscortManagePresenter;
import com.miaxis.esmanage.presenter.impl.EscortManagePresenter;
import com.miaxis.esmanage.util.Constant;
import com.miaxis.esmanage.view.IEscortListView;
import com.miaxis.esmanage.view.activity.EscortDetailActivity;
import com.miaxis.esmanage.view.custom.MySpinner;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class EscortListFragment extends BaseFragment implements IEscortListView {

    @BindView(R.id.rv_escort)
    RecyclerView rvEscort;
    @BindView(R.id.srl_escort)
    SwipeRefreshLayout srlEscort;
    @BindView(R.id.sp_company_1)
    Spinner spCompany1;
    @BindView(R.id.sp_company_2)
    Spinner spCompany2;
    @BindView(R.id.sp_company_3)
    MySpinner spCompany3;
    @BindView(R.id.fab_add_escort)
    FloatingActionButton fabAddEscort;

    private CompanySelAdapter adapter1;
    private CompanySelAdapter adapter2;
    private CompanySelAdapter adapter3;

    private boolean sp1IsFirstLoad = true;
    private boolean sp2IsFirstLoad = true;
    private boolean sp3IsFirstLoad = true;

    private EscortListAdapter listAdapter;

    private MaterialDialog mDialog;

    private IEscortManagePresenter presenter;

    private int curCompId = -1;

    public EscortListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadCompany1();
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_escort_list;
    }

    @Override
    protected void initData() {
        presenter = new EscortManagePresenter(this);
        listAdapter = new EscortListAdapter(getContext(), null);
        adapter1 = new CompanySelAdapter(null, getContext());
        adapter2 = new CompanySelAdapter(null, getContext());
        adapter3 = new CompanySelAdapter(null, getContext());
    }

    @Override
    protected void initView() {
        mDialog = new MaterialDialog.Builder(Objects.requireNonNull(getContext())).build();
        spCompany1.setAdapter(adapter1);
        spCompany1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp1IsFirstLoad) {
                    sp1IsFirstLoad = false;
                    return;
                }
                List<Company> companies = adapter1.getCompanyList();
                if (companies != null) {
                    curCompId = companies.get(position).getId();
                    presenter.loadCompany2(companies.get(position).getCompcode());
                    presenter.loadEscortsByCompanyId(curCompId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCompany2.setAdapter(adapter2);
        spCompany2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp2IsFirstLoad) {
                    sp2IsFirstLoad = false;
                    return;
                }
                List<Company> companies = adapter2.getCompanyList();
                if (companies != null) {
                    curCompId = companies.get(position).getId();
                    presenter.loadCompany3(companies.get(position).getCompcode());
                    presenter.loadEscortsByCompanyId(curCompId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCompany3.setAdapter(adapter3);
        spCompany3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp3IsFirstLoad) {
                    sp3IsFirstLoad = false;
                    return;
                }
                List<Company> companies = adapter3.getCompanyList();
                if (companies != null) {
                    curCompId = companies.get(position).getId();
                    presenter.loadEscortsByCompanyId(curCompId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        srlEscort.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadEscortsByCompanyId(curCompId);
            }
        });

        rvEscort.setAdapter(listAdapter);
        rvEscort.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter.setItemListener(new EscortListAdapter.onRecyclerItemClickerListener() {
            @Override
            public void onRecyclerItemClick(View view, Object data, int position) {
                Intent i = new Intent(getContext(), EscortDetailActivity.class);
                i.putExtra(Constant.INTENT_ESCORT_DETAIL_ESCORT, (Escort)data);
                i.putExtra(Constant.INTENT_DETAIL_OP, Constant.MODE_VIEW);
                i.putExtra(Constant.INTENT_EXTRA_COM_ID, curCompId);
                startActivity(i);
            }
        });

        spCompany1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    Class<?> clazz = AdapterView.class;
                    //Field field = clazz.getDeclaredField("mOldSelectedPosition");
                    //field.setAccessible(true);
                    //field.setInt(spn,-1);
                    Field field = clazz.getDeclaredField("mOldSelectedRowId");
                    field.setAccessible(true);
                    field.setInt(spCompany1,Integer.MIN_VALUE);
                } catch(Exception e){
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    public void showCompanySpinner1(List<Company> companyList) {
        adapter1.setCompanyList(companyList);
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void showCompanySpinner2(List<Company> companyList) {
        spCompany2.setVisibility(View.VISIBLE);
        adapter2.setCompanyList(companyList);
        adapter2.notifyDataSetChanged();
    }

    @Override
    public void showCompanySpinner3(List<Company> companyList) {
        spCompany3.setVisibility(View.VISIBLE);
        adapter3.setCompanyList(companyList);
        adapter3.notifyDataSetChanged();
    }

    @Override
    public void hideCompanySpinner2() {
        spCompany2.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideCompanySpinner3() {
        spCompany3.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showEscortList(List<Escort> escortList) {
        listAdapter.setEscortList(escortList);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(String message) {
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        mDialog.setContent(message);
    }

    @Override
    public void hideLoading() {
        srlEscort.setRefreshing(false);
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @OnClick(R.id.fab_add_escort)
    void onAddEscort() {
        Intent i = new Intent(getContext(), EscortDetailActivity.class);
        i.putExtra(Constant.INTENT_DETAIL_OP, Constant.MODE_ADD);
        i.putExtra(Constant.INTENT_EXTRA_COM_ID, curCompId);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (curCompId > 0) {
            presenter.loadEscortsByCompanyId(curCompId);
        }
    }
}
