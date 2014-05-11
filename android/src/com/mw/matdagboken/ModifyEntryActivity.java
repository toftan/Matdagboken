package com.mw.matdagboken;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class ModifyEntryActivity extends NewEntryActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// /storage/sdcard0/Matdagboken/20140510_125748/MD_20140510_125748.png
		
		super.onCreate(savedInstanceState);
		
		File entryFile = new File("/storage/sdcard0/Matdagboken/20140510_125748/MD_20140510_125748.json");
		mEntry = EntrySerializer.tryLoadEntry(entryFile);
		
		if(mEntry != null)
		{
			
		//	mCalendar.setTime(mEntry.Date);
			
			//updateDateLabel();
			updateTimeLabel();
			
			
			/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
		    String dateStamp = dateFormat.format(mEntry.Date); 
		    EditText dateEntry = (EditText) findViewById(R.id.dateEntry);
		    dateEntry.setText(dateStamp);
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
			String timeStamp = timeFormat.format(mEntry.Date);
			Button timeEntry = (Button) findViewById(R.id.timeEntry);
			timeEntry.setText(timeStamp);
			yyyyMMdd_HHmmss*/
			
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
				
		}
		
	}
}