package com.jobportal.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * This is used to load images using picaso, glide
 */

public class ImageLoader {

    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
    }

    public void displayImageWithGlide(ImageView imageView, String imagePath, int placeholderId, int errorId,Context _context) {
        if (context == null) {
            context = _context;
        }
        if (errorId != -1) {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(placeholderId)
                    .error(errorId)
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(placeholderId)
                    .into(imageView);
        }
    }

    public void displayImageWithGlideAsBitmap(ImageView imageView, String imagePath, int placeholderId, int errorId,Context _context) {
        if (context == null) {
            context = _context;
        }
        if (errorId != -1) {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(placeholderId)
                    .error(errorId)
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(placeholderId)
                    .into(imageView);
        }
    }

    public void displayImageWithPicasso(ImageView imageView, String imagePath, int placeholderId, int errorId,Context _context) {
        if (context == null) {
            context = _context;
        }
        if (StringUtils.isEmpty(imagePath)) {
            imagePath = "R.drawable.skip_pager_img1";
        }
        if (errorId != -1) {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(placeholderId)
                    .error(errorId)
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(placeholderId)
                    .into(imageView);
        }
    }
}
