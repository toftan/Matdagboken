package com.mw.matdagboken;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class GalleryActivity extends Activity implements OnClickListener
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
		
	/*	gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
		    	}
		});*/
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub	
	}

}
