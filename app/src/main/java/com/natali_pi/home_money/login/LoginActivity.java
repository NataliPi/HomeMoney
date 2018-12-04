package com.natali_pi.home_money.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.authoization.AuthorisationActivity;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.registration.RegistrationActivity;
import com.natali_pi.home_money.utils.OneButtonDialog;
import com.natali_pi.home_money.utils.PURPOSE;

public class LoginActivity extends BaseActivity {
LoginPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenter(this);
        setBaseContentView(R.layout.activity_login);
        setupToolbar(R.drawable.arrow, "");
        setHighlightedText(getString(R.string.authorization));

        setNavigationButtonListener(getBackAction());

        TextView forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new OneButtonDialog(LoginActivity.this, OneButtonDialog.DIALOG_TYPE.MESSAGE_AND_INPUT)
                        .setTitle(getString(R.string.enter_email))
                        .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                        .setMessage(getString(R.string.forget_email_message))
                        .setEditTextHint(getString(R.string.email))
                        .setCustomTextStyle(R.style.standard_margin)
                        .setCustomTitleStyle(R.style.dialog_title_style)
        .setOkListener((email)->{presenter.forgotPassword(email);})
                        .build();
            }
        });
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);
        //*//TODO: delete this text
        email.setText("ZakharchenkoWork@gmail.com");
        password.setText("philips2010");
         //*/
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //TODO: ADD CHECKS
                Human human = new Human();
                human.setEmail(email.getText().toString());
                human.setPassword(password.getText().toString());
               presenter.login(human);
            }
        });
    }

    public void toMainActivity() {
        Intent intent = new Intent (LoginActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.TAG_PURPOSE, PURPOSE.SPENDED.ordinal());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
