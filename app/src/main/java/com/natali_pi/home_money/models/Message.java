package com.natali_pi.home_money.models;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Natali-Pi on 13.12.2017.
 */

public class Message {
    private static final String FAILURE = "failure";
    private static final String SUCCESS = "success";
String result;

    public String getResult() {
        return result;
    }

    public Message(String result) {
        this.result = result;
    }
    public Message(Bitmap photo) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String photoBase64 = null;

        if (photo != null) {

            photo.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            photoBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        this.result = photoBase64;
    }
    public boolean isSuccess(){
        return result!= null && result.equals(SUCCESS);
    }
    public boolean isFailure(){
        return result== null || result.equals(FAILURE);
    }
}

