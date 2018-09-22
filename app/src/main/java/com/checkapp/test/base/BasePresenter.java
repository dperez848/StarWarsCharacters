package com.checkapp.test.base;

import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BasePresenter<V extends BaseMVPView> implements BaseMVPPresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();

    private V mMvpView;
    private CompositeDisposable mCompositeDisposable;

    public BasePresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onAttach(V mvpView) {
        Log.d(TAG, "onAttach()");

        mMvpView = mvpView;
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach()");

        mCompositeDisposable.dispose();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

}