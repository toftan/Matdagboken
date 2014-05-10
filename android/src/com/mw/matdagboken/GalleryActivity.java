package com.mw.matdagboken;

import java.util.ArrayList;
import android.app.ActionBar;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GalleryActivity extends FoodshotActivity implements OnClickListener
{
	private GridView mGridView; //Why not set to null??
	//private ArrayList<Item> mGridArray = new ArrayList<Item>(); 
	//private ImageAdapter mCustomImageAdapter; 

	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.galleryactivity);
		
		GridView gridView = (GridView) findViewById(R.id.galleryGridView);
		gridView.setAdapter(new ImageAdapter(this));
		
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	/*	gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
		    	}
		});*/
	}
	
	//Respond to actions in the action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle presses on the action bar items
	    switch (item.getItemId()) 
	    {
	        case android.R.id.home:
	        	finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub	
	}

}
