package com.jobportal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.entities.TopRoles;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 08-09-2016.
 */
public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TopRoles> mArrLResult;
    private AdapterResponseInterface listener;


    public JobListAdapter(Context context, ArrayList<TopRoles> arrResult, AdapterResponseInterface listener) {
        super();
        this.mContext = context;
        this.mArrLResult = arrResult;
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_job_list, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int i) {
        holder.mTvJobTitle.setText(mArrLResult.get(i).getRole());
    }

    @Override
    public int getItemCount() {
        return mArrLResult.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvJobTitle, tv_role, tv_loaclity, tv_posted_by, tv_date, btn_apply;
        private ImageView iv_fav, iv_star;

        public CategoryViewHolder(View view) {
            super(view);
            mTvJobTitle = (TextView) view.findViewById(R.id.tv_job_title);
            tv_role = (TextView) view.findViewById(R.id.tv_role);
            tv_loaclity = (TextView) view.findViewById(R.id.tv_loaclity);
            tv_posted_by = (TextView) view.findViewById(R.id.tv_posted_by);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            iv_fav = (ImageView) view.findViewById(R.id.iv_fav);
            iv_star = (ImageView) view.findViewById(R.id.iv_star);
            btn_apply = (TextView) view.findViewById(R.id.btn_apply);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.ADAPTER_POSITION, getAdapterPosition());
                    bundle.putString(AppConstants.TYPE_CLICKED, AppConstants.TYPE_LAYOUT);
                    listener.getAdapterResponse(bundle);
                }
            });
            btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.ADAPTER_POSITION, getAdapterPosition());
                    bundle.putString(AppConstants.TYPE_CLICKED, AppConstants.TYPE_BUTTON);
                    listener.getAdapterResponse(bundle);
                }
            });
        }

    }
}

