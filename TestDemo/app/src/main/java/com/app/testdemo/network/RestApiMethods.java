package com.app.testdemo.network;

import com.app.testdemo.model.ClsPostResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import static com.app.testdemo.interfaces.KeyInterface.PRODUCT_LIST_URL;

public interface RestApiMethods
{
    @Headers({"Content-Type: application/json",})

    @GET(PRODUCT_LIST_URL)
    Call<ClsPostResponse> getPostList(@Query("tags") String tag, @Query("page") int page);

}