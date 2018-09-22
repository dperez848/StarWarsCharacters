package com.checkapp.test.app.ui.home;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.checkapp.test.R;
import com.checkapp.test.base.BaseFiltrableRecyclerAdapter;
import com.checkapp.test.data.people_repository.local.People;
import com.checkapp.test.databinding.ListItemBinding;

import java.util.Collections;
import java.util.Comparator;

public class HomeAdapter extends BaseFiltrableRecyclerAdapter<People,
        BaseFiltrableRecyclerAdapter.BaseViewHolder<People>> {

    private Comparator<People> nameComparator;
    private Comparator<People> birthComparator;

    public enum SortType {
        NAME,
        BIRTH
    }

    public HomeAdapter(OnEmptyStateListener listener) {
        super(listener);
        this.nameComparator = setNameComparator();
        this.birthComparator = setBirthComparator();
    }

    @Override
    protected void addItemsAccordingToFilter(String query) {
        for (People people : getItems()) {
            if (people.getName().toLowerCase().contains(query.toLowerCase())
                    || people.getHomeworld().toLowerCase().contains(query.toLowerCase())) {
                getVisibleItems().add(people);
            }
        }
    }

    @NonNull
    @Override
    public BaseViewHolder<People> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new HomeViewHolder(view);
    }

    @NonNull
    private Comparator<People> setBirthComparator() {
        return new Comparator<People>() {
            @Override
            public int compare(People people, People t1) {
                return people.getBirthYearNumbers().compareTo(t1.getBirthYearNumbers());
            }
        };
    }

    @NonNull
    private Comparator<People> setNameComparator() {
        return new Comparator<People>() {
            @Override
            public int compare(People people, People t1) {
                return people.getName().compareTo(t1.getName());
            }
        };
    }

    @Override
    public int getItemCount() {
        return getVisibleItems().size();
    }

    public void setSortType(SortType sortType) {
        Collections.sort(getVisibleItems(), sortType == SortType.NAME ? nameComparator : birthComparator);
    }

    private class HomeViewHolder extends BaseViewHolder<People> implements View.OnClickListener {

        private ListItemBinding binding;

        public HomeViewHolder(View view) {
            super(view);
            this.binding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }

        @Override
        public void bindViews(People people) {
            binding.title.setText(people.getName());
            binding.date.setText(people.getBirthYear());
            binding.homeworld.setText(people.getHomeworld());
        }


        @Override
        public void onClick(View view) {

        }
    }
}
