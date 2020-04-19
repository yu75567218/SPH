package com.example.sphtech.model.http;

import com.example.sphtech.model.bean.BeanQuarterTotal;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/action/datastore_search")
    Observable<BeanQuarterTotal> search(@Query("resource_id") String resource_id, @Query("limit") int limit, @Query("offset") int offset);

}