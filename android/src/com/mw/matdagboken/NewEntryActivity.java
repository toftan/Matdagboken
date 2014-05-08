package com.mw.matdagboken;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewEntryActivity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener, DatePickerDialog.OnDateSetListener, OnTimeSetListener
{
//	private Date mDate;
	private Calendar mCalendar = null;
	private static final int REQUEST_CODE_CAMERA_PHOTO = 1000;
	private static final int REQUEST_CODE_SELECT_PHOTO = 1001;
	private File mImageFile = null;
	private Entry mEntry = null;
	private Button mSaveButton = null;
	private Button mCancelButton = null;
	private ImageButton mCalendarButton = null;
	private EditText mTimeEntry = null;
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
		
		mCancelButton = (Button) findViewById(R.id.cancelButton);
		mCancelButton.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.foodImage);
		mImageView.setOnClickListener(this);
		
		mCalendarButton = (ImageButton) findViewById(R.id.calendarButton);
		mCalendarButton.setOnClickListener(this);
		
		mTimeEntry = (EditText) findViewById(R.id.timeEntry);
		mTimeEntry.setOnClickListener(this);
		
		ActionBar actionBar = getActionBar();
		actionBar.show();

		
		//mDate = new Date();
		mCalendar = Calendar.getInstance();
		updateDateLabel();
		updateTimeLabel();
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
	    String dateStamp = dateFormat.format(mDate); 
	    EditText dateEntry = (EditText) findViewById(R.id.dateEntry);
	    dateEntry.setText(dateStamp);*/
	    
	    SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
	    String dayStamp = dayFormat.format(mCalendar.getTime()); 
	    TextView dayEntry = (TextView) findViewById(R.id.dayTitle);
	    dayEntry.setText(dayStamp);
	    
	/*    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
	    String timeStamp = timeFormat.format(mCalendar.getTime());
	    EditText timeEntry = (EditText) findViewById(R.id.timeEntry);
	    timeEntry.setText(timeStamp);*/
	    
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
		Intent galleryIntent = new Intent(Intent.ACTION_PICK); // Only images form SDCARD
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
			mEntry.Date = mCalendar.getTime();
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
		else if(v == mCancelButton)
		{
			/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		    String dateStamp = dateFormat.format(mDate); 
		    EditText dateEntry = (EditText) findViewById(R.id.dateEntry);
		    dateEntry.setText(dateStamp);*/
			mCalendar = Calendar.getInstance();
			updateDateLabel();
			updateTimeLabel();
		    
		 /*   SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
		    String timeStamp = timeFormat.format(mCalendar.getTime());
		    EditText timeEntry = (EditText) findViewById(R.id.timeEntry);
		    timeEntry.setText(timeStamp);*/
		    
		    //Reset spinner option
		    ((Spinner) findViewById(R.id.mealTypeEntry)).setSelection(0);;
		    
		    ((ImageView) findViewById(R.id.foodImage)).setImageResource(R.drawable.ic_camera_background); //Set mImageBitmap instead as in onCreate()?
		  
			((EditText) findViewById(R.id.beverageEntry)).setText("");
			((EditText) findViewById(R.id.foodEntry)).setText("");
			((EditText) findViewById(R.id.howEntry)).setText("");
			((EditText) findViewById(R.id.moodEntry)).setText("");
			((EditText) findViewById(R.id.commentEntry)).setText("");
		}
		else if(v == mImageView)
		{			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.listView_title);
			builder.setItems(R.array.listView_items, this);
			AlertDialog alert = builder.create();
			alert.show();		
		}
		else if(v == mCalendarButton)
		{			
			DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                    mCalendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.setTitle(R.string.datePicker_title);
			(datePickerDialog.getDatePicker()).setCalendarViewShown(true); //Only available in API 11??
			(datePickerDialog.getDatePicker()).setSpinnersShown(false);
			datePickerDialog.show();

		/*	DatePicker datePicker = new DatePicker(NewEntryActivity.this);
			datePicker.setSpinnersShown(false);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.datePicker_title);
			builder.setView(datePicker);
			AlertDialog alert = builder.create();
			alert.show();		*/
		}
		else if(v == mTimeEntry)
		{		
			// Let user choose 24 hour view or AM/PM as future feature? 
			TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
			timePickerDialog.setTitle("Set time");
			timePickerDialog.show();	
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
	
	 private void updateDateLabel() 
	 {
		SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
		
		EditText dateEntry = (EditText) findViewById(R.id.dateEntry);
		dateEntry.setText(calendarDateFormat.format(mCalendar.getTime()));
	 }
	 
	 private void updateTimeLabel() 
	 {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
		String timeStamp = timeFormat.format(mCalendar.getTime());
		EditText timeEntry = (EditText) findViewById(R.id.timeEntry);
		timeEntry.setText(timeStamp);
	 }

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
	{
		mCalendar.set(Calendar.YEAR, year);
    	mCalendar.set(Calendar.MONTH, monthOfYear);
    	mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    	updateDateLabel();		
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute)
	{
    	mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
    	mCalendar.set(Calendar.MINUTE, minute);
    	updateTimeLabel();
	}

}