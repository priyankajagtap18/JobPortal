package com.jobportal.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.jobportal.R;
import com.jobportal.utils.ImageLoader;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    private ArrayList<Integer> listImages;
    private Context mContext;

    public CustomPagerAdapter(Context context, ArrayList<Integer> listImages) {
        mContext = context;
        this.listImages = listImages;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.layout_image_pager, collection, false);
        ImageView imageView = (ImageView) layout.findViewById(R.id.iv_pager_cover);
        new ImageLoader(mContext).displayImageWithGlideAsBitmap(imageView, "" + listImages.get(position), listImages.get(position), -1,mContext);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}