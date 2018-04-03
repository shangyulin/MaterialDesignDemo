package com.example.shangyulin.materialdesigndemo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by shangyulin on 2018/4/3.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    public OnRemoveConfirmListener listener;

    public void setOnRemoveConfirmListener(OnRemoveConfirmListener listener) {
        this.listener = listener;
    }

    interface OnRemoveConfirmListener{
        void onremove(int position);
    }

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlag, swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        if (listener!=null){
            listener.onremove(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}
