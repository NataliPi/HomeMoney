package com.natali_pi.home_money.registration;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.PURPOSE;

public class RegistrationActivity extends BaseActivity {
    RegistrationPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RegistrationPresenter(this);
        setBaseContentView(R.layout.activity_registration);
        setupToolbar(R.drawable.arrow, "");
        setHighlightedText(getString(R.string.registration));
        TextView privacy = (TextView) findViewById(R.id.privacy);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacyPolicyLink)));
                startActivity(i);
            }
        });
        // privacy.setMovementMethod(LinkMovementMethod.getInstance());
        setNavigationButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button registration = (Button) findViewById(R.id.registration);
        EditText password = (EditText) findViewById(R.id.password);
        EditText repeatPassword = (EditText) findViewById(R.id.repeatPassword);
        EditText name = (EditText) findViewById(R.id.name);
        EditText surname = (EditText) findViewById(R.id.surname);
        EditText email = (EditText) findViewById(R.id.email);


        //* //TODO COMMENT THIS LINES
           name.setText("Константин");
        password.setText("philips2010");
        repeatPassword.setText("philips2010");
        email.setText("ZakharchenkoWork@gmail.com");
        //*/
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(repeatPassword.getText().toString())){
                    //TODO: Add all other checks
                    Human human = new Human();
                    human.setPassword(password.getText().toString());
                    human.setName(name.getText().toString());
                    human.setEmail(email.getText().toString());
                    human.setFamilyName(surname.getText().toString());
                    presenter.register(human);
                } else {
                    showMessage("Введенные пароли не совпадают");
                }

            }
        });
    }

    public void toMainActivity() {
    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.TAG_PURPOSE, PURPOSE.SPENDED.ordinal());
    startActivity(intent);
}

}
