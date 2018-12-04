package com.natali_pi.home_money.statistic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.add_spending.SpendingActivity;
import com.natali_pi.home_money.models.CategoryMoney;
import com.natali_pi.home_money.models.Money;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;
import com.natali_pi.home_money.utils.PickDateDialog;
import com.natali_pi.home_money.utils.Utils;
import com.natali_pi.home_money.utils.views.PieChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticActivity extends BaseActivity {

    StatisticsFragment statisticsFragment = new StatisticsFragment();
    StatisticsFragment2 statisticsFragment2 = new StatisticsFragment2();

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(statisticsFragment, getString(R.string.pie_chart));
        adapter.addFragment(statisticsFragment2, getString(R.string.bar_chart));
        viewPager.setAdapter(adapter);

    }
    public void setTabLayout(int index, String text) {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
        tabOne.setText(text);
        tabLayout.getTabAt(index).setCustomView(tabOne);
    }
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_statistics);
        setNavigationButtonListener(getBackAction());
        hideHighlight();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabLayout(0, getString(R.string.pie_chart));
        setTabLayout(1, getString(R.string.bar_chart));

    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
