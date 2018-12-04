package com.natali_pi.home_money.statistic;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.natali_pi.home_money.BaseFragment;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.CategoryMoney;
import com.natali_pi.home_money.models.Money;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;
import com.natali_pi.home_money.utils.PickDateDialog;
import com.natali_pi.home_money.utils.Utils;
import com.natali_pi.home_money.utils.views.PieChart;
import com.natali_pi.home_money.utils.views.line_chart.LineChartView;

import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsFragment2 extends BaseFragment {


    @Override
    protected void resolveDaggerDependencies() {

    }

    @Override
    protected int contentView() {
        return R.layout.fragment_statistic2;
    }

    @Override
    protected View onCreateView(View root) {


        LineChartView diagram = (LineChartView) root.findViewById(R.id.diagram);
        prepareViews(Utils.getTodayKeys(), diagram);


        ImageView calenadar = (ImageView) root.findViewById(R.id.calenadar);
        calenadar.setOnClickListener(new PickDateDialog(getActivity(),(date)->{
            Log.d("date", date[0]+":"+(date[1]+1)+":"+date[2]);
            prepareViews(date, diagram);

        }));
        return root;
    }

    private void prepareViews(int[] date, LineChartView diagram){
        HashMap<String, Money> categoriesMoneyByMonth = DataBase.getInstance().getFamily().getCategoriesMoneyByMonth(date);
        ArrayList<CategoryMoney> items = DataBase.getInstance().getFamily().mapCategoriesMoney(categoriesMoneyByMonth);
        diagram.setData(items);
    }
}
