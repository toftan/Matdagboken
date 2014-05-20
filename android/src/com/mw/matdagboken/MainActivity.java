package com.mw.matdagboken;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FoodshotActivity implements OnClickListener
{

	private Button mNewEntryButton;
	private Button mGalleryButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.mainactivity);
				
		mNewEntryButton = (Button) findViewById(R.id.newEntryButton);
		mNewEntryButton.setOnClickListener(this);
		
		mGalleryButton = (Button) findViewById(R.id.galleryButton);
		mGalleryButton.setOnClickListener(this);
		
		ActionBar actionBar = getActionBar();
		actionBar.show();
		
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
	
}
