package com.mw.matdagboken;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter
{
	private Context mContext;
	
	// references to our images should it be private or public?
    private Integer[] mThumbIds = {
            R.drawable.ic_camera_background, R.drawable.ic_camera_background,
            R.drawable.ic_camera_background, R.drawable.ic_camera_background,
            R.drawable.ic_camera_background, R.drawable.ic_camera_background
    };
	
	public ImageAdapter(Context c) 
	{
        mContext = c;
    }

	@Override
	public int getCount()
	{
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position)
	{
		 return mThumbIds[position];
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
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } 
        else 
        {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
	}
}
