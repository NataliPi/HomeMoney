package com.natali_pi.home_money.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Natali-Pi on 13.12.2017.
 */

public class AsyncTransformer<Type> implements ObservableTransformer<Type, Type> {
    @Override
    public ObservableSource<Type> apply(Observable<Type> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}