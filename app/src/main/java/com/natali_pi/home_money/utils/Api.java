package com.natali_pi.home_money.utils;

import com.natali_pi.home_money.models.Family;
import com.natali_pi.home_money.models.Human;
import com.natali_pi.home_money.models.LoginData;
import com.natali_pi.home_money.models.Message;
import com.natali_pi.home_money.models.Spending;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Natali-Pi on 13.12.2017.
 */

public class Api implements BaseAPI {
    private static BaseAPI api;
    public Api(){
        if (api == null){
            api = App.getBaseApi();
        }
    }
    @Override
    public Observable<List<Family>> test() {
        return api.test().compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> register(Human human) {
        return api.register(human).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<LoginData> login(Human human) {
        return api.login(human).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> addCategory(String familyId, String categoryId, String name) {
        return api.addCategory(familyId,categoryId,name).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> updateCategory(Message message, String familyId, String categoryId, String name) {
        return api.updateCategory(message, familyId,categoryId,name).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> hideCategory(String familyId, String categoryId) {
        return api.hideCategory(familyId,categoryId).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> setSpending(PURPOSE purpose, Spending spending) {
        return api.setSpending(purpose, spending).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> uploadPicture(Message message, String id) {
        return api.uploadPicture(message, id).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> uploadPicture(Message message, String familyId, String id) {
        return api.uploadPicture(message, familyId, id).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> updateProfile(Human data) {
        return api.updateProfile(data).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> prepareInvitation(String familyId) {
        return api.prepareInvitation(familyId).compose(new AsyncTransformer<>());
    }

    @Override
    public Observable<Message> acceptInvitation(String familyId, String humanId, String password) {
        return api.acceptInvitation(familyId, humanId, password).compose(new AsyncTransformer<>());
    }


}
