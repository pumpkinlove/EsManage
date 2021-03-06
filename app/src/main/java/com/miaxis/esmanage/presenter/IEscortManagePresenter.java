package com.miaxis.esmanage.presenter;

import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Escort;

public interface IEscortManagePresenter {

    // TODO: 2018/5/20 0020 合并三层加载方法
    void loadCompany1();
    void loadCompany2(String parentCompCode);
    void loadCompany3(String parentCompCode);

    void loadEscortsByCompanyId(int compId);

    void toAddEscort(Company company);
    void toModEscort(Escort escort);
    void delEscort(Escort escort);

}