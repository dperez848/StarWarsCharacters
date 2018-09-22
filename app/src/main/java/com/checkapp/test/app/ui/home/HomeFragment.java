package com.checkapp.test.app.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.checkapp.test.R;
import com.checkapp.test.app.ui.home.mvp.HomeViewListener;
import com.checkapp.test.app.ui.home.mvp.PresenterFrgHome;
import com.checkapp.test.base.BaseFiltrableRecyclerAdapter;
import com.checkapp.test.base.BaseMVPFragment;
import com.checkapp.test.data.people_repository.local.People;
import com.checkapp.test.databinding.HomeFragmentBinding;

import java.util.List;

public class HomeFragment extends BaseMVPFragment implements HomeViewListener {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private HomeFragmentBinding binding;
    private HomeAdapter adapter;
    private PresenterFrgHome<HomeViewListener> presenter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.doGetItems();
    }

    @Override
    protected void initVars() {
        setHasOptionsMenu(true);
        initAdapter();
        initPresenter();
    }

    @Override
    protected void initViews() {
        setupRecycler();
        presenter.onAttach(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);
        setupSearchView(menu);
        menu.getItem(1).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name:
                adapter.setSortType(HomeAdapter.SortType.NAME);
                break;
            case R.id.sort_birth:
                adapter.setSortType(HomeAdapter.SortType.BIRTH);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initPresenter() {
        presenter = new PresenterFrgHome<>();
        setPresenter(presenter);
    }

    private void initAdapter() {
        adapter = new HomeAdapter(new BaseFiltrableRecyclerAdapter.OnEmptyStateListener() {
            @Override
            public void onAdapterEmpty() {
                binding.emptyView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdapterNotEmpty() {
                binding.emptyView.setVisibility(View.GONE);
            }
        });
    }

    private void setupRecycler() {
        binding.recyclerPeople.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerPeople.setHasFixedSize(true);
        binding.recyclerPeople.setAdapter(adapter);
    }

    private void setupSearchView(Menu menu) {
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setFilter(newText);
                return false;
            }
        });
    }

    @Override
    public void onItemsDone(List<People> list) {
        adapter.setItems(list);
        adapter.setSortType(HomeAdapter.SortType.NAME);
    }
}
