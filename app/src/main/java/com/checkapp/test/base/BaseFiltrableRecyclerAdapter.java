package com.checkapp.test.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFiltrableRecyclerAdapter<Item, Holder extends BaseFiltrableRecyclerAdapter.BaseViewHolder<Item>>
        extends RecyclerView.Adapter<Holder> {

    private static final String TAG = BaseFiltrableRecyclerAdapter.class.getSimpleName();

    protected OnEmptyStateListener onEmptyStateListener;
    protected List<Item> mItems = new ArrayList<>();
    protected List<Item> mVisibleItems = new ArrayList<>();

    public BaseFiltrableRecyclerAdapter(OnEmptyStateListener listener) {
        onEmptyStateListener = listener;
    }

    public void clearItems() {
        mItems.clear();

        mVisibleItems.clear();

        if (onEmptyStateListener != null) {
            onEmptyStateListener.onAdapterEmpty();
        }

        notifyDataSetChanged();
    }

    protected List<Item> getItems() {
        return mItems;
    }

    protected List<Item> getVisibleItems() {
        return mVisibleItems;
    }

    public void setItems(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);

        mVisibleItems.clear();
        mVisibleItems.addAll(mItems);

        notifyEmptyState();

        notifyDataSetChanged();
    }

    protected void notifyEmptyState() {
        if (onEmptyStateListener != null) {
            if (mVisibleItems.size() > 0) {
                onEmptyStateListener.onAdapterNotEmpty();
            } else {
                onEmptyStateListener.onAdapterEmpty();
            }
        }
    }

    public void addItem(Item item) {
        mItems.add(item);

        mVisibleItems.add(item);

        notifyEmptyState();

        notifyDataSetChanged();
    }

    protected void removeItemInPosition(int position) {
        mItems.remove(position);

        mVisibleItems.remove(position);

        notifyEmptyState();

        notifyItemRemoved(position);
    }

    public void updateItem(Item item) {
        int itemPos = getItems().indexOf(item);

        if (itemPos > -1) {
            getItems().set(itemPos, item);

            notifyDataSetChanged();
        }
    }

    public void flushFilter() {
        mVisibleItems.clear();
        mVisibleItems.addAll(mItems);

        notifyEmptyState();

        notifyDataSetChanged();
    }

    public void setFilter(String query) {
        mVisibleItems.clear();

        addItemsAccordingToFilter(query);

        notifyEmptyState();

        notifyDataSetChanged();
    }

    protected abstract void addItemsAccordingToFilter(String query);

    @Override
    public int getItemCount() {
        return mVisibleItems.size();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bindViews(mVisibleItems.get(position));
    }

    public interface OnEmptyStateListener {
        void onAdapterEmpty();

        void onAdapterNotEmpty();
    }

    public static abstract class BaseViewHolder<Item> extends RecyclerView.ViewHolder {

        public BaseViewHolder(View view) {
            super(view);
        }

        public abstract void bindViews(Item item);

    }

}