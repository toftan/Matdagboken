package com.mw.matdagboken;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class Main extends Activity
{
	private CameraLayout mCameraLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); //Comment 2
		
		mCameraLayout = new CameraLayout(this);
		setContentView(mCameraLayout.getContentView());
	}
	
	@Override
    public boolean onTouchEvent(MotionEvent event) 
	{
		if (MotionEvent.ACTION_UP == event.getAction())
		{
			mCameraLayout.tryStartCameraActivity(this);
			return true;
		}
		return super.onTouchEvent(event);
    }

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
	    if (mCameraLayout != null) 
	    {
	        mCameraLayout.onActivityResult(requestCode, resultCode, data);
	    }
	}
}
