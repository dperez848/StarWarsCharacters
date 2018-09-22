package com.checkapp.test.base;


import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<Item, Holder extends BaseRecyclerAdapter.BaseViewHolder<Item>>
        extends RecyclerView.Adapter<Holder> {

    private static final String TAG = BaseRecyclerAdapter.class.getSimpleName();

    protected OnEmptyStateListener onEmptyStateListener;
    protected List<Item> mItems = new ArrayList<>();

    public BaseRecyclerAdapter(OnEmptyStateListener listener) {
        onEmptyStateListener = listener;
    }

    public void clearItems() {
        mItems.clear();

        if (onEmptyStateListener != null) {
            onEmptyStateListener.onAdapterEmpty();
        }

        notifyDataSetChanged();
    }

    protected List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);

        notifyEmptyState();

        notifyDataSetChanged();
    }

    protected void notifyEmptyState() {
        if (onEmptyStateListener != null) {
            if (mItems.size() > 0) {
                onEmptyStateListener.onAdapterNotEmpty();
            } else {
                onEmptyStateListener.onAdapterEmpty();
            }
        }
    }

    public void addItem(Item item) {
        mItems.add(item);

        notifyEmptyState();

        notifyDataSetChanged();
    }

    protected void removeItemInPosition(int position) {
        mItems.remove(position);

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

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindView(mItems.get(position));
    }

    public interface OnEmptyStateListener {
        void onAdapterEmpty();

        void onAdapterNotEmpty();
    }

    public static abstract class BaseViewHolder<Item> extends RecyclerView.ViewHolder {

        private Context context;

        public BaseViewHolder(View view) {
            super(view);
            this.context = view.getContext();
        }

        protected abstract void bindView(Item var1);

        @CallSuper
        public Context getContext() {
            return this.context;
        }

    }


}