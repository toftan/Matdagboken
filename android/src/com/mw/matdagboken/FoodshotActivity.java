package com.mw.matdagboken;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;

public class FoodshotActivity extends Activity
{
	//Add actions to action bar
		@Override
		public boolean onCreateOptionsMenu(Menu menu) 
		{
		    // Inflate the menu items for use in the action bar
		    MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.actionbar, menu);
		    return super.onCreateOptionsMenu(menu);
		}
}
