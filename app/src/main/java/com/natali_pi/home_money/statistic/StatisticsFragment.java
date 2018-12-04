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

import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsFragment extends BaseFragment {


    @Override
    protected void resolveDaggerDependencies() {

    }

    @Override
    protected int contentView() {
        return R.layout.diagram_view;
    }

    @Override
    protected View onCreateView(View root) {

        PieChart diagram = (PieChart) root.findViewById(R.id.diagram);


        TextView totalMoney = (TextView) root.findViewById(R.id.totalMoney);
        totalMoney.setText(DataBase.getInstance().getFamily().calculateSpendingsMoney(PURPOSE.SPENDED).getPureMoney());
        RecyclerView categoriesList = (RecyclerView) root.findViewById(R.id.categoriesList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        categoriesList.setLayoutManager(layoutManager);
        prepareViews(Utils.getTodayKeys(), diagram, categoriesList);
        ImageView calenadar = (ImageView) root.findViewById(R.id.calenadar);
        calenadar.setOnClickListener(new PickDateDialog(getActivity(),(date)->{
            Log.d("date", date[0]+":"+(date[1]+1)+":"+date[2]);
            prepareViews(date, diagram, categoriesList);

        }));
        return root;
    }

    private void prepareViews(int[] date, PieChart diagram, RecyclerView categoriesList){
        HashMap<String, Money> categoriesMoneyByMonth = DataBase.getInstance().getFamily().getCategoriesMoneyByMonth(date);
        ArrayList<CategoryMoney> items = DataBase.getInstance().getFamily().mapCategoriesMoney(categoriesMoneyByMonth);
        diagram.setSlices(DataBase.getInstance().getFamily().getCategoriesPercentsByMonth(categoriesMoneyByMonth), items);
        categoriesList.setAdapter(new StatisticsAdapter(items));
        diagram.setOnSliceClickListener((category) -> {
            int position = ((StatisticsAdapter) categoriesList.getAdapter()).highlightCategory(categoriesList, category);

            if (position < ((LinearLayoutManager)categoriesList.getLayoutManager()).findFirstVisibleItemPosition() ||position>  ((LinearLayoutManager)categoriesList.getLayoutManager()).findLastVisibleItemPosition()) {
                if (position < ((LinearLayoutManager)categoriesList.getLayoutManager()).findFirstVisibleItemPosition()) {
                    categoriesList.scrollToPosition(0);
                    categoriesList.post(() -> {
                        categoriesList.scrollToPosition(position);
                        ((StatisticsAdapter) categoriesList.getAdapter()).highlightCategory(categoriesList, category);
                    });

                } else {
                    categoriesList.scrollToPosition(categoriesList.getAdapter().getItemCount()-1);
                    categoriesList.post(() -> {
                        categoriesList.scrollToPosition(position);
                        ((StatisticsAdapter) categoriesList.getAdapter()).highlightCategory(categoriesList, category);
                    });

                }
            }

        });
    }
}
