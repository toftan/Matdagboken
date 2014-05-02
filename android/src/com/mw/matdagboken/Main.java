package com.mw.matdagboken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class Main extends Activity implements OnClickListener
{
	private LinearLayout mLayout;
	private Button mCameraButton;
	private Button mSaveButton;
	private DiaryEntry mDiaryEntry;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); //Comment 2
		
		mLayout = new LinearLayout(this);
		mLayout.setBackgroundColor(0xFFFFFFFF);
		mLayout.setOrientation(LinearLayout.VERTICAL);
		
		mCameraButton = new Button(this);
		mCameraButton.setText("Take Picture");
		mCameraButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mCameraButton.setOnClickListener(this);
		
		mSaveButton = new Button(this);
		mSaveButton.setText("Save Achievement");
		mSaveButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mSaveButton.setOnClickListener(this);

		mLayout.addView(mCameraButton);
		mLayout.addView(mSaveButton);
		
		mDiaryEntry = new DiaryEntry();
		
		setContentView(mLayout);
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
		if (v == mCameraButton)
		{
            Intent cameraScreen = new Intent(getApplicationContext(), CameraActivity.class);
			startActivity(cameraScreen);
		}
		else if (v == mSaveButton)
		{
			trySaveDiaryEntry();
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
