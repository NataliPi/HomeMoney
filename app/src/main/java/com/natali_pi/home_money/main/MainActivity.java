package com.natali_pi.home_money.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import com.natali_pi.home_money.DraweredActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.add_spending.SpendingActivity;
import com.natali_pi.home_money.models.Spending;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DraweredActivity {
    private MainPresenter presenter;
    private List<DaySpendingsFragment> fragments = new ArrayList<>();
    private PURPOSE purpose;

    public static final String TAG_PURPOSE = "PURPOSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        purpose = PURPOSE.values()[getIntent().getIntExtra(TAG_PURPOSE, 0)];

        presenter = new MainPresenter(this);
        setBaseContentView(R.layout.activity_main);
        setupToolbar(R.drawable.burger, "");
        setupOption(R.drawable.plus);
        if(purpose == PURPOSE.SPENDED) {
            setupLabel(getString(R.string.last_spendings));
        } else if(purpose == PURPOSE.PLANED){
            setupLabel(getString(R.string.planned_spendings));
        }
        setupSideDrawer();

        purpose = PURPOSE.values()[getIntent().getIntExtra(TAG_PURPOSE, 0)];

        setOptionButtonListener((v) -> {
            Intent intent = new Intent(MainActivity.this, SpendingActivity.class);
            intent.putExtra(MainActivity.TAG_PURPOSE, purpose.ordinal());
            startActivity(intent);
        });
        hideHighlight();


    }

    @Override
    protected void onResume() {
        super.onResume();
        purpose = PURPOSE.values()[getIntent().getIntExtra(TAG_PURPOSE, 0)];
        setupScroller();
    }

    private void setupScroller() {
        ((ViewGroup) findViewById(R.id.list)).removeAllViews();
        fragments.removeAll(fragments);
        final TextView dateText = (TextView) findViewById(R.id.dateText);
        TextView noSpendingsMessage = (TextView) findViewById(R.id.noSpendingsMessage);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        if (DataBase.getInstance().getFamily().getSpendings(purpose) != null &&
                DataBase.getInstance().getFamily().getSpendings(purpose).size() > 0) {
            dateText.setVisibility(View.VISIBLE);
            noSpendingsMessage.setVisibility(View.GONE);
            ArrayList<ArrayList<Spending>> spendingsByMMonth = DataBase.getInstance().getFamily().getSpendingsByMMonth(purpose);
            for (int i = 0; i < spendingsByMMonth.size(); i++) {

                DaySpendingsFragment daySpendingsFragment = new DaySpendingsFragment();
                daySpendingsFragment.setSpendings(purpose, spendingsByMMonth.get(i));

                getFragmentManager().beginTransaction().add(R.id.list, daySpendingsFragment).commit();
                fragments.add(daySpendingsFragment);
            }

        }
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int pos = getPosition(dateText);
                for (int i = 0; i < fragments.size(); i++) {
                    if (pos >= fragments.get(i).getDatePosition() - dateText.getHeight() / 2) {
                        dateText.setText(fragments.get(i).getMonth());
                    } else {
                        break;
                    }
                }
            }
        });
    }

    private int getPosition(TextView textView) {
        int[] pos = new int[2];
        textView.getLocationOnScreen(pos);
        return pos[1];
    }

    @Override
    protected void onBitmapLoaded(DraweredActivity.TAG tag, Bitmap bitmap) {
        presenter.uploadPicture(tag, bitmap);
    }


}