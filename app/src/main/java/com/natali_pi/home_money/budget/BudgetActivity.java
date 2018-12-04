package com.natali_pi.home_money.budget;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.natali_pi.home_money.DraweredActivity;
import com.natali_pi.home_money.R;

import java.util.ArrayList;
import java.util.List;

public class BudgetActivity extends DraweredActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_sliding);
        setupToolbar(R.drawable.burger, "");
        setupSideDrawer();
        //activity_sliding
        setupLabel(getString(R.string.add_spending));
        hideHighlight();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabLayout(0,getString(R.string.may_budget));
        setTabLayout(1,getString(R.string.family_budget));

    }

    public void setTabLayout(int index, String text) {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
        tabOne.setText(text);
        tabLayout.getTabAt(index).setCustomView(tabOne);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new BudgetFragment(), getString(R.string.chooseCategory));
        adapter.addFragment(new BudgetFragment(), getString(R.string.baseData));
        viewPager.setAdapter(adapter);

    }

    @Override
    protected void onBitmapLoaded(DraweredActivity.TAG tag, Bitmap bitmap) {

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

