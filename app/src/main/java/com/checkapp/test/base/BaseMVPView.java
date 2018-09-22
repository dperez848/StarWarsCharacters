package com.checkapp.test.base;
import android.content.Context;
import android.support.annotation.StringRes;

public interface BaseMVPView {

    Context getAppContext();

    void showLoading(@StringRes int resId);

    void showLoading(@StringRes int resId, Object... objects);

    void showLoading(String message);

    void hideLoading();

    void showToast(@StringRes int resId, Object... objects);

    void showToast(String message);

    void showLongToast(@StringRes int resId, Object... objects);

    void showLongToast(String message);

    void showGeneralSnackbar(@StringRes int resId);

    void showGeneralSnackbar(String message);

    void hideGeneralSnackbar();

    void showNoConnectivitySnackbar();

    void hideNoConnectivitySnackbar();

}