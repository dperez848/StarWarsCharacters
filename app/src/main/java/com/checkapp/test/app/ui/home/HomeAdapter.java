package com.checkapp.test.app.ui.home;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.checkapp.test.R;
import com.checkapp.test.base.BaseFiltrableRecyclerAdapter;
import com.checkapp.test.data.entities.Character;
import com.checkapp.test.databinding.ListItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;

public class HomeAdapter extends BaseFiltrableRecyclerAdapter<Character,
        BaseFiltrableRecyclerAdapter.BaseViewHolder<Character>> {

    private Comparator<Character> nameComparator;
    private Comparator<Character> birthComparator;
    private FavoriteListener listener;

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
        for (Character character : getItems()) {
            if (namesAreEquals(query, character)) {
                getVisibleItems().add(character);
            }
        }
    }

    @NonNull
    @Override
    public BaseViewHolder<Character> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new HomeViewHolder(view);
    }

    @NonNull
    private Comparator<Character> setBirthComparator() {
        return new Comparator<Character>() {
            @Override
            public int compare(Character character, Character t1) {
                Timber.d("number " + character.getBirthYearNumbers() + " other number " +
                        t1.getBirthYearNumbers() + " result compare " +
                        character.getBirthYearNumbers().compareTo(t1.getBirthYearNumbers()));
                return character.getBirthYearNumbers().compareTo(t1.getBirthYearNumbers());
            }
        };
    }

    @NonNull
    private Comparator<Character> setNameComparator() {
        return new Comparator<Character>() {
            @Override
            public int compare(Character character, Character t1) {
                return character.getName().compareTo(t1.getName());
            }
        };
    }

    @Override
    public int getItemCount() {
        return getVisibleItems().size();
    }

    public void sortItemsBy(SortType sortType) {
        Collections.sort(getVisibleItems(), sortType == SortType.NAME ? nameComparator : birthComparator);
        notifyDataSetChanged();
    }

    public void filterFavorites() {
        List<Character> favorites = new ArrayList<>();
        for (Character character : getVisibleItems()) {
            if (character.isFavorite()) {
                favorites.add(character);
            }
        }
        setItems(favorites);
    }

    private boolean namesAreEquals(String query, Character character) {
        return character.getName().toLowerCase().contains(query.toLowerCase())
                || character.getHomeworld().toLowerCase().contains(query.toLowerCase());
    }

    private class HomeViewHolder extends BaseViewHolder<Character> implements View.OnClickListener {

        private ListItemBinding binding;

        public HomeViewHolder(View view) {
            super(view);
            this.binding = DataBindingUtil.bind(view);
            view.setOnClickListener(this);
        }

        @Override
        public void bindViews(final Character character) {
            binding.title.setText(character.getName());
            binding.date.setText(character.getBirthYear());
            binding.homeworld.setText(character.getHomeworld());

            binding.favorite.setImageResource(character.isFavorite()
                    ? R.drawable.ic_favorite_on
                    : R.drawable.ic_favorite_off);

            setFavoriteListener(character);
        }

        private void setFavoriteListener(final Character character) {
            binding.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getListener().onFavoriteClick(character);
                }
            });
        }

        @Override
        public void onClick(View view) {
            getListener().onItemClick(getVisibleItems().get(getAdapterPosition()));
        }
    }

    public interface FavoriteListener {
        void onFavoriteClick(Character character);

        void onItemClick(Character character);
    }

    private FavoriteListener getListener() {
        return listener;
    }

    public void setListener(FavoriteListener listener) {
        this.listener = listener;
    }
}
