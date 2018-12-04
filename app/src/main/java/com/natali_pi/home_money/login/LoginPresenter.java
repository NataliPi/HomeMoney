package com.natali_pi.home_money.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.BasePresenter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.models.LoginData;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;
import com.natali_pi.home_money.utils.OneButtonDialog;

public class LoginPresenter extends BasePresenter<LoginActivity> {

    private BaseAPI api;



    public LoginPresenter(LoginActivity activity) {
        setView(activity);
        api = new Api();
    }
public void forgotPassword(String email){
        //TODO: Add request
}

public void login(Human human){
    api.login(human).subscribe(getObserver(true, (data)->{
        //TODO: Add message show on error
        DataBase.getInstance().login(data);
        createAccount(data);
        getView().toMainActivity();
    }));
}
    public void createAccount(LoginData data){
        AccountManager accountManager = AccountManager.get(getView()); //this is Activity
        Account account = new Account(data.getHuman().getEmail(),"com.company.demo.account.DEMOACCOUNT");
        boolean success = accountManager.addAccountExplicitly(account,data.getHuman().getPassword(),null);
        if(success){
            Log.d("TAG","Account created");
        }else{
            Log.d("TAG","Account creation failed. Look at previous logs to investigate");
        }

    }

}
