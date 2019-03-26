package com.app.testdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.testdemo.R;
import com.app.testdemo.interfaces.OnItemSelected;
import com.app.testdemo.interfaces.OnLoadMoreListener;
import com.app.testdemo.model.ClsHitRequest;
import com.app.testdemo.utility.Utility;

import java.util.ArrayList;

import static com.app.testdemo.interfaces.KeyInterface.LOCAL_DATE_FORMAT;
import static com.app.testdemo.interfaces.KeyInterface.SERVER_DATE_FORMAT;


public class PostListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ClsHitRequest> clsHitRequestArrayList;

    private final int ITEM = 0;
    private final int LOADING = 1;

    private int visibleThreshold = 1;
    private boolean isLoading = false;
    private int lastVisibleItem, totalItemCount;

    private OnItemSelected itemSelected;
    private OnLoadMoreListener onLoadMoreListener;

    public PostListRecyclerAdapter(Context context, ArrayList<ClsHitRequest> hitArrayList) {
        this.context = context;
        this.clsHitRequestArrayList = hitArrayList;
    }

    /**
     * Handle load more of recyclerview.
     */
    public void setRecyclerViewScrollListener(RecyclerView recyclerView) {
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (totalItemCount < 5) {
                    return;
                }
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    isLoading = true;
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_load_more, parent, false);
            return new LoadingViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_posts, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return clsHitRequestArrayList.get(position) == null ? LOADING : ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PostViewHolder) {
            ClsHitRequest clsHitRequest = clsHitRequestArrayList.get(position);
            ((PostViewHolder) holder).txtPostTitle.setText(clsHitRequest.getTitle());
            ((PostViewHolder) holder).txtPostCreatedAt.setText(context.getString(R.string.created_at) + " : " + Utility.convertDateFormat(clsHitRequest.getCreatedAt(), SERVER_DATE_FORMAT, LOCAL_DATE_FORMAT));
            ((PostViewHolder) holder).chkPostSelect.setChecked(clsHitRequest.isSelected());
            ((PostViewHolder) holder).constraintRoot.setSelected(clsHitRequest.isSelected());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressLoadMore.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return clsHitRequestArrayList.size();
    }

    public void setProductArrayList(ArrayList<ClsHitRequest> productArrayList) {
        this.clsHitRequestArrayList = productArrayList;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPostTitle;
        private TextView txtPostCreatedAt;
        private CheckBox chkPostSelect;
        private ConstraintLayout constraintRoot;


        PostViewHolder(View itemView) {
            super(itemView);
            txtPostTitle = itemView.findViewById(R.id.txtPostTitle);
            txtPostCreatedAt = itemView.findViewById(R.id.txtPostCreatedAt);
            chkPostSelect = itemView.findViewById(R.id.chkPostSelect);
            constraintRoot = itemView.findViewById(R.id.constraintRoot);

            chkPostSelect.setClickable(false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clsHitRequestArrayList.get(getAdapterPosition()).setSelected(!clsHitRequestArrayList.get(getAdapterPosition()).isSelected());
                    notifyDataSetChanged();
                    if (itemSelected != null) {
                        itemSelected.onItemSelectedDone();
                    }
                }
            });
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressLoadMore;

        LoadingViewHolder(View view) {
            super(view);
            progressLoadMore = view.findViewById(R.id.loadMoreProgress);
        }
    }

    public void setItemSelected(OnItemSelected itemSelected) {
        this.itemSelected = itemSelected;
    }

    public ArrayList<ClsHitRequest> getClsHitRequestArrayList() {
        return clsHitRequestArrayList;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }
}