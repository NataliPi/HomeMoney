package com.natali_pi.home_money;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Natali-Pi on 13.12.2017.
 */

public class BasePresenter<ViewType extends BaseActivity> {

    private ViewType activity;
    private List<Disposable> disposables = new ArrayList<>();

    public void setView(ViewType activity) {
        this.activity = activity;
    }

    public ViewType getView() {
        return activity;
    }

    protected <Type> Observer<Type> getObserver(boolean isShowLoading, OnNext<Type> action) {
        return new Observer<Type>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposables.add(d);
                if (isShowLoading) {
                    getView().onStartLoading();
                }
            }

            @Override
            public void onNext(Type t) {
                action.onNext(t);
                if (isShowLoading) {
                    getView().onFinishLoading();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isShowLoading) {
                    onCallError();
                }
                    Log.e("Presenter", e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.v("Presenter", "completed");
            }
        };
    }

    private void onCallError() {
        getView().onError();
    }
        public String getStringResourceByName(String name){
        return getView().getStringResourceByName(name);
    }
    public interface OnNext<Type> {
        void onNext(Type data);
    }
    public void onStop(){}
}
