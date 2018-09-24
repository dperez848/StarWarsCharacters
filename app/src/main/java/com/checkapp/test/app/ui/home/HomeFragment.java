package com.checkapp.test.app.ui.home;

import android.content.Context;
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
import com.checkapp.test.data.entities.Character;
import com.checkapp.test.databinding.HomeFragmentBinding;

import java.util.List;

public class HomeFragment extends BaseMVPFragment implements HomeViewListener {

    public static final String TAG = HomeFragment.class.getSimpleName();

    private HomeFragmentBinding binding;
    private HomeAdapter adapter;
    private PresenterFrgHome<HomeViewListener> presenter;
    private OnInteractionListener listener;

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
        setAdapterListeners();
    }

    private void setAdapterListeners() {
        adapter.setListener(new HomeAdapter.FavoriteListener() {
            @Override
            public void onFavoriteClick(Character character) {
                presenter.setFavoriteToCharacter(character);
            }

            @Override
            public void onItemClick(Character character) {
                presenter.doGoToDetail(character);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_home, menu);
        setupSearchView(menu);
        menu.getItem(1).setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_name:
                adapter.sortItemsBy(HomeAdapter.SortType.NAME);
                break;
            case R.id.sort_birth:
                adapter.sortItemsBy(HomeAdapter.SortType.BIRTH);
                break;
            case R.id.action_favorite:
                adapter.filterFavorites();
                break;
            case R.id.action_all:
                presenter.doSetAllItemsToAdapter();
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
        binding.recyclerCharacter.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerCharacter.setHasFixedSize(true);
        binding.recyclerCharacter.setAdapter(adapter);
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
    public void onItemsDone(List<Character> list) {
        refreshAdapterItems(list);
    }

    @Override
    public void onRefreshAdapterItems(List<Character> characterListArray) {
        refreshAdapterItems(characterListArray);
    }

    @Override
    public void onGoToDetail(String characterInfo) {
        if (listener != null) {
            listener.navigateToDetail(characterInfo);
        }
    }

    private void refreshAdapterItems(List<Character> list) {
        adapter.setItems(list);
        adapter.sortItemsBy(HomeAdapter.SortType.NAME);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInteractionListener) {
            listener = (OnInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() +
                    " Must implement " + OnInteractionListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    public interface OnInteractionListener {
        void navigateToDetail(String characterInfo);
    }
}

