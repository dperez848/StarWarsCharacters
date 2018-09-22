package com.checkapp.test.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.checkapp.test.R;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMVPActivity extends AppCompatActivity implements
        BaseMVPView {

    private static final int TOAST_LONG_CHARACTERS_COUNT = 50;

    private ProgressDialog mProgressDialog;
    private List<String> titleStack;
    private List<String> subtitleStack;
    private BasePresenter presenter;
    private Snackbar generalSnackbar;
    private Snackbar noConnectivitySnackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();

        setPresenter(getPresenter());

        titleStack = new ArrayList<>();
        subtitleStack = new ArrayList<>();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);

        initViews();
    }

    protected abstract BasePresenter getPresenter();

    private void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

    protected abstract void initVars();

    abstract protected void initViews();

    private void onNetworkConnect() {
        hideNoConnectivitySnackbar();
    }

    private void onNetworkDisconnect() {
        showNoConnectivitySnackbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public void showToast(int stringResId, Object... objects) {
        showToast(getString(stringResId, objects));
    }

    @Override
    public void showToast(String text) {
        if (text != null) {
            int duration = text.length() > TOAST_LONG_CHARACTERS_COUNT ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
            Toast.makeText(this, text, duration).show();
        }
    }

    @Override
    public void showLongToast(int stringResId, Object... objects) {
        showLongToast(getString(stringResId, objects));
    }

    @Override
    public void showLongToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showGeneralSnackbar(int stringResId) {
        showGeneralSnackbar(getString(stringResId));
    }

    @Override
    public void showGeneralSnackbar(String message) {
        final View rootView = getRootView();
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
        final View rootView = getRootView();
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
        if (noConnectivitySnackbar != null) {
            noConnectivitySnackbar.dismiss();
            noConnectivitySnackbar = null;
        }
    }

    private View getRootView() {
        final ViewGroup contentViewGroup = findViewById(android.R.id.content);
        View rootView = null;

        if (contentViewGroup != null)
            rootView = contentViewGroup.getChildAt(0);

        if (rootView == null)
            rootView = getWindow().getDecorView().getRootView();

        return rootView;
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
    public void onBackPressed() {
        if (titleStack.size() > 0) {
            titleStack.remove(titleStack.size() - 1);
            updateTitleFromBackStack();
        }

        if (subtitleStack.size() > 0) {
            subtitleStack.remove(subtitleStack.size() - 1);
            updateTitleFromBackStack();
        }

        super.onBackPressed();
    }

    protected void updateTitleFromBackStack() {
        if (titleStack.size() > 0) {
            getSupportActionBar().setTitle(titleStack.get(titleStack.size() - 1));
        }

        if (subtitleStack.size() > 0) {
            getSupportActionBar().setSubtitle(subtitleStack.get(subtitleStack.size() - 1));
        }
    }

    protected void navigateHorizontalToFragment(Fragment fragment, int containerId, String title,
                                                String tag) {
        titleStack.add(title);

        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment, tag)
                .commit();

        updateTitleFromBackStack();
    }

    protected void navigateHorizontalToFragment(Fragment fragment, int containerId, String title, String subtitle,
                                                String tag) {
        titleStack.add(title);
        subtitleStack.add(subtitle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment, tag)
                .commit();

        updateTitleFromBackStack();
    }

    protected void navigateHorizontalReplaceToFragment(Fragment fragment, int containerId, String title,
                                                       String tag) {
        titleStack.add(title);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();

        updateTitleFromBackStack();
    }

    protected void navigateHorizontalReplaceToFragment(Fragment fragment, int containerId, String title,
                                                       String subtitle, String tag) {
        titleStack.add(title);
        subtitleStack.add(subtitle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment, tag)
                .commit();

        updateTitleFromBackStack();
    }

    protected void navigateDownToFragment(Fragment fragment, int containerId, String title,
                                          String tag) {
        titleStack.add(title);

        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment, tag)
                .addToBackStack("backStack")
                .commit();

        updateTitleFromBackStack();
    }

    protected void navigateDownToFragment(Fragment fragment, int containerId, String title, String subtitle,
                                          String tag) {
        titleStack.add(title);
        subtitleStack.add(subtitle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment, tag)
                .addToBackStack("backStack")
                .commit();

        updateTitleFromBackStack();
    }

    protected void clearBackStack() {
        getSupportFragmentManager().popBackStack("backStack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected void setOkResult() {
        setResult(RESULT_OK);
    }

    protected void setOkResultWithData(Bundle args) {
        Intent intent = new Intent();
        intent.putExtras(args);

        setResult(RESULT_OK, intent);
    }

    protected Object getArgument(String key) {
        if (getIntent().getExtras() != null) {
            return getIntent().getExtras().get(key);
        }

        return null;
    }

    protected Object getArgument(Intent intent, String key) {
        if (intent.getExtras() != null) {
            return intent.getExtras().get(key);
        }

        return null;
    }
}