package com.natali_pi.home_money.statistic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.BaseAdapter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Category;
import com.natali_pi.home_money.models.CategoryMoney;
import com.natali_pi.home_money.utils.SwipeableViewHolder;
import com.natali_pi.home_money.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantyn Zakharchenko on 19.07.2018.
 */

public class StatisticsAdapter extends BaseAdapter<CategoryMoney> {
    public StatisticsAdapter(List<CategoryMoney> items) {
        super(items);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    protected void onItemSwiped(SwipeableViewHolder viewHolder) {

    }

    @Override
    protected void onItemNormal(SwipeableViewHolder viewHolder, int position) {
        /*if (holders.size() >= position){
            holders.add(((ViewHolder)viewHolder));
        }else {
            holders.set(position, ((ViewHolder) viewHolder));
        }*/
        ((ViewHolder)viewHolder).name.setText(getItems().get(position).getCategory().getName());
        if (getItems().get(position).getCategory().getPhoto() != null) {
            Picasso.with(((ViewHolder) viewHolder).name.getContext()).load(getItems().get(position).getCategory().getPhoto()).into(((ViewHolder) viewHolder).icon);
        } else {
            ((ViewHolder) viewHolder).icon.setImageResource(R.drawable.no_photo_statistics);
            ((ViewHolder) viewHolder).icon.setBackgroundColor(Utils.nameToCollor(getItems().get(position).getCategory().getName()));
        }
        ((ViewHolder)viewHolder).value.setText(""+getItems().get(position).getMoney().getBill());
        ((ViewHolder)viewHolder).currency.setText(getItems().get(position).getMoney().getCurrency().getValue());
    }

    @Override
    protected void onItemDeleted(CategoryMoney item) {

    }

    public int highlightCategory(RecyclerView categoriesList, Category category) {
        int position = -1;
            for (int i = 0; i < getItems().size(); i++) {

                    if (getItems().get(i).getCategory().getId().equals(category.getId())) {
                        RecyclerView.ViewHolder viewHolder = categoriesList.findViewHolderForAdapterPosition(i);
                        if (viewHolder!= null) {
                            ((ViewHolder) viewHolder).name.setBackgroundColor(((ViewHolder) viewHolder).name.getContext().getResources().getColor(R.color.yellow));

                        }
                        position = i;
                    } else {

                        RecyclerView.ViewHolder viewHolder = categoriesList.findViewHolderForAdapterPosition(i);
                        if (viewHolder != null) {
                            ((ViewHolder) viewHolder).name.setBackgroundColor(((ViewHolder) viewHolder).name.getContext().getResources().getColor(R.color.darkViolet));
                        }
                    }

        }
        return position;
    }

    static class ViewHolder extends SwipeableViewHolder {
        public ViewHolder(ViewGroup parent) {
            super(parent);
        }

        TextView name;

        ImageView icon;
        TextView value;
        TextView currency;
        @Override
        public View prepareMainView() {
            View view = inflate(R.layout.item_statistic);
            name = (TextView) view.findViewById(R.id.name);
            icon = (ImageView) view.findViewById(R.id.icon);
            value = (TextView) view.findViewById(R.id.value);
            currency = (TextView) view.findViewById(R.id.currency);
            // holder = (RelativeLayout) view.findViewById(R.id.holder);
            //    text = (TextView) view.findViewById(R.id.text);
            //   icon = (ImageView) view.findViewById(R.id.icon);
            return view;
        }

    }
}
