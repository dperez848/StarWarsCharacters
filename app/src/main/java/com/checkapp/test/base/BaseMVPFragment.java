package com.checkapp.test.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.checkapp.test.R;

/**
 * @author daniel.leonett
 */

public abstract class BaseMVPFragment extends Fragment implements BaseMVPView {

    // Constants
    private static final String TAG = BaseMVPFragment.class.getSimpleName();
    private static final int TOAST_LONG_CHARACTERS_COUNT = 50;

    // Fields
    private ProgressDialog mProgressDialog;
    private Snackbar generalSnackbar;
    private Snackbar noConnectivitySnackbar;
    private BasePresenter presenter;

    abstract protected void initVars();

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    abstract protected void initViews();

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDetach();
        }

        Log.d(TAG, "onDestroyView()");
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Context getAppContext() {
        return getContext().getApplicationContext();
    }

    public void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void showToast(int stringResId, Object... objects) {
        showToast(getString(stringResId, objects));
    }

    @Override
    public void showToast(String text) {
        if (text != null) {
            int duration = text.length() > TOAST_LONG_CHARACTERS_COUNT ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
            Toast.makeText(getActivity(), text, duration).show();
        }
    }

    @Override
    public void showLongToast(int stringResId, Object... objects) {
        showLongToast(getString(stringResId, objects));
    }

    @Override
    public void showLongToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(@StringRes int resId) {
        showLoading(getString(resId));
    }

    @Override
    public void showLoading(@StringRes int resId, Object... objects) {
        showLoading(getString(resId, objects));
    }

    @Override
    public void showLoading(String message) {
        hideLoading();
        mProgressDialog.setMessage((message != null) ? message : getString(R.string.common_loading));
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void showGeneralSnackbar(int stringResId) {
        showGeneralSnackbar(getString(stringResId));
    }

    @Override
    public void showGeneralSnackbar(String message) {
        final View rootView = getView();
        if (rootView != null) {
            hideGeneralSnackbar();
            generalSnackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE);
            TextView tv = generalSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(ContextCompat.getColor(getAppContext(), android.R.color.white));
            generalSnackbar.setAction(R.string.common_dismiss, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generalSnackbar.dismiss();
                }
            });

            generalSnackbar.show();
        }
    }

    @Override
    public void hideGeneralSnackbar() {
        if (generalSnackbar != null && generalSnackbar.isShown()) {
            generalSnackbar.dismiss();
            generalSnackbar = null;
        }
    }

    @Override
    public void showNoConnectivitySnackbar() {
        final View rootView = getView();
        if (rootView != null) {
            hideNoConnectivitySnackbar();
            noConnectivitySnackbar = Snackbar.make(rootView, R.string.common_no_internet_connection, Snackbar.LENGTH_INDEFINITE);
            TextView tv = noConnectivitySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(ContextCompat.getColor(getAppContext(), android.R.color.white));
            noConnectivitySnackbar.setAction(R.string.common_dismiss, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noConnectivitySnackbar.dismiss();
                }
            });

            noConnectivitySnackbar.show();
        }
    }

    @Override
    public void hideNoConnectivitySnackbar() {
        if (noConnectivitySnackbar != null && noConnectivitySnackbar.isShown()) {
            noConnectivitySnackbar.dismiss();
            noConnectivitySnackbar = null;
        }
    }

}