package com.natali_pi.home_money.family_settings;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.natali_pi.home_money.R;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by Konstantyn Zakharchenko on 29.11.2017.
 */

public class GenerateQRActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);
        String password = getIntent().getStringExtra("DATA");

        ImageView qrCode = (ImageView) findViewById(R.id.qrCode);
        qrCode.setImageBitmap(QRCode.from(DataBase.getInstance().getFamily().getId() + "::" + password).bitmap());

    }

}