package com.checkapp.test.app.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.checkapp.test.R;
import com.checkapp.test.app.ui.detail.DetailFragment;
import com.checkapp.test.base.BaseMVPActivity;
import com.checkapp.test.base.BasePresenter;
import com.checkapp.test.databinding.HomeActivityBinding;

public class DetailActivity extends BaseMVPActivity{

    public static final String ARG_Character = "ARG_Character";
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
        addFragment(DetailFragment.newInstance((String) getArgument(ARG_Character)),
                R.id.container, getString(R.string.detail_title), HomeFragment.TAG);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
