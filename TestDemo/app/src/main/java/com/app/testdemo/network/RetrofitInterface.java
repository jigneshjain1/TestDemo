package com.app.testdemo.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.app.testdemo.interfaces.KeyInterface.BASE_URL;

final class RetrofitInterface {

    private RetrofitInterface() {
    }

    static Retrofit getJsonClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        int CONNECTION_TIME_OUT = 60;
        httpClient.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

}