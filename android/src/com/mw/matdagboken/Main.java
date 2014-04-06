package com.mw.matdagboken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity
{
	private CameraLayout mCameraLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		mCameraLayout = new CameraLayout(this);
		setContentView(mCameraLayout.getContentView());
		
		mCameraLayout.tryStartCameraActivity(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
	    if (mCameraLayout != null) 
	    {
	        mCameraLayout.onActivityResult(requestCode, resultCode, data);
	    }
	}
}
