    package com.natali_pi.home_money.family_settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.natali_pi.home_money.DraweredActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.login.LoginActivity;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Message;
import com.natali_pi.home_money.utils.OneButtonDialog;
import com.natali_pi.home_money.utils.PURPOSE;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

public class FamilySettingActivity extends DraweredActivity {
    SettingsPresenter presenter = SettingsPresenter.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_family_setting);
        setHighlightedText(getString(R.string.family_setting));
        setupSideDrawer();
        presenter.setView(this);
        final ImageView familyPhoto = (ImageView) findViewById(R.id.familyPhoto);
        final ImageView whitephoto = (ImageView) getDrawer().findViewById(R.id.whitephoto);
        whitephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(getPickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult result) {
                                familyPhoto.setImageBitmap(result.getBitmap());
                            }
                        }).show(FamilySettingActivity.this);
            }
        });

        TextView deleteFamily = (TextView) findViewById(R.id.deleteFamily);
        deleteFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OneButtonDialog(FamilySettingActivity.this, OneButtonDialog.DIALOG_TYPE.MESSAGE_ONLY)
                        .setMessage(getString(R.string.leave_family_dialog_message))
                        .setTitle(getString(R.string.leave_family_dialog_title, "Пятковские"))
                        .setCustomTitleStyle(R.style.dialog_title_style)
                        .build();
            }
        });
        TextView joinTheFamily = (TextView) findViewById(R.id.joinToFamily);
        joinTheFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilySettingActivity.this, DecoderActivity.class);
                startActivity(intent);
            }
        });
        TextView addToFamily = (TextView) findViewById(R.id.addToFamily);
        addToFamily.setOnClickListener(v->presenter.prepareInvitation());
    }

    public void toQRActivity(String password) {
        Intent intent = new Intent(FamilySettingActivity.this, GenerateQRActivity.class);
        intent.putExtra(DATA, password);
        startActivity(intent);
    }

    @Override
    protected void onBitmapLoaded(DraweredActivity.TAG tag, Bitmap bitmap) {

    }

    public void toMainActivity() {
        Intent intent = new Intent (FamilySettingActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.TAG_PURPOSE, PURPOSE.SPENDED.ordinal());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void invitationAccepted(Message responce) {
        runOnUiThread(()->{
            new OneButtonDialog(FamilySettingActivity.this, OneButtonDialog.DIALOG_TYPE.MESSAGE_ONLY)
                    .setMessage("Invitation accepted")
                    .setOkListener(v->presenter.relogin())
                    .build();
        });

    }
}