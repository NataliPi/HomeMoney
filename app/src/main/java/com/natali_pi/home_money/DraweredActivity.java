package com.natali_pi.home_money;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.natali_pi.home_money.budget.BudgetActivity;
import com.natali_pi.home_money.family_settings.FamilySettingActivity;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.planned_spending.PlannedSpendingActivity;
import com.natali_pi.home_money.search.Search;
import com.natali_pi.home_money.settings.SettingActivity;
import com.natali_pi.home_money.statistic.StatisticActivity;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Natali-Pi on 11.12.2017.
 */

public abstract class DraweredActivity extends BaseActivity {
    abstract protected void onBitmapLoaded(TAG tag, Bitmap bitmap);

    public enum TAG {
        AVATAR
    }

    @Override
    public void setupSideDrawer() {
        super.setupSideDrawer();
        final ImageView imageView = (ImageView) getDrawer().findViewById(R.id.imageView);
        TextView name = (TextView) findViewById(R.id.name);
        TextView familyName = (TextView) findViewById(R.id.familyName);
        LinearLayout background = (LinearLayout) findViewById(R.id.background);
        DataBase.getInstance().subscribeOnHuman((human -> {

            Picasso.with(this).load(human.getPhoto())
                    .transform(new CropCircleTransformation())
                    .placeholder(R.drawable.photo)
                    .into(imageView);

            name.setText(human.getName());
            familyName.setText(human.getFamilyName());
        }));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(getPickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult result) {
                                Bitmap bitmap = result.getBitmap();
                                Log.d("photo", result.getPath());
                                if (bitmap != null) {
                                    imageView.setImageBitmap(new CropCircleTransformation().transform(bitmap.copy(Bitmap.Config.ARGB_8888, false)));
                                    onBitmapLoaded(TAG.AVATAR, bitmap);
                                }
                            }
                        }).show(DraweredActivity.this);
            }
        });




        ImageView settings = (ImageView) getDrawer().findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DraweredActivity.this, SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        TextView lastSpendings = (TextView) getDrawer().findViewById(R.id.last_spendings);
        lastSpendings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.yellow);
                Intent intent = new Intent(DraweredActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.TAG_PURPOSE, PURPOSE.SPENDED.ordinal());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        TextView plannedSpendings = (TextView) getDrawer().findViewById(R.id.planned_spendings);
        plannedSpendings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.yellow);
                Intent intent = new Intent(DraweredActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.TAG_PURPOSE, PURPOSE.PLANED.ordinal());
                //intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                startActivity(intent);
            }
        });

        TextView budget = (TextView) getDrawer().findViewById(R.id.budget);
        budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.yellow);
                Intent intent = new Intent(DraweredActivity.this, BudgetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        TextView statistics = (TextView) getDrawer().findViewById(R.id.statistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.yellow);
                Intent intent = new Intent(DraweredActivity.this, StatisticActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        TextView familySettings = (TextView) getDrawer().findViewById(R.id.familySettings);
        familySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.yellow);
                Intent intent = new Intent(DraweredActivity.this, FamilySettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
        TextView search = (TextView) getDrawer().findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.color.yellow);
                Intent intent = new Intent(DraweredActivity.this, Search.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        getDrawer().closeDrawers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearMenu();
    }

    private void clearMenu() {
        TextView lastSpendings = (TextView) getDrawer().findViewById(R.id.last_spendings);
        lastSpendings.setBackgroundResource(R.color.white);
        TextView search = (TextView) getDrawer().findViewById(R.id.search);
        search.setBackgroundResource(R.color.white);
        TextView plannedSpendings = (TextView) getDrawer().findViewById(R.id.planned_spendings);
        plannedSpendings.setBackgroundResource(R.color.white);
        TextView budget = (TextView) getDrawer().findViewById(R.id.budget);
        budget.setBackgroundResource(R.color.white);
        TextView statistics = (TextView) getDrawer().findViewById(R.id.statistics);
        statistics.setBackgroundResource(R.color.white);
        TextView familySettings = (TextView) getDrawer().findViewById(R.id.familySettings);
        familySettings.setBackgroundResource(R.color.white);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }
}
