package com.checkapp.test.app.ui.home.mvp;

import com.checkapp.test.base.BaseMVPView;
import com.checkapp.test.data.entities.Character;

import java.util.List;

public interface HomeViewListener extends BaseMVPView {

    void onItemsDone(List<Character> list);

    void onRefreshAdapterItems(List<Character> CharacterListArray);

    void onGoToDetail(String CharacterInfo);
}
