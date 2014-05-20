package com.mw.matdagboken;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GalleryActivity extends FoodshotActivity implements OnClickListener, OnItemClickListener
{
	// Number of columns of Grid View
	//private static final int NUM_OF_COLUMNS = 3;
    // Gridview image padding
    //private static final int GRID_PADDING = 4; // in dp
	
	private GridView mGridView = null;
	//private ArrayList<Item> mGridArray = new ArrayList<Item>(); 
	//private ImageAdapter mCustomImageAdapter;
	
	public int getScreenWidth()
	{
        int columnWidth;
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        display.getSize(point);
        
        columnWidth = point.x;
        return columnWidth;
    }

	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.galleryactivity);
		
		mGridView = (GridView) findViewById(R.id.galleryGridView);
		mGridView.setAdapter(new ImageAdapter(this));
		
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		/*Resources r = getResources();
        float floatPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GRID_PADDING, r.getDisplayMetrics());
        int columnWidth = (int) ((getScreenWidth() - ((NUM_OF_COLUMNS + 1) * floatPadding)) / NUM_OF_COLUMNS);
        mGridView.setNumColumns(NUM_OF_COLUMNS);
        mGridView.setColumnWidth(columnWidth);
        mGridView.setStretchMode(GridView.NO_STRETCH);
        
        int padding = (int) floatPadding;
        mGridView.setPadding(padding, padding, padding, padding);
        mGridView.setHorizontalSpacing(padding);
        mGridView.setVerticalSpacing(padding);*/
        mGridView.setOnItemClickListener(this);
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
		if(v.getTag() != null)
		{
			Intent newModifyScreen = new Intent(getApplicationContext(), ModifyEntryActivity.class);
			ImageHelper.EntryFile entryFile = (ImageHelper.EntryFile) v.getTag();
			
			newModifyScreen.putExtra("jsonPath", entryFile.mJsonFile.getAbsolutePath());
			newModifyScreen.putExtra("pngPath", entryFile.mPngFile.getAbsolutePath());
			
			startActivity(newModifyScreen);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		ImageView imageView = (ImageView) view.findViewById(R.id.gallery_image);
        if (imageView != null)
        {
			if(imageView.getTag() != null)
			{
				Intent newModifyScreen = new Intent(getApplicationContext(), ModifyEntryActivity.class);
				ImageHelper.EntryFile entryFile = (ImageHelper.EntryFile) imageView.getTag();
				
				newModifyScreen.putExtra("jsonPath", entryFile.mJsonFile.getAbsolutePath());
				newModifyScreen.putExtra("pngPath", entryFile.mPngFile.getAbsolutePath());
				
				startActivity(newModifyScreen);
			}
        }
		
	}

}
