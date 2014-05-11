package com.mw.matdagboken;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    	LinearLayout layout;
        if (convertView == null)  // if it's not recycled, initialize some attributes
        {
        	layout = (LinearLayout) View.inflate(parent.getContext(), R.layout.galleryitem, null);
        } 
        else 
        {
        	layout = (LinearLayout) convertView;
        }
        
        ImageView imageView = (ImageView) layout.findViewById(R.id.gallery_image);
        if (imageView != null)
        {
        	File imagefile = (File) getItem(position);
            Bitmap bitmap = ImageHelper.decodeAndCropFileToFitSize(imagefile, 128);
            imageView.setImageBitmap(bitmap);
        }
        
        return layout;
    }
}