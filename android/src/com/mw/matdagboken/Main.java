package com.mw.matdagboken;

import android.app.Activity;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.RelativeLayout;
//import android.os.Build;

public class Main extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

        RelativeLayout myLayout = new RelativeLayout(this);
        myLayout.setBackgroundColor(0xFFFFFFFF);
        setContentView(myLayout);
	}
}
