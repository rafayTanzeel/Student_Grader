package com.assignmentrecord.labgroup.finalyearprojectdemo.Activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;


public class SimpleTouchCallback extends ItemTouchHelper.Callback {
    SwipeListener mListener;
    public SimpleTouchCallback(SwipeListener listener) {
        mListener=listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.END);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onSwip(viewHolder.getAdapterPosition());
        //Snackbar.make(viewHolder.itemView.getRootView(), "Item Successfully Deleted", Snackbar.LENGTH_SHORT).show();
    }
}
