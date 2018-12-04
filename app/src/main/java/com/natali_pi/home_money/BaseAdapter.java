package com.natali_pi.home_money;

/**
 * Created by Konstantyn Zakharchenko on 29.01.2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.natali_pi.home_money.utils.SwipeableViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RecyclerView adapter enabling undo on a swiped away item.
 */
public abstract class BaseAdapter<ItemsType> extends RecyclerView.Adapter {

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec++++++++++

    List<ItemsType> items = new ArrayList<>();
    List<ItemsType> itemsPendingRemoval = new ArrayList<>();

    public List<ItemsType> getItems() {
        return items;
    }

    private Handler handler = new Handler(); // hanlder for running delayed runnables
    HashMap<ItemsType, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

    public BaseAdapter(List<ItemsType> items) {
        if(items != null) {
            this.items.addAll(items);
        }
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SwipeableViewHolder viewHolder = (SwipeableViewHolder) holder;
        if(position >= items.size()){
            onItemNormal(viewHolder, position);
            return;
        }
        final ItemsType item = items.get(position);

        if (itemsPendingRemoval.contains(item)) {
            // we need to show the "undo" state of the row
            viewHolder.itemView.setBackgroundResource(R.color.darkViolet);
            viewHolder.getView().setVisibility(View.INVISIBLE);
            viewHolder.getUndoButton().setVisibility(View.VISIBLE);
            onItemSwiped(viewHolder);
            viewHolder.getUndoButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // user wants to undo the removal, let's cancel the pending task

                    Runnable pendingRemovalRunnable = pendingRunnables.get(item);
                    pendingRunnables.remove(item);
                    if (pendingRemovalRunnable != null)
                        handler.removeCallbacks(pendingRemovalRunnable);
                    itemsPendingRemoval.remove(item);
                    // this will rebind the row in "normal" state

                    notifyItemChanged(items.indexOf(item));
                }
            });
        } else {
            // we need to show the "normal" state
            viewHolder.itemView.setBackgroundColor(Color.WHITE);
            viewHolder.getView().setVisibility(View.VISIBLE);
            viewHolder.getUndoButton().setVisibility(View.GONE);
            viewHolder.getUndoButton().setOnClickListener(null);
            onItemNormal(viewHolder, position);
        }
    }

    protected abstract void onItemSwiped(SwipeableViewHolder viewHolder);

    protected abstract void onItemNormal(SwipeableViewHolder viewHolder, int position);

    protected abstract void onItemDeleted(ItemsType item);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void pendingRemoval(int position) {
        final ItemsType item = items.get(position);
        if (!itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.add(item);
            // this will redraw row in "undo" state
            notifyItemChanged(position);
            // let's create, store and post a runnable to remove the item
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remove(items.indexOf(item));

                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(item, pendingRemovalRunnable);
        }
    }

    public void remove(int position) {
        ItemsType item = items.get(position);
        if (itemsPendingRemoval.contains(item)) {
            itemsPendingRemoval.remove(item);
        }
        if (items.contains(item)) {
            items.remove(position);
            notifyItemRemoved(position);
        }
        notifyDataSetChanged();
        onItemDeleted(item);
    }

    public boolean isPendingRemoval(int position) {
       try {
           ItemsType item = items.get(position);
           return itemsPendingRemoval.contains(item);
       }catch (IndexOutOfBoundsException iobe){
           return false;
       }
    }

    public void hideKeyboard(View view){

        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}



