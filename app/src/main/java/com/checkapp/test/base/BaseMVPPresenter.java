package com.checkapp.test.base;

public interface BaseMVPPresenter<V extends BaseMVPView> {

    void onAttach(V mvpView);

    void onDetach();

}