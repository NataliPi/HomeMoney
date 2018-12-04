package com.natali_pi.home_money.spended;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.DraweredActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.add_spending.ComponentsAdapter;
import com.natali_pi.home_money.add_spending.SpendingActivity;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Category;
import com.natali_pi.home_money.models.Spending;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static com.natali_pi.home_money.main.MainActivity.TAG_PURPOSE;

public class SpendedActivity extends DraweredActivity {
    private PURPOSE purpose;
    SpendedPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_spended);
        setupToolbar(R.drawable.burger, "");
        setupOption(R.drawable.pen);
        setupLabel(null);
        setHighlightedText(null);
        setupSideDrawer();
        presenter = new SpendedPresenter(this);
        purpose = PURPOSE.values()[getIntent().getIntExtra(TAG_PURPOSE, 0)];
    }

    @Override
    protected void onBitmapLoaded(DraweredActivity.TAG tag, Bitmap bitmap) {
        presenter.uploadPicture(tag, bitmap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        purpose = PURPOSE.values()[getIntent().getIntExtra(TAG_PURPOSE, 0)];
        showData();
    }

    private void showData(){
        String spendingId = getIntent().getStringExtra(DATA);
        Spending spending =DataBase.getInstance().getFamily().getSpendingById(purpose, spendingId);
        //(Spending) getIntent().getSerializableExtra(DATA);
        setOptionButtonListener((v)-> {
            Intent intent = new Intent(SpendedActivity.this, SpendingActivity.class);
            intent.putExtra(DATA, spending);
            intent.putExtra(TAG_PURPOSE, purpose.ordinal());
            startActivity(intent);
        });
        TextView spendedName = (TextView) findViewById(R.id.spendedName);

        spendedName.setText(spending.getName());

        TextView categoryName = (TextView) findViewById(R.id.categoryName);
        Category category = DataBase.getInstance().getCategoryById(spending.getCategory());
        if(category.isStandard()){
            categoryName.setText(getStringResourceByName(category.getName()));
        }else {
            categoryName.setText(category.getName());
        }
        TextView date = (TextView) findViewById(R.id.date);
        date.setText(spending.getDate());
        TextView sumText = (TextView) findViewById(R.id.sumText);
        sumText.setText(spending.getSum().toString() + " " + spending.getSum().getCurrency());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

            Picasso.with(this).load(spending.getPhoto())
                    .transform(new CropCircleTransformation())
                    .placeholder(R.drawable.photo)
                    .into(imageView);


        RecyclerView componentsHolder = (RecyclerView) findViewById(R.id.componentsHolder);

        componentsHolder.setLayoutManager(new LinearLayoutManager(this));
        ComponentsAdapter componentsAdapter = new ComponentsAdapter(spending.getComponents(), false);
        componentsHolder.setAdapter(componentsAdapter);
       // setUpItemTouchHelper(componentsHolder);
       // setUpAnimationDecoratorHelper(componentsHolder);
    }
}
