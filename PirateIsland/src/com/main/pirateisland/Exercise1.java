package com.main.pirateisland;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class Exercise1 extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise1);
		
		
		//monimes metavlites, den allazoun
		//gia allagi me to patioma enos koumpiou, orizoume mesa sta koumpia k oxi final
		
		final int  random1 = (int) ((Math.random() * 10 ) + 1);
		final int  random2 = (int) ((Math.random() * 10 ) + 1);
		
		 Button button1 = (Button) findViewById(R.id.button1);
	     button1.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		 
	    		//int  random1 = (int) ((Math.random() * 10 ) + 1);
	    		//int  random2 = (int) ((Math.random() * 10 ) + 1);
	    		
				TextView textView1 = (TextView) findViewById(R.id.textView1);
			     textView1.setText(String.valueOf(random1)); 
			     
			     TextView textView3 = (TextView) findViewById(R.id.textView3);
			     textView3.setText(String.valueOf(random2));
				
			}
		});
	     
	     Button button2 = (Button) findViewById(R.id.button2);
	     button2.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		
	    		EditText editText1 = (EditText) findViewById(R.id.editText1);
	    		
	    		int  result = random1 +random2;
	    		
	    		int answer = Integer.parseInt(editText1.getText().toString());
	     
	    		if (answer == result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("Σωστά!");
			     }
			     
	             if (answer > result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("Λυπάμε, έκανες λάθος! Δοκίμασε ένα πιο μικρό νούμερο.");
			     }
	             
	             if (answer < result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("Λυπάμε, έκανες λάθος! Δοκίμασε ένα πιο μεγάλο νούμερο.");
			     }
				
			}
		}); 
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
