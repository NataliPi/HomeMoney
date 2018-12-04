package com.natali_pi.home_money.utils.auth;


import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Konstantyn Zakharchenko on 13.01.2018.
 */

public class Service extends android.app.Service {
    @Override
    public IBinder onBind(Intent intent) {
        Authenticator authenticator = new Authenticator(this);
        return authenticator.getIBinder();
    }
}
