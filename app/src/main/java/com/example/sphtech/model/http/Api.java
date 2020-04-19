package com.example.sphtech.model.http;

public class Api {

    private static ApiService apiService;

    public static ApiService getInstance() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    apiService = RetrofitUtils.getInstance().create(ApiService.class);
                }
            }
        }

        return apiService;
    }
}
