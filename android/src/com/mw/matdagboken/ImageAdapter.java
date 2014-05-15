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
    ArrayList<ImageHelper.EntryFile> mEntryFiles = null;
    
    public ImageAdapter(Context c) 
    {
        mContext = c;
        mEntryFiles = ImageHelper.getEntryFiles();
    }

    @Override
    public int getCount()
    {
        return mEntryFiles.size();
    }

    @Override
    public Object getItem(int position)
    {
         return mEntryFiles.get(position);
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
        	ImageHelper.EntryFile imagefile = (ImageHelper.EntryFile) getItem(position);
            Bitmap bitmap = ImageHelper.decodeAndCropFileToFitSize(imagefile.mPngFile, 128);
            imageView.setImageBitmap(bitmap);
            imageView.setTag(imagefile);
        }
        
        return layout;
    }
}