package com.natali_pi.home_money.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;

public class Search extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_search);
        setHighlightedText(getString(R.string.search));
    }
}
