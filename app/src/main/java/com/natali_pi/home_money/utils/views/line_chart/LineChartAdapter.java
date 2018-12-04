package com.natali_pi.home_money.utils.views.line_chart;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natali_pi.home_money.BaseAdapter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Category;
import com.natali_pi.home_money.models.CategoryMoney;
import com.natali_pi.home_money.utils.SwipeableViewHolder;
import com.natali_pi.home_money.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstantyn Zakharchenko on 19.07.2018.
 */

public class LineChartAdapter extends BaseAdapter<CategoryMoney> {
    int max = 0;
    int height = 0;
    public LineChartAdapter(List<CategoryMoney> items, int max, int height) {
        super(items);
        this.max = max;
        this.height = height;
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


        if (getItems().get(position).getCategory().getPhoto() != null) {
            Picasso.with(((ViewHolder) viewHolder).icon.getContext()).load(getItems().get(position).getCategory().getPhoto()).into(((ViewHolder) viewHolder).icon);
        } else {
            ((ViewHolder) viewHolder).icon.setImageResource(R.drawable.no_photo_statistics);
            ((ViewHolder) viewHolder).icon.setBackgroundColor(Utils.nameToCollor(getItems().get(position).getCategory().getName()));
        }


        ((ViewHolder) viewHolder).icon.setOnClickListener((v)->{
            Toast.makeText(((ViewHolder) viewHolder).icon.getContext(), getItems().get(position).getCategory().getName(), Toast.LENGTH_SHORT).show();
        });
        viewHolder.setTransparentBase();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)((ViewHolder) viewHolder).value.getLayoutParams();
            params.height = (int) (((float)height/(float) max)*getItems().get(position).getMoney().getAsFloat());
            ((ViewHolder) viewHolder).value.setLayoutParams(params);

        if (getItems().get(position).getCategory().getPhoto() != null){
            Picasso.with(((ViewHolder) viewHolder).value.getContext()).load(getItems().get(position).getCategory().getPhoto())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            Palette.from(bitmap)
                                    .generate((Palette palette) -> {
                                        Palette.Swatch textSwatch = palette.getVibrantSwatch();
                                        if (textSwatch == null) {
                                            textSwatch = palette.getDominantSwatch();
                                            if (textSwatch == null) {
                                                return;
                                            }//ffe8e8c0
                                        }
                                        ((ViewHolder) viewHolder).value.setBackgroundColor(textSwatch.getRgb());

                                    });
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            Log.d("picasso", "error");
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        } else {
            ((ViewHolder) viewHolder).value.setBackgroundColor(Utils.nameToCollor(getItems().get(position).getCategory().getName()));


        }
    }

    @Override
    protected void onItemDeleted(CategoryMoney item) {

    }


    static class ViewHolder extends SwipeableViewHolder {
        public ViewHolder(ViewGroup parent) {
            super(parent);
        }

        ImageView icon;
        ImageView value;


        @Override
        public View prepareMainView() {
            View view = inflate(R.layout.line_chart_item);
            value = (ImageView) view.findViewById(R.id.value);
            icon = (ImageView) view.findViewById(R.id.icon);

            return view;
        }

    }
}
