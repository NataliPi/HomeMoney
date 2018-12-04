package com.natali_pi.home_money.spended;

import android.graphics.Bitmap;

import com.natali_pi.home_money.BasePresenter;
import com.natali_pi.home_money.DraweredActivity;
import com.natali_pi.home_money.main.MainActivity;
import com.natali_pi.home_money.models.Message;
import com.natali_pi.home_money.utils.Api;
import com.natali_pi.home_money.utils.BaseAPI;
import com.natali_pi.home_money.utils.DataBase;

/**
 * Created by Natali-Pi on 22.11.2017.
 */

public class SpendedPresenter extends BasePresenter<SpendedActivity> {

    private BaseAPI api;

    public SpendedPresenter(SpendedActivity activity) {
        setView(activity);
        api = new Api();
    }

    public void uploadPicture(DraweredActivity.TAG tag, Bitmap bitmap) {
        String id = "";
        if (tag == DraweredActivity.TAG.AVATAR) {
            id = DataBase.getInstance().getHuman().getId();
        }
        api.uploadPicture(new Message(bitmap), id)
                .subscribe(getObserver(true, (response) -> {
                    if (!response.isFailure()) {
                        DataBase.getInstance().getHuman().setPhoto(response.getResult());
                    }
                }));
    }

}
