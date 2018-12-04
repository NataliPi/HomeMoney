package com.natali_pi.home_money.authoization;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.BasePresenter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.add_spending.SpendingPresenter;
import com.natali_pi.home_money.login.LoginActivity;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.models.LoginData;
import com.natali_pi.home_money.registration.RegistrationActivity;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;

public class AuthorisationPresenter extends BasePresenter<AuthorisationActivity> {
    private BaseAPI api = new Api();
    private static AuthorisationPresenter instance = new AuthorisationPresenter();

    public static AuthorisationPresenter getInstance() {
        return instance;
    }

    public Human getOldAccount(){

        AccountManager accountManager = AccountManager.get(getView());
        try {
            Account account = accountManager.getAccountsByType("com.company.demo.account.DEMOACCOUNT")[0];
            Human human = new Human();
            human.setEmail(account.name);
            human.setPassword(accountManager.getPassword(account));
            return human;
        }catch (IndexOutOfBoundsException iobe){
            return null;
        }
    }

    public void login(Human human){
        api.login(human).subscribe(getObserver(true, (data)->{
            //TODO: Add message show on error
            if(!data.getHuman().getSettings().isPasswordProtected()) {
                DataBase.getInstance().login(data);
                getView().toMainActivity();
            } else {
                getView().showAuthScreen();
            }
        }));
    }
}
