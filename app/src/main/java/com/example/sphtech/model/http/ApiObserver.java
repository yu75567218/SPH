package com.example.sphtech.model.http;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ApiObserver<T> implements Observer<T> {

    Context context;

    public ApiObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof ApiException) {
            ApiException exception = (ApiException)t;
            Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onComplete() {

    }
}
