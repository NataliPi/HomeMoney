package com.natali_pi.home_money.settings;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.utils.Currency;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.TextPickerDialog;
import com.natali_pi.home_money.utils.views.DropdownView;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class SettingActivity extends BaseActivity {
    SettingsPresenter presenter = SettingsPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setView(this);
        setBaseContentView(R.layout.activity_setting);
        setupToolbar(R.drawable.arrow, "");
        setupOption(R.drawable.ok);
        setHighlightedText(getString(R.string.settings));
        setNavigationButtonListener(getBackAction());
        EditText name = (EditText) findViewById(R.id.name);
        EditText familyName = (EditText) findViewById(R.id.familyName);
        //TODO: Продумать смены фамилии семьи и человека.
        final ImageView avatar = (ImageView) findViewById(R.id.spend_photo);
        name.setText(DataBase.getInstance().getHuman().getName());
        familyName.setText(DataBase.getInstance().getHuman().getFamilyName());
        Picasso.with(this).load(DataBase.getInstance().getHuman().getPhoto())
                .transform(new CropCircleTransformation())
                .placeholder(R.drawable.photo)
                .into(avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(getPickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult result) {
                                Bitmap photo = result.getBitmap();
                                avatar.setImageBitmap(new CropCircleTransformation().transform(photo.copy(null, false)));
                                presenter.setAvatar(photo);

                            }
                        }).show(SettingActivity.this);
            }
        });

        Switch notificationsSwitch = (Switch) findViewById(R.id.notificationsSwitch);
        notificationsSwitch.setChecked(DataBase.getInstance().getHuman().getSettings().isNotificationOn());
        Switch passwordSwitch = (Switch) findViewById(R.id.passwordSwitch);
        passwordSwitch.setChecked(DataBase.getInstance().getHuman().getSettings().isPasswordProtected());
        Switch premiumSwitch = (Switch) findViewById(R.id.premiumSwitch);
        premiumSwitch.setChecked(DataBase.getInstance().getHuman().getSettings().isHasPremium());
        TextView currency = findViewById(R.id.currency);
        currency.setText(DataBase.getInstance().getCurrentCurrency());
        currency.setOnClickListener((v)->{
            new TextPickerDialog(this, "", DataBase.getInstance().getCurrentCurrencyValue().ordinal(), Currency.getAsList())
                    .setOnDoneListener((result)->{
                        currency.setText(Currency.getAsList().get( (int)result));
                    }).showMe();
        });
        setOptionButtonListener((v) -> {
            Human data = DataBase.getInstance().getHuman().copy();
            data.setName(name.getText().toString());
            data.setFamilyName(familyName.getText().toString());
            data.getSettings().setNotificationOn(notificationsSwitch.isChecked());
            data.getSettings().setPasswordProtected(passwordSwitch.isChecked());
            data.getSettings().setHasPremium(premiumSwitch.isChecked());
            data.getSettings().setDefaultCurrency(Currency.valueOf(currency.getText().toString()));
            presenter.updateProfile(data);
        });



    }

}
