package com.natali_pi.home_money.add_spending;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Spending;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;
import com.natali_pi.home_money.utils.views.ViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.natali_pi.home_money.main.MainActivity.TAG_PURPOSE;

public class SpendingActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    SpendingPresenter presenter = SpendingPresenter.getInstance();
    private CategoryFragment categoryFragment = new CategoryFragment();
    private SpendingFragment spendingFragment = new SpendingFragment();
    private PURPOSE purpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        purpose = PURPOSE.values()[getIntent().getIntExtra(TAG_PURPOSE, 0)];
        presenter.setView(this);
        setBaseContentView(R.layout.activity_sliding);
        setupToolbar(R.drawable.arrow, "");
        setNavigationButtonListener(getBackAction());
        setupOption(R.drawable.plus);
        setupLabel(getString(R.string.add_spending));
        hideHighlight();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        setOptionButtonListener((view -> {
            if (presenter.getCategory() != null) {
                presenter.setSpending(purpose, spendingFragment.getSpending());
            } else {
                if (categoryFragment.isEditable()){
                    categoryFragment.finishEditable();
                } else {
                    viewPager.setCurrentItem(0);
                }
            }
        }));
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setTabLayout(0, getString(R.string.chooseCategory));
        setTabLayout(1, getString(R.string.baseData));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                    if (tabLayout.getTabAt(0) == tab) {
                        setupSecondOption(R.drawable.pen);
                    } else {

                            hideSecondOption();
                            setupOption(R.drawable.plus);

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (getIntent().hasExtra(DATA)) {
            inputSpendingData((Spending) getIntent().getSerializableExtra(DATA));
        } else {
            setupSecondOption(R.drawable.pen);

        }
    }

    @Override
    public void hideSecondOption() {
        super.hideSecondOption();

        if (categoryFragment.isEditable()) {
            viewPager.setPagingEnabled(false);
            findViewById(R.id.tabDisabler).setOnTouchListener((v, d) -> true);
        } else {
            viewPager.setPagingEnabled(true);
            findViewById(R.id.tabDisabler).setOnTouchListener((v, d) -> false);
        }
    }

    @Override
    public void showSecondOption() {
        super.showSecondOption();
        viewPager.setPagingEnabled(true);
        findViewById(R.id.tabDisabler).setOnTouchListener((v,d)->false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    private void inputSpendingData(Spending spending) {
        Bundle data = new Bundle();
        data.putSerializable(DATA, spending);
        categoryFragment.setArguments(data);
        spendingFragment.setArguments(data);
    }

    public void toSpendig() {
        viewPager.setCurrentItem(1);
    }

    public void setTabLayout(int index, String text) {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
        tabOne.setText(text);
        tabLayout.getTabAt(index).setCustomView(tabOne);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        categoryFragment.setPresenter(presenter);
        adapter.addFragment(categoryFragment, getString(R.string.chooseCategory));
        adapter.addFragment(spendingFragment, getString(R.string.baseData));
        viewPager.setAdapter(adapter);

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

    public void updateCategoriesList() {
        categoryFragment.setAdapter(DataBase.getInstance().getFamily().getCategories());
        categoryFragment.setEditable();
    }
}
