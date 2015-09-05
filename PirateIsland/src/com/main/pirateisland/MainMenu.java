package com.main.pirateisland;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

public class MainMenu extends Activity{
	//protected String name;
	//protected String pass;
	TextView textname,textpass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		textname=(TextView)findViewById(R.id.text_name);
		textpass=(TextView)findViewById(R.id.text_pass);

		Intent i= getIntent();
	    textname.setText(i.getStringExtra("name"));
	    textpass.setText(i.getStringExtra("pass"));
	}

}
