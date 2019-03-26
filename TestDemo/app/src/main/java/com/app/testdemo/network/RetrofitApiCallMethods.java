package com.app.testdemo.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.app.testdemo.R;
import com.app.testdemo.model.ClsPostResponse;
import com.app.testdemo.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app.testdemo.interfaces.KeyInterface.ITEM_TAG;

public class RetrofitApiCallMethods {
    private Context mContext;

    public RetrofitApiCallMethods(Context mContext) {
        this.mContext = mContext;
    }

    public void getPostsList(final int page, final OnApiCalledCompleted onApiCalledCompleted) {
        if (Utility.isInternetAvailable(mContext)) {
            RestApiMethods apiInterface = RetrofitInterface.getJsonClient().create(RestApiMethods.class);
            Call<ClsPostResponse> call = apiInterface.getPostList(ITEM_TAG, page);
            call.enqueue(new Callback<ClsPostResponse>() {
                @Override
                public void onResponse(@NonNull Call<ClsPostResponse> call, @NonNull Response<ClsPostResponse> response) {
                    onApiCalledCompleted.onApiCallingCompleted(true, response.body());
                }

                @Override
                public void onFailure(@NonNull Call<ClsPostResponse> call, @NonNull final Throwable t) {
                    onApiCalledCompleted.onApiCallingCompleted(false, t.getLocalizedMessage());
                }
            });
        } else {
            onApiCalledCompleted.onApiCallingCompleted(false, mContext.getString(R.string.msg_network_error));
        }
    }

}