package com.natali_pi.home_money.utils;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.R;

/**
 * Created by Konstantyn Zakharchenko on 30.01.2018.
 */
/**
 * ViewHolder capable of presenting two states: "normal" and "undo" state.
 */
public abstract class SwipeableViewHolder extends RecyclerView.ViewHolder {


    private TextView undoButton;
    private View view;
    private RelativeLayout relativeLayout;
    public SwipeableViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false));
        undoButton = (TextView) itemView.findViewById(R.id.undo_button);

        relativeLayout = (RelativeLayout)itemView.findViewById(R.id.holder);
        view = prepareMainView();
        relativeLayout.addView(view);
    }
public void setTransparentBase(){
    itemView.setBackgroundColor(Color.TRANSPARENT);
}
    protected View inflate(@LayoutRes int layoutId){
        return LayoutInflater.from(relativeLayout .getContext()).inflate(layoutId, relativeLayout, false);
    }
    public abstract View prepareMainView();

    public View getView(){
        return view;
    }

    public TextView getUndoButton() {
        return undoButton;
    }
}