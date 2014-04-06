package com.mw.matdagboken;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class CameraLayout 
{
	private LinearLayout mLayout;
	private ImageView mImageView;
	public static final int REQUEST_CODE_CAMERA_PHOTO = 0x40a46757; //generated from http://www.guidgen.com/
	
	CameraLayout(Context context) 
	{
		mLayout = new LinearLayout(context);
		mLayout.setBackgroundColor(0xFFFFFFFF);
		mImageView = new ImageView(context);  
		mImageView.setBackgroundColor(0xFF00FFFF);
		mImageView.setScaleType(ScaleType.CENTER_INSIDE);
		mLayout.addView(mImageView);
	}
	
	public View getContentView()
	{
		return mLayout;
	}
	
	public void tryStartCameraActivity(Activity activity)
	{
		PackageManager pm = activity.getPackageManager();
		if (pm != null)
		{
			if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA))
			{
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				ComponentName cameraActivity = cameraIntent.resolveActivity(pm);
			    if (cameraActivity != null) 
			    {
			    	activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA_PHOTO);
			    }
			}
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (requestCode == REQUEST_CODE_CAMERA_PHOTO && resultCode == Activity.RESULT_OK)
	    {
	        Bundle extras = data.getExtras();
	        Bitmap imageBitmap = (Bitmap) extras.get("data");
			mImageView.setImageBitmap(imageBitmap);
	    }
	}
}
