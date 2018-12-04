package com.natali_pi.home_money.utils.views.line_chart;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.CategoryMoney;

import java.util.ArrayList;

/**
 * Created by Konstantyn Zakharchenko on 23.07.2018.
 */

public class LineChartView extends RelativeLayout {
    private ArrayList<CategoryMoney> data;
    private View view;
    private TextView[] lines=  new TextView[9];
    private RecyclerView list;
    private int[] linesIds = new int[]{R.id.line1, R.id.line2, R.id.line3, R.id.line4, R.id.line5, R.id.line6, R.id.line7, R.id.line8, R.id.line9};
    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        addView(view = LayoutInflater.from(context).inflate(R.layout.line_chart, null));
        for (int i = 0; i < linesIds.length; i++) {
            lines[i] = findViewById(linesIds[i]);
        }
        list = findViewById(R.id.list);
    }

    public void setData(ArrayList<CategoryMoney> data) {
        this.data = data;
        float max = 0.0f;
        for (int i = 0; i < data.size(); i++) {
            float value = data.get(i).getMoney().getAsFloat();
            if (value > max) {
                max = value;
            }
        }

        String tempMax = ""+(int)max;
        if (tempMax.length() > 2) {
            max = max - (max % (int)Math.pow(10, tempMax.length()-1));
            max += Math.pow(10, tempMax.length() -1);
        }

        for (int i = 0; i < lines.length; i++) {
            lines[i].setText(""+ (int)((max/10) * (i+1)));
        }
list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        float finalMax = max;
        list.post(()->{
            int height = findViewById(R.id.lines).getHeight();
            list.setAdapter(new LineChartAdapter(data, (int) finalMax, height));
        });

    }
}
