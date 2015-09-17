package com.main.pirateisland;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.TextView;

public class MainMenu extends Activity{
	//protected String name;
	//protected String pass;
	TextView textname,textpass;
	Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		textname=(TextView)findViewById(R.id.text_name);
		textpass=(TextView)findViewById(R.id.text_pass);

		Intent i= getIntent();
	    textname.setText(i.getStringExtra("name"));
	    textpass.setText(i.getStringExtra("pass"));
	    
	    
	    button1 = (Button) findViewById(R.id.button1);
		 
		// Capture button clicks
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
 
				// Start NewActivity.class
				Intent myIntent = new Intent(MainMenu.this,
						Exercise1.class);
				startActivity(myIntent);
			}
		});
	}
	
	/*
	@Override
	public void onResume() {
       super.onResume();
     StartPlayer();
     }
*/
}
