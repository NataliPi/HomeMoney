package com.natali_pi.home_money.settings;

import android.graphics.Bitmap;

import com.natali_pi.home_money.BasePresenter;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.models.Message;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;

/**
 * Created by Konstantyn Zakharchenko on 23.01.2018.
 */

public class SettingsPresenter extends BasePresenter<SettingActivity>{

    private BaseAPI api = new Api();

    private static final SettingsPresenter ourInstance = new SettingsPresenter();

    public static SettingsPresenter getInstance() {
        return ourInstance;
    }

    private Bitmap avatar;

    private SettingsPresenter() {
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }
    public void updateProfile(Human data){
        if(avatar != null){
            api.uploadPicture(new Message(avatar), data.getId()).subscribe(getObserver(true, (response->{
                if(!response.isFailure()) {
                    data.setPhoto(response.getResult());
                    saveProfile(data);
                }
            })));
        } else {
            saveProfile(data);
        }


    }
    public void saveProfile(Human data){
        api.updateProfile(data).subscribe(getObserver(true, (response->{
                if(response.isSuccess()){
                    DataBase.getInstance().setHuman(data);
                    getView().finish();
                    //TODO: handle results
                }
        })));
    }
}

