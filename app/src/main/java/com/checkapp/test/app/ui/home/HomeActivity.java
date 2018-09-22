package com.checkapp.test.app.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.checkapp.test.R;
import com.checkapp.test.base.BaseMVPActivity;
import com.checkapp.test.base.BasePresenter;
import com.checkapp.test.databinding.HomeActivityBinding;

public class HomeActivity extends BaseMVPActivity {

    private HomeActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.binding = DataBindingUtil.setContentView(this, R.layout.home_activity);

        super.onCreate(savedInstanceState);

    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }


    @Override
    protected void initVars() {
    }

    @Override
    protected void initViews() {
        initToolbar();

        navigateHorizontalToFragment(HomeFragment.newInstance(), R.id.container, getString(R.string.app_name), HomeFragment.TAG);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }


}
