package com.jobportal.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jobportal.R;
import com.jobportal.constants.AppConstants;
import com.jobportal.listeners.AdapterResponseInterface;

import java.util.ArrayList;

/**
 * Created by PriyankaJ on 08-09-2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CategoryViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> mArrLResult;
    private AdapterResponseInterface listener;


    public CityAdapter(Context context, ArrayList<String> arrResult, AdapterResponseInterface listener) {
        super();
        this.mContext = context;
        this.mArrLResult = arrResult;
        mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_city, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int i) {
        holder.mTvCity.setText(mArrLResult.get(i));
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
        private TextView mTvCity;

        public CategoryViewHolder(View view) {
            super(view);
            mTvCity = (TextView) view.findViewById(R.id.tv_city);

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

