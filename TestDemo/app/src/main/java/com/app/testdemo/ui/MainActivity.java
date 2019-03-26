package com.app.testdemo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.testdemo.R;
import com.app.testdemo.adapter.PostListRecyclerAdapter;
import com.app.testdemo.interfaces.OnItemSelected;
import com.app.testdemo.interfaces.OnLoadMoreListener;
import com.app.testdemo.model.ClsHitRequest;
import com.app.testdemo.model.ClsPostResponse;
import com.app.testdemo.network.OnApiCalledCompleted;
import com.app.testdemo.network.RetrofitApiCallMethods;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ProgressBar progressBar;
    private TextView txtDataNotFound;
    private RecyclerView recyclePostList;
    private SwipeRefreshLayout pullToRefresh;

    private ArrayList<ClsHitRequest> hitRequestArrayList = new ArrayList<>();
    private PostListRecyclerAdapter postListRecyclerAdapter;

    private int pageNumber = 0;
    private boolean isLoadMore;
    private boolean isComeFromPUll = false;
    private int count = 0;

    private boolean isBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    /*Initialize or find view from Ids*/
    private void initViews() {
        recyclePostList = findViewById(R.id.recyclePostList);
        progressBar = findViewById(R.id.progressBar);
        txtDataNotFound = findViewById(R.id.txtDataNotFound);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        //Set RecyclerView Layout Manager
        recyclePostList.setLayoutManager(new LinearLayoutManager(this));
        pullToRefresh.setOnRefreshListener(this);

        isComeFromPUll = false;
        callPostWSForGetHits();
    }

    private void callPostWSForGetHits() {
        if (hitRequestArrayList.size() == 0)
            progressBar.setVisibility(View.VISIBLE);
        RetrofitApiCallMethods retrofitApiCallMethods = new RetrofitApiCallMethods(this);
        retrofitApiCallMethods.getPostsList(pageNumber, new OnApiCalledCompleted() {
            @Override
            public void onApiCallingCompleted(boolean isSuccess, Object objects) {
                progressBar.setVisibility(View.GONE);
                if (isSuccess) {
                    handleSuccessResponse((ClsPostResponse) objects);
                } else {
                    handleFailureResponse();
                }
                if (isComeFromPUll) {
                    isComeFromPUll = false;
                    pullToRefresh.setRefreshing(false);
                    setTitle(getResources().getString(R.string.app_name));
                }
            }
        });
    }

    private void handleSuccessResponse(ClsPostResponse modelPost) {
        if (hitRequestArrayList.size() != 0) {
            hitRequestArrayList.remove(hitRequestArrayList.size() - 1);
            postListRecyclerAdapter.notifyItemRemoved(hitRequestArrayList.size());
        }
        ArrayList<ClsHitRequest> hitDataList = (ArrayList<ClsHitRequest>) modelPost.getHitList();
        if (isLoadMore) {
            if (hitDataList.size() >= modelPost.getHitsPerPage()) {
                postListRecyclerAdapter.setLoaded();
                isLoadMore = false;
            }
        }
        hitRequestArrayList.addAll(hitDataList);
        setData();
    }


    private void handleFailureResponse() {
        if (hitRequestArrayList.size() != 0) {
            isLoadMore = false;
            pageNumber = pageNumber - 1;
            hitRequestArrayList.remove(hitRequestArrayList.size() - 1);
            postListRecyclerAdapter.notifyItemRemoved(hitRequestArrayList.size());
            postListRecyclerAdapter.setLoaded();
        } else {
            recyclePostList.setVisibility(View.GONE);
            txtDataNotFound.setVisibility(View.VISIBLE);
            txtDataNotFound.setText(getResources().getString(R.string.msg_network_error));
        }
    }


    private void setData() {
        if (hitRequestArrayList.size() > 0) {
            txtDataNotFound.setVisibility(View.GONE);
            recyclePostList.setVisibility(View.VISIBLE);
            if (postListRecyclerAdapter == null) {
                postListRecyclerAdapter = new PostListRecyclerAdapter(this, hitRequestArrayList);
                recyclePostList.setAdapter(postListRecyclerAdapter);
                postListRecyclerAdapter.setRecyclerViewScrollListener(recyclePostList);
                postListRecyclerAdapter.setOnLoadMoreListener(new LoadMoreListener());
            } else {
                postListRecyclerAdapter.setRecyclerViewScrollListener(recyclePostList);
                postListRecyclerAdapter.setProductArrayList(hitRequestArrayList);
                postListRecyclerAdapter.notifyDataSetChanged();
            }
            postListRecyclerAdapter.setItemSelected(new OnItemSelected() {
                @Override
                public void onItemSelectedDone() {
                    if (getSelectedNoOfCount() == 0)
                        setTitle(getResources().getString(R.string.app_name));
                    else
                        setTitle(getResources().getString(R.string.selected_post, getSelectedNoOfCount()));
                }
            });
        } else {
            // No post data available.
            recyclePostList.setVisibility(View.GONE);
            txtDataNotFound.setVisibility(View.VISIBLE);
            txtDataNotFound.setText(getResources().getString(R.string.msg_network_error));
        }
    }

    private int getSelectedNoOfCount() {
        count = 0;
        for (ClsHitRequest hit : postListRecyclerAdapter.getClsHitRequestArrayList()) {
            if (hit != null && hit.isSelected()) {
                count++;
            }
        }
        return count;
    }


    /*Swipe to refresh*/
    @Override
    public void onRefresh() {
        pageNumber = 0;
        hitRequestArrayList = new ArrayList<>();
        isComeFromPUll = true;
        callPostWSForGetHits();
    }

    private class LoadMoreListener implements OnLoadMoreListener {
        @Override
        public void onLoadMore() {
            if (hitRequestArrayList.size() != 0) {
                isLoadMore = true;
                pageNumber = pageNumber + 1;
                hitRequestArrayList.add(null);
                postListRecyclerAdapter.notifyItemInserted(hitRequestArrayList.size() - 1);
                isComeFromPUll = false;
                callPostWSForGetHits();
            } else {
                postListRecyclerAdapter.setLoaded();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (isBackPressed) {
            super.onBackPressed();
            return;
        }
        Toast.makeText(this, R.string.double_exit_message, Toast.LENGTH_SHORT).show();
        isBackPressed = true;
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        };
        handler.postDelayed(runnable, 1500);

    }
}
