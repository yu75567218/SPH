package com.example.sphtech.model.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitUtils {

    private static Retrofit mRetrofit;

    public static void init(String baseUrl) {
        if (mRetrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (mRetrofit == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS);

                    builder.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Request requestBuilder = request.newBuilder()
                                    .addHeader("os", "android")
                                    .addHeader("Content-Type", "application/json;charset=utf-8")
                                    .build();
                            Log.e("test", new Gson().toJson(requestBuilder));
                            return chain.proceed(requestBuilder);
                        }
                    });

                    mRetrofit = new Retrofit.Builder()
                            .client(builder.build())
                            .addConverterFactory(ResponseConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(baseUrl)
                            .build();
                }
            }
        }
    }

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            throw new NullPointerException("must init RetrofitUtils");
        }

        return mRetrofit;
    }
}
