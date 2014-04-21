package com.mw.matdagboken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class CameraActivity extends Activity
{
	private LinearLayout mLayout;
	private ImageView mImageView;
	private static final int REQUEST_CODE_CAMERA_PHOTO = 0x40a46757; //generated from http://www.guidgen.com/
	private File mImageFile = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int size = Math.min(metrics.heightPixels, metrics.widthPixels);
		
		mImageView = new ImageView(this);
		mImageView.setImageResource(R.drawable.ic_camera_background);
		mImageView.setScaleType(ScaleType.CENTER_INSIDE);
		mImageView.setLayoutParams(new LayoutParams(size, size));
		mImageView.requestLayout();

		mLayout = new LinearLayout(this);
		mLayout.setBackgroundColor(0xFFFFFFFF);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		mLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		mLayout.addView(mImageView);

		setContentView(mLayout);
		
		tryStartCameraActivity();
	}
	
	private File tryCreateImageFile()
	{
		if (!FileHelper.isExternalStorageWritable())
			return null;
		
	    // Create an image file name
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
	    String timeStamp = dateFormat.format(new Date());
	    String imageFileName = "MD_" + timeStamp;
	    File storageDir = FileHelper.getCameraStoragePath();
	    
	    if (storageDir.mkdirs() || storageDir.exists())
	    {
		    try
	        {
		    	File image = new File(storageDir, imageFileName);
		    	image.createNewFile();
		    	return image;
	        } 
	        catch (IOException ex) 
	        {
	            //TODO make dialogue saying there is not enough space left or whatever fails...
	        	ex.printStackTrace();
	        }
	    }
	    
	    return null;
	}
	
	private static Bitmap decodeFileToFitImageView(ImageView imageView, File imageFile)
	{
	    // Get the dimensions of the View
		//TODO this does not work
	    int targetW = 512;//imageView.getWidth();
	    int targetH = 512;//imageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
	    return bitmap;
	}
	
	private void tryStartCameraActivity()
	{
		PackageManager pm = getPackageManager();
		if (pm != null)
		{
			if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA))
			{
				Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				ComponentName cameraActivity = cameraIntent.resolveActivity(pm);
			    if (cameraActivity != null) 
			    {
			    	//note this function may return null if it fails
			    	mImageFile = tryCreateImageFile();
			   
			        // Continue only if the File was successfully created
			        if (mImageFile != null) 
			        {
			        	cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mImageFile));
			        	startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA_PHOTO);
			        }
			    }
			}
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (requestCode == REQUEST_CODE_CAMERA_PHOTO && resultCode == Activity.RESULT_OK)
	    {
	    	if (mImageFile != null && mImageFile.exists())
	    	{
	    		Bitmap bitmap = CameraActivity.decodeFileToFitImageView(mImageView, mImageFile);
	    		mImageView.setImageBitmap(bitmap);
	    	}
	    }
	}
}
