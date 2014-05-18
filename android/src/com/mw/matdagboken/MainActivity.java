package com.mw.matdagboken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends FoodshotActivity implements OnClickListener
{
	private LinearLayout mLayout;
	private Button mNewEntryButton;
	private Button mGalleryButton;
	private Entry mDiaryEntry;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); //Comment 2
		setContentView(R.layout.mainactivity);
				
		mNewEntryButton = (Button) findViewById(R.id.newEntryButton);
		mNewEntryButton.setOnClickListener(this);
		
		mGalleryButton = (Button) findViewById(R.id.galleryButton);
		mGalleryButton.setOnClickListener(this);
		
		ActionBar actionBar = getActionBar();
		actionBar.show();
		
		
	/*	mLayout = new LinearLayout(this);
		mLayout.setBackgroundColor(0xFFFFFFFF);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		
		mNewEntryButton = new Button(this);
		mNewEntryButton.setText(R.string.newEntry_button);
		mNewEntryButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mNewEntryButton.setOnClickListener(this); 
		
		mGalleryButton = new Button(this);
		mGalleryButton.setText(R.string.gallery_button);
		mGalleryButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mGalleryButton.setOnClickListener(this);

		mLayout.addView(mNewEntryButton);
		mLayout.addView(mGalleryButton);
		setContentView(mLayout); */
		
		mDiaryEntry = new Entry();	
	}
	
	private File tryCreateDiaryJSONFile(String timeStamp)
	{
		if (!FileHelper.isExternalStorageWritable())
			return null;
		
	    String imageFileName = "MD_" + timeStamp + ".json";
	    File storageDir = new File(FileHelper.getApplicationExternalStoragePath().getAbsolutePath() + "/Matdagboken/" + timeStamp);

	    if (storageDir.mkdirs() || storageDir.exists())
	    {
		    try
	        {
		    	File file = new File(storageDir, imageFileName);
		    	file.createNewFile();
		    	return file;
	        } 
	        catch (IOException ex) 
	        {
	            //TODO make dialogue saying there is not enough space left or whatever fails...
	        	ex.printStackTrace();
	        }
	    }
	    
	    return null;
	}
	
	private boolean trySaveDiaryEntry()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
	    String timeStamp = dateFormat.format(new Date());
		
	    File file = tryCreateDiaryJSONFile(timeStamp);
		if (file != null)
		{
			FileOutputStream fileOutpuStream;
			try 
			{
				fileOutpuStream = new FileOutputStream(file);
				JSONSerializer jsonSerializer = new JSONSerializer();
				jsonSerializer.Write(mDiaryEntry, fileOutpuStream);
				
				try 
				{
					fileOutpuStream.flush();
					fileOutpuStream.close();
					return true;
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public void onClick(View v)
	{
		if (v == mNewEntryButton)
		{
            Intent newEntryScreen = new Intent(getApplicationContext(), NewEntryActivity.class);
			startActivity(newEntryScreen);
		}
		else if(v == mGalleryButton)
		{
			Intent newEntryScreen = new Intent(getApplicationContext(), GalleryActivity.class);
			startActivity(newEntryScreen);	 
		}
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) 
	{
		return super.onTouchEvent(event);
    }

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
