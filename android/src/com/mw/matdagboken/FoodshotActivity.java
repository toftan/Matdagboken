package com.mw.matdagboken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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
			
		public void openSettings()
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.settings_button);
			
			
			// Get the layout inflater
		    LayoutInflater inflater = this.getLayoutInflater();

			// Inflate and set the layout for the dialog
		    // Pass null as the parent view because its going in the dialog layout
		    builder.setView(inflater.inflate(R.layout.help, null));
		    final AlertDialog alert = builder.create();
			alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Close",  new DialogInterface.OnClickListener()
		    {
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					alert.dismiss();
					
				}
		    });
			alert.show();
		}
		
		//Respond to actions in the action bar
		@Override
		public boolean onOptionsItemSelected(MenuItem item) 
		{
		    // Handle presses on the action bar items
		    switch (item.getItemId()) 
		    {
		        case R.id.ac_help:
		        	openSettings();
		            return true;
		        default:
		            return super.onOptionsItemSelected(item);
		    }
		}

}
