package com.mw.matdagboken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class NewEntryActivity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener
{
	private Date mDate;
	private static final int REQUEST_CODE_CAMERA_PHOTO = 1000;
	private static final int REQUEST_CODE_SELECT_PHOTO = 1001;
	private File mImageFile = null;
	private Entry mEntry = null;
	private Button mSaveButton = null;
	private Bitmap mImageBitmap = null;
	private ImageView mImageView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newentryactivity);
		
		mEntry = new Entry();
		mSaveButton = (Button) findViewById(R.id.saveButton);
		mSaveButton.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.foodImage);
		mImageView.setOnClickListener(this);
		
		mDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
	    String dateStamp = dateFormat.format(mDate); 
	    EditText dateEntry = (EditText) findViewById(R.id.dateEntry);
	    dateEntry.setText(dateStamp);
	    
	    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
	    String dayStamp = dayFormat.format(mDate); 
	    TextView dayEntry = (TextView) findViewById(R.id.dayTitle);
	    dayEntry.setText(dayStamp);
	    
	    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
	    String timeStamp = timeFormat.format(mDate);
	    EditText timeEntry = (EditText) findViewById(R.id.timeEntry);
	    timeEntry.setText(timeStamp);
	    
		mImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera_background);
	    
	}
	
	private static File tryCreateCameraImageFile()
	{
		if (!FileHelper.isExternalStorageWritable())
			return null;
		
	    // Create an image file name
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
	    String timeStamp = dateFormat.format(new Date());
	    String imageFileName = timeStamp + ".jpg";
	    File storageDir = new File(FileHelper.getCameraStoragePath().getAbsoluteFile() + "/Camera/" );
	    
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
	
	private static Bitmap decodeAndCropFileToFitSize(File imageFile, int targetSize)
	{
	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;
	    int minPhotoSize = Math.min(photoW, photoH);
	    
	    //This indicates that we failed to decode the image and can not proceed
	    if (minPhotoSize <= 0)
	    	return null;
	    
	    //Try to get target size
	    targetSize = Math.min(targetSize, minPhotoSize);

	    // Determine how much to scale down the image
	    int scaleFactor = Math.max(1, Math.min(photoW/targetSize, photoH/targetSize));

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;
	    
	    //This will decode the bitmap into memory
	    Bitmap fullBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), bmOptions);
	    
	    photoW = bmOptions.outWidth;
	    photoH = bmOptions.outHeight;
	    minPhotoSize = Math.min(photoW, photoH);
	    if (minPhotoSize <= 0)
	    	return null;
	    
	    //Create the target bitmap
	    Bitmap croppedBitmap = Bitmap.createBitmap(targetSize, targetSize, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(croppedBitmap);
	    
	    //Paint the loaded image into the new bitmap which later on will be saved
	    int size = Math.min(photoW, photoH);
	    int x = (photoW - size) / 2;
	    int y = (photoH - size) / 2;
	    Rect srcRect = new Rect(x, y, x + size, y + size);
	    Rect dstRect = new Rect(0, 0, targetSize, targetSize);
	    canvas.drawBitmap(fullBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));

	    fullBitmap.recycle();
	    return croppedBitmap;
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
			    	mImageFile = tryCreateCameraImageFile();
			   
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
	
	private void tryPickPhotoFromGallery()
	{
		Intent galleryIntent = new Intent(Intent.ACTION_PICK); // ONly images form SDCARD
		galleryIntent.setType("image/*");
		startActivityForResult(galleryIntent, REQUEST_CODE_SELECT_PHOTO);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode != Activity.RESULT_OK)
		{
			return;
		}
		
	    if (requestCode == REQUEST_CODE_CAMERA_PHOTO)
	    {
	    	if (mImageFile != null && mImageFile.exists())
	    	{
	    		ImageView image = (ImageView) findViewById(R.id.foodImage);
	    		mImageBitmap = decodeAndCropFileToFitSize(mImageFile, 512);
	    		image.setImageBitmap(mImageBitmap);
	    	}
	    }   
	    else if(requestCode == REQUEST_CODE_SELECT_PHOTO)
	    {
			Uri selectedImage = data.getData();
			String[] filePathColumn = {MediaStore.Images.Media.DATA};
			
			Cursor cursor = getContentResolver().query( selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String filePath = cursor.getString(columnIndex);
			cursor.close();
			
			File file = new File(filePath);
			ImageView image = (ImageView) findViewById(R.id.foodImage);
    		mImageBitmap = decodeAndCropFileToFitSize(file, 512);
    		image.setImageBitmap(mImageBitmap);
	    }
	}
	
	//Add actions to action bar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.newentryactionbar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	//Respond to actions in the action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) 
	    {
	        case R.id.ac_backButton:
	            goBack();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void goBack()
	{
		
	}

	@Override
	public void onClick(View v)
	{
		if(v == mSaveButton)
		{
			mEntry.Date = mDate;
			Spinner spinner = (Spinner) findViewById(R.id.mealTypeEntry);
			mEntry.Meal = spinner.getSelectedItemPosition();
			EditText beverage = (EditText) findViewById(R.id.beverageEntry);
			mEntry.Beverage = beverage.getText().toString();
			EditText food = (EditText) findViewById(R.id.foodEntry);
			mEntry.Food = food.getText().toString();
			EditText how = (EditText) findViewById(R.id.howEntry);
			mEntry.How = how.getText().toString();
			EditText mood = (EditText) findViewById(R.id.moodEntry);
			mEntry.Mood = mood.getText().toString();
			EditText comment = (EditText) findViewById(R.id.commentEntry);
			mEntry.Comment = comment.getText().toString();
				
			EntrySerializer.trySaveEntry(mEntry, mImageBitmap, mEntry.Date); 
		}	
		else if(v == mImageView)
		{			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.listView_title);
			builder.setItems(R.array.listView_items, this);
			AlertDialog alert = builder.create();
			alert.show();		
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		if(which == 0)
		{
			tryStartCameraActivity();
		}
		else if(which == 1)
		{
			tryPickPhotoFromGallery();
		}		
	}
}
