package com.checkapp.test.app.ui.home.mvp;

import com.checkapp.test.base.BaseMVPView;
import com.checkapp.test.data.people_repository.local.People;

import java.util.List;

public interface HomeViewListener extends BaseMVPView {

    void onItemsDone(List<People> list);
}
