package com.natali_pi.home_money.utils.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Category;
import com.natali_pi.home_money.models.CategoryMoney;
import com.natali_pi.home_money.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Konstantyn Zakharchenko on 23.04.2018.
 */

public class PieChart extends RelativeLayout {
    List<Integer> slices = new ArrayList<>();
    List<CategoryMoney> categoryMonies = new ArrayList<>();
    List<ProgressBar> views = new ArrayList<>();
    List<TextView> labels = new ArrayList<>();
    int base;

    public void setSlices(List<Integer> slices, List<CategoryMoney> categoryMonies) {
        this.slices = slices;
        this.categoryMonies = categoryMonies;
        int total = 0;

        for (int i = 0; i < slices.size(); i++) {
            total += slices.get(i);
        }
        if (total < 100) {

            for (int i = 0; total < 100 && i < slices.size(); i++) {
                slices.set(i, slices.get(i) + 1);
                total++;
            }

        }

        clear();
        init();
    }

    private void clear() {
        removeAllViews();
        views = new ArrayList<>();
        labels = new ArrayList<>();
    }

    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PieChart, 0, 0);
        base = a.getResourceId(R.styleable.PieChart_centerImage, R.drawable.icon);
        /*ColorStateList color = a.getColorStateList(R.styleable.PieChart_textColor);
        int base = a.getResourceId(R.styleable.PieChart_centerImage, R.drawable.icon);*/
        slices.add(5);
        slices.add(10);
        slices.add(15);
        slices.add(20);
        slices.add(25);
        slices.add(25);

        init();

    }

    private void init() {
        int summ = 0;
        for (int i = 0; i < slices.size(); i++) {
            summ += slices.get(i);
            Random rnd = new Random();
            //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            if (categoryMonies.size() > 0) {
                views.add(createProgress(summ, categoryMonies.get(i)));
            }
            labels.add(createLabel(summ, slices.get(i)));

        }

        for (int i = views.size() - 1; i >= 0; i--) {
            addView(views.get(i));
        }


        addView(getCenterImage(base));
        for (TextView label : labels) {
            addView(label);
        }
    }

    private TextView createLabel(float summ, float currentSlice) {
        TextView textView = new TextView(getContext());

        post(() -> {
            double hypo = getWidth() - 70;

            RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);


            float angle = getAngle(summ - currentSlice / 2);
            double x = hypo * Math.sin(angle * 0.0174533);
            double y = hypo * Math.cos(angle * 0.0174533);
            x = x < 0 ? -x : x;
            y = y < 0 ? -y : y;


            textView.setText((int) currentSlice + "%");
            textView.setTextColor(getResources().getColor(R.color.darkViolet));
            //textView.setTextSize(getResources().getDimension(R.dimen.text_medium));
            textView.setLayoutParams(layoutParams);
            // textView.setPadding((int)x,(int)y,0,0);
            if (angle < 90) {
                textView.setPadding((int) x, 0, 0, (int) y);
            } else if (angle >= 90 && angle < 180) {
                textView.setPadding((int) x, (int) y, 0, 0);
            } else if (angle >= 180 && angle < 270) {
                textView.setPadding(0, (int) y, (int) x, 0);
            } else if (angle >= 270 && angle < 360) {
                textView.setPadding(0, 0, (int) x, (int) y);
            }

        });
        return textView;

    }

    public float getAngle(float persent) {
        return 3.6f * persent;
    }

    private Drawable getPaintedDrawable(int color) {
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.pie_drawable);
        GradientDrawable gDrawable = (GradientDrawable) ((RotateDrawable) ((LayerDrawable) drawable).getDrawable(0)).getDrawable();
        gDrawable.setColor(color);
        return drawable;
    }

    private RelativeLayout getCenterImage(int resId) {
        RelativeLayout.LayoutParams baseLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        baseLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        layoutParams.setMargins(350, 350, 350, 350);

        RelativeLayout base = new RelativeLayout(getContext());

        ImageView background1 = new ImageView(getContext());

        ImageView background2 = new ImageView(getContext());
        background1.setImageResource(R.drawable.background);

        background2.setImageResource(R.drawable.background);
        background2.setAlpha(0.5f);
        background1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        background2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(resId);

        imageView.setLayoutParams(layoutParams);
        imageView.post(() -> {
            RelativeLayout.LayoutParams baseParams = new LayoutParams(imageView.getWidth() + 250, imageView.getWidth() + 250);
            baseParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            background1.setLayoutParams(baseParams);
            background2.setLayoutParams(baseParams);
        });
        background1.setLayoutParams(layoutParams);
        background2.setLayoutParams(layoutParams);

        background1.setPadding(65, 65, 65, 65);

        base.setLayoutParams(baseLayoutParams);
        base.addView(background1);
        base.addView(background2);
        base.addView(imageView);


        return base;
    }

    private ProgressBar createProgress(int progress, CategoryMoney category) {
        ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgress(progress);
        progressBar.setIndeterminate(false);
if (category.getCategory().getPhoto() != null){
        Picasso.with(getContext()).load(category.getCategory().getPhoto())
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
                                    Drawable drawable = getPaintedDrawable(textSwatch.getRgb());
                                    progressBar.setProgressDrawable(drawable);
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
    Drawable drawable = getPaintedDrawable(Utils.nameToCollor(category.getCategory().getName()));
    progressBar.setProgressDrawable(drawable);

}

        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        layoutParams.setMargins(50, 50, 50, 50);
        progressBar.setLayoutParams(layoutParams);

       progressBar.setOnTouchListener(new OnSliceTouchListener(category.getCategory()));

        return progressBar;
    }
private class OnSliceTouchListener implements OnTouchListener{
    private Category category;

    public OnSliceTouchListener(Category category) {
        this.category = category;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int[] position = Utils.getPosition(PieChart.this);
        float centreX=position[0] + PieChart.this.getWidth()  / 2;
        float centreY=position[1] + PieChart.this.getHeight() / 2;

        float direction = getDirection(event.getRawX() - centreX, centreY - event.getRawY() );
        //float maxDirection = (360/100)*((ProgressBar)v).getProgress();
        int total = 0;
        for (int i = 0; i < slices.size(); i++) {
            total += slices.get(i);
            if (direction < 3.6f * total){

                    onSliceClickListener.onSliceClicked(categoryMonies.get(i).getCategory());

                return true;

            }
        }
        /*if (direction< maxDirection){
            onSliceClickListener.onSliceClicked(category);
            return true;
        }*/
        return false;
    }
}

    public float getLength(float coordX, float coordY) {
        // Length of vector = sqrt(x*x + y*y)
        return (float) Math.hypot(coordX, coordY);
    }
    public float getDirection(float coordX, float coordY ) {

        // Getting angle
        float directionInRads = (float) Math.acos (  coordY / getLength(coordX, coordY) );
        // Converting radians to degrees
        float direction =  (float) (  directionInRads * 180 / Math.PI) ;
        // Reversing of acos result if wind is blowing from port side
        if(coordX < 0){ direction = 180 + ( 180 - direction );}
        // Magical turning of angle to acquire 0 at N otherwise it is on E
        //direction = 450 - direction;

        // Returning back to 0 - 360 degrees range
        direction = direction % 360;
        return direction;

    }
private OnSliceClickListener onSliceClickListener;

    public void setOnSliceClickListener(OnSliceClickListener onSliceClickListener) {
        this.onSliceClickListener = onSliceClickListener;
    }

    public interface OnSliceClickListener{
        void onSliceClicked(Category category);
    }
}
