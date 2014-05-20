package com.mw.matdagboken;

import java.io.File;
import java.sql.Date;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class ModifyEntryActivity extends NewEntryActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// /storage/sdcard0/Matdagboken/20140510_125748/MD_20140510_125748.png
		
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) 
		{
			String jsonPath = extras.getString("jsonPath");
		    String pngPath = extras.getString("pngPath");
		    
		    File entryFile = new File(jsonPath);
			File pngFile = new File(pngPath);
			mEntry = EntrySerializer.tryLoadEntry(entryFile);
			
			Bitmap bitmap = EntrySerializer.tryLoadPGNFile(pngFile);
			
			if(bitmap != null)
			{
				ImageView foodImage = (ImageView) findViewById(R.id.foodImage);
				foodImage.setImageBitmap(bitmap);	
			}
			
			if(mEntry != null)
			{
				Date date = new Date(mEntry.Time);
				mCalendar.setTime(date);
				
				updateDateLabel();
				updateTimeLabel();
				
				ImageButton camendarButton = (ImageButton) findViewById(R.id.calendarButton);
				camendarButton.setVisibility(View.GONE);
				
				Button timeButton = (Button) findViewById(R.id.timeEntry);
				timeButton.setClickable(false);
				timeButton.setBackgroundResource(R.drawable.appthemenocol_btn_default_holo_light);
				
				Spinner spinner = (Spinner) findViewById(R.id.mealTypeEntry);
				spinner.setSelection(mEntry.Meal);
				
				EditText beverage = (EditText) findViewById(R.id.beverageEntry);
				beverage.setText(mEntry.Beverage);
				
				EditText food = (EditText) findViewById(R.id.foodEntry);
				food.setText(mEntry.Food);
				
				EditText how = (EditText) findViewById(R.id.howEntry);
				how.setText(mEntry.How);

				EditText mood = (EditText) findViewById(R.id.moodEntry);
				mood.setText(mEntry.Mood);
				
				EditText comment = (EditText) findViewById(R.id.commentEntry);
				comment.setText(mEntry.Comment);
				
				for (int i = 0; i < mMoodButtons.size(); i++)
				{
					ImageButton moodView = mMoodButtons.get(i);
					if (i == mEntry.MoodIcon)
					{
						moodView.setBackgroundResource(R.drawable.apptheme_btn_default_holo_light);
					}
					else
					{
						moodView.setBackgroundResource(R.drawable.appthemenocol_btn_default_holo_light);
					}
				}
			}
		    
		}		
	}
}
