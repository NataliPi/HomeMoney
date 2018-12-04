package com.natali_pi.home_money.registration;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.natali_pi.home_money.BaseActivity;
import com.natali_pi.home_money.BasePresenter;
import com.natali_pi.home_money.R;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Family;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.models.LoginData;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;

import java.util.List;

import io.reactivex.Observer;

public class RegistrationPresenter extends BasePresenter<RegistrationActivity>{
    private BaseAPI api;


    public RegistrationPresenter(RegistrationActivity activity) {
        setView(activity);
        api = new Api();
    }
    public void register(Human human){
        api.register(human).subscribe(getObserver(true, (message)->{


           if(!message.getResult().equals("failure") ){//TODO: change to constant
            api.login(human).subscribe(getObserver(true, (data)->{
                DataBase.getInstance().login(data);
                createAccount(data);
                getView().toMainActivity();
            }));


            } else {
               getView().showMessage("Sorry something went wrong");
            }
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
