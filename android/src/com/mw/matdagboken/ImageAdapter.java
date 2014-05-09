package com.mw.matdagboken;

import java.io.File;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
    private Context mContext;
    ArrayList<File> mImageFiles;
    
    public ImageAdapter(Context c) 
    {
        mContext = c;
        mImageFiles = ImageHelper.getEntryImageFiles();
    }

    @Override
    public int getCount()
    {
        return mImageFiles.size();
    }

    @Override
    public Object getItem(int position)
    {
         return mImageFiles.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null)  // if it's not recycled, initialize some attributes
        { 
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } 
        else 
        {
            imageView = (ImageView) convertView;
        }

        File imagefile = (File) getItem(position);
        Bitmap image = ImageHelper.decodeAndCropFileToFitSize(imagefile, 128);
        imageView.setImageBitmap(image);
        return imageView;
    }
}