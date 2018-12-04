package com.natali_pi.home_money.family_settings;

import com.natali_pi.home_money.BasePresenter;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;

/**
 * Created by Konstantyn Zakharchenko on 28.12.2017.
 */

public class SettingsPresenter  extends BasePresenter<FamilySettingActivity> {
    private BaseAPI api = new Api();
    private static SettingsPresenter instance = new SettingsPresenter();
    private SettingsPresenter(){}
    public static SettingsPresenter getInstance() {
        return instance;
    }

    public void prepareInvitation() {
        api.prepareInvitation(DataBase.getInstance().getFamily().getId()).subscribe(getObserver(true, (responce)->{
            getView().toQRActivity(responce.getResult());//TODO: Handle result failure
        }));
    }

    public void acceptInvitation(String data) {
        String[] parts = data.split("::");
        api.acceptInvitation(parts[0], DataBase.getInstance().getHuman().getId(), parts[1]).subscribe(getObserver(true, (responce)->{
            getView().invitationAccepted(responce);
        }));
    }
public void relogin(){
    api.login(DataBase.getInstance().getHuman()).subscribe(getObserver(true, (loginResponce)->{
        //TODO: Add message show on error
        DataBase.getInstance().login(loginResponce);
        getView().toMainActivity();
    }));
}
    public interface Callback{
        void onResult(String result);
    }
}
