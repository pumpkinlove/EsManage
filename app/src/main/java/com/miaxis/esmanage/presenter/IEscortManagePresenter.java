package com.miaxis.esmanage.presenter;

import com.miaxis.esmanage.entity.Company;
import com.miaxis.esmanage.entity.Escort;

public interface IEscortManagePresenter {

    void loadRootCompany();
    void loadChildCompany(String compCode);
    void loadEscortsByCompanyId(int compId);
    void toAddEscort(Company company);
    void delEscort(Escort escort);
    void toModEscort(Escort escort);

}