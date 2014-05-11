package com.mw.matdagboken;

import android.app.ActionBar;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.GridView;

public class GalleryActivity extends FoodshotActivity implements OnClickListener
{
	// Number of columns of Grid View
	private static final int NUM_OF_COLUMNS = 3;
    // Gridview image padding
    private static final int GRID_PADDING = 4; // in dp
	
	private GridView mGridView; //Why not set to null??
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
		
		Resources r = getResources();
        float floatPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, GRID_PADDING, r.getDisplayMetrics());
        int columnWidth = (int) ((getScreenWidth() - ((NUM_OF_COLUMNS + 1) * floatPadding)) / NUM_OF_COLUMNS);
        mGridView.setNumColumns(NUM_OF_COLUMNS);
        mGridView.setColumnWidth(columnWidth);
        mGridView.setStretchMode(GridView.NO_STRETCH);
        
        int padding = (int) floatPadding;
        mGridView.setPadding(padding, padding, padding, padding);
        mGridView.setHorizontalSpacing(padding);
        mGridView.setVerticalSpacing(padding);
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
