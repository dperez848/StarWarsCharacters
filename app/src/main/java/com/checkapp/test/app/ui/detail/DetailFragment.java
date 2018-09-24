package com.checkapp.test.app.ui.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.checkapp.test.R;
import com.checkapp.test.app.ui.detail.mvp.DetailViewListener;
import com.checkapp.test.app.ui.detail.mvp.PresenterFrgDetail;
import com.checkapp.test.base.BaseMVPFragment;
import com.checkapp.test.data.entities.Character;
import com.checkapp.test.databinding.DetailFragmentBinding;

public class DetailFragment extends BaseMVPFragment implements DetailViewListener {

    public static final String ARG_Character = "ARG_Character";
    private DetailFragmentBinding binding;
    private PresenterFrgDetail<DetailViewListener> presenter;

    public static DetailFragment newInstance(String CharacterInfo) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        args.putString(ARG_Character, CharacterInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    protected void initVars() {
        initPresenter();
        if (getArguments() != null && getArguments().getString(ARG_Character) != null) {
            presenter.setArguments(getArguments().getString(ARG_Character));
        }
    }

    private void initPresenter() {
        presenter = new PresenterFrgDetail<>();
        setPresenter(presenter);
    }

    @Override
    protected void initViews() {
        presenter.onAttach(this);
        presenter.doSetCharacterInfo();
    }

    @Override
    public void onCharacterInfo(Character Character) {
        binding.name.setText(Character.getName());
        binding.homeworld.setText(Character.getHomeworld());
        binding.birth.setText(Character.getBirthYear());
        binding.films.setText(Character.getFilmsText());
        binding.vehicles.setText(Character.getVehicleText());
    }
}
