package com.natali_pi.home_money.authoization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.login.LoginActivity;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.registration.RegistrationActivity;
import com.natali_pi.home_money.utils.OneButtonDialog;
import com.natali_pi.home_money.utils.PURPOSE;

public class AuthorisationActivity extends BaseActivity {
AuthorisationPresenter presenter = AuthorisationPresenter.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.setView(this);
        Human human = presenter.getOldAccount();
        if(human == null) {
            showAuthScreen();
        } else {
            setContentView(R.layout.empty);
            presenter.login(human);
        }
    }
public void showAuthScreen(){
    setContentView(R.layout.activity_authorisation);

    Button login = (Button) findViewById(R.id.login);
    Button registration = (Button) findViewById(R.id.registration);
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AuthorisationActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    });
    registration.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AuthorisationActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }
    });
}
    public void toMainActivity() {
        Intent intent = new Intent (AuthorisationActivity.this, MainActivity.class);
        intent.putExtra(MainActivity.TAG_PURPOSE, PURPOSE.SPENDED.ordinal());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
