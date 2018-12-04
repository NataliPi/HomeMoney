package com.natali_pi.home_money.utils;

import android.app.Application;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.natali_pi.home_money.utils.BaseAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Natali-Pi on 13.12.2017.
 */

public class App extends Application {
public static final String BASE_URL = "http://78.26.206.78/FamilyBudgetRest/";
public static final String PICTURE_URL = "rest/picture/";
    private static BaseAPI api;

    @Override
    public void onCreate() {
        super.onCreate();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Базовая часть адреса
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
     .client(new OkHttpClient.Builder()
             .connectTimeout(10, TimeUnit.SECONDS)
             .writeTimeout(10, TimeUnit.SECONDS)
             .readTimeout(10, TimeUnit.SECONDS)
             .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
             .build())
                .build();
        api = retrofit.create(BaseAPI.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static BaseAPI getBaseApi() {
        return api;
    }
}
