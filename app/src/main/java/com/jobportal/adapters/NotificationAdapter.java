package com.jobportal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PravinK on 06-06-2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mArrLResult;
    private AdapterResponseInterface listener;


    public NotificationAdapter(Context context, ArrayList<String> arrResult, AdapterResponseInterface listener) {
        super();
        this.mContext = context;
        this.mArrLResult = arrResult;
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public NotificationAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_notification, null);
        return new NotificationAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.CategoryViewHolder holder, final int i) {
        // holder.mTvRoleName.setText(mArrLResult.get(i).getRole());
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        //   private TextView mTvRoleName;

        public CategoryViewHolder(View view) {
            super(view);
            //  mTvRoleName = (TextView) view.findViewById(R.id.tv_role);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(AppConstants.ADAPTER_POSITION, getAdapterPosition());
                    listener.getAdapterResponse(bundle);
                }
            });
        }

    }
}