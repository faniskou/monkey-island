package com.main.pirateisland;

//import android.support.v7.app.ActionBarActivity;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.main.pirateisland.SplitActivity.MyFrame;


public class Exercise1 extends Activity {
	
	int errorflag = 0; 
	int MyException;
	int  random1 = (int) ((Math.random() * 10 ) + 1);
	int  random2 = (int) ((Math.random() * 10 ) + 1);
	 int  result ;
	final int A = result - 3;
    final int B = result - 1;
	final int C = result + 1;
	
	logindatabaseadapter loginDataBaseAdapter;
	
	ImageButton imageButton1, imageButton2;
	
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	
	MyFrame myView;
	
	private logindatabaseadapter DataBase;
	private User curuser;
	
	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			
			//EditText editText1 = (EditText) findViewById(R.id.editText1);
    		//int answer = Integer.parseInt(editText1.getText().toString());
			
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			if (mAccel > 11) {
				//check0(answer);
				//myView.invalidate();
				
                EditText editText1 = (EditText) findViewById(R.id.editText1);
	    		
	    		
	    		String answercheck = editText1.getText().toString();
	    		
	    		if (answercheck.matches("")) {
	    			
	    			
	    			Toast toast = Toast.makeText(getApplicationContext(), R.string.giveanswer, Toast.LENGTH_SHORT);
	    			toast.show();
	    		}
	    		else{
	    			
	    			int answer = Integer.parseInt(editText1.getText().toString());
	    			
	    			if (errorflag == 3){
		    			check3(answer);
		    		}
		    		if (errorflag == 2){
		    			check2(answer);
		    		}
		    		if (errorflag == 1) {
		    			check1(answer);
				    }
		    		if (errorflag == 0) {
		    			check0(answer);	 
		    			}
	    		}
				
				
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	private void finishok(){
		Toast toast = Toast.makeText(getApplicationContext(), R.string.correct , Toast.LENGTH_SHORT);
		toast.show();

		
		if (curuser._CURRENTLEVEL== 1 )
		{
			curuser._FAILSLEVEL1 = errorflag;
		}
		else if (curuser._CURRENTLEVEL== 2 )
		{
			curuser._FAILSLEVEL2 = errorflag;
		}
		else if (curuser._CURRENTLEVEL== 3 )
		{
			curuser._FAILSLEVEL3 = errorflag;
		}
		else if (curuser._CURRENTLEVEL== 4 )
		{
			curuser._FAILSLEVEL4 = errorflag;
		}
		else if (curuser._CURRENTLEVEL== 5 )
		{
			curuser._FAILSLEVEL5 = errorflag;
		}
		else if (curuser._CURRENTLEVEL== 6 )
		{
			curuser._FAILSLEVEL6 = errorflag;
		}


		if (curuser._MAXLEVEL == curuser._CURRENTLEVEL) {
			curuser._MAXLEVEL = curuser._MAXLEVEL + 1;

		}
		DataBase.updateAll(curuser);
		Intent a = new Intent(Exercise1.this, MainActivity.class);

		a.putExtra("name", curuser._USERNAME);
		a.putExtra("pass", curuser._AGE );
		startActivity(a);

		finish();
		
		
		
	} 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise1);
		
		// get user
		Intent inten = getIntent();
		String curname = inten.getStringExtra("name");
		String curpass = inten.getStringExtra("pass");
		if (curname == null || curpass == null) {
			curname = "no";
			curpass = "user";
			}
		DataBase = new logindatabaseadapter(this);
		DataBase = DataBase.open();
		curuser = DataBase.getUser(curname, curpass);
		
	    random1 = new Random().nextInt(4) + (curuser._CURRENTLEVEL)	+ ((10 * curuser._DIFFICULTY) - 1);
		random2 = new Random().nextInt(4) + (curuser._CURRENTLEVEL)	+ ((10 * curuser._DIFFICULTY) - 1);
		 result = random1 +random2;
		
		//to Onoma tou xristi einai stin "name"
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;
		
		
	
         TextView textView1 = (TextView) findViewById(R.id.textView1);
	     textView1.setText(String.valueOf(random1)); 
	     
	     TextView textView3 = (TextView) findViewById(R.id.textView3);
	     textView3.setText(String.valueOf(random2));
    	 
	   
	      imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
	      imageButton1.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
				
	    		
	    		EditText editText1 = (EditText) findViewById(R.id.editText1);
                String answercheck = editText1.getText().toString();
	    		
	    		if (answercheck.matches("")) {
	    			
	    			
	    			Toast toast = Toast.makeText(getApplicationContext(), R.string.giveanswer, Toast.LENGTH_SHORT);
	    			toast.show();
	    		}
	    		
	    		else{
	    			int answer = Integer.parseInt(editText1.getText().toString());
	    			
	    			if (errorflag == 3){
		    			check3(answer);
		    		}
		    		if (errorflag == 2){
		    			check2(answer);
		    		}
		    		if (errorflag == 1) {
		    			check1(answer);
				    }
		    		if (errorflag == 0) {
		    			check0(answer);	 
		    			}
	    			
	    		}
	    	}
		}); 	
	      
	      
	      imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
	      imageButton2.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
		
	    		Intent a = new Intent(Exercise1.this,
						MainActivity.class);

	    		a.putExtra("name", curuser._USERNAME);
	    		a.putExtra("pass", curuser._AGE );
	    		startActivity(a);
	    		
	    		}
	    	
		}); 	
	      
	      
	      
	}
	
	
	public void check0(int answer) {
		
		if (answer == result){
			finishok();
		}
		if (answer > result){
			Toast toast = Toast.makeText(getApplicationContext(), R.string.wrongpleasesmaller, Toast.LENGTH_SHORT);
    			toast.show();
	    	 errorflag = 1;
	     }
		if (answer < result){
			Toast toast = Toast.makeText(getApplicationContext(), R.string.wrongpleasebigger, Toast.LENGTH_SHORT);
    			toast.show();
        	 errorflag = 1;
	    }
     }

	public void check1 (int answer){
		if (answer == result){
			finishok();
	    	 }
		
		else {
			 Toast toast = Toast.makeText(getApplicationContext(), R.string.wrongchoosefromfour, Toast.LENGTH_SHORT);
   			toast.show();
   
   	        TextView textView7 = (TextView) findViewById(R.id.textView7);
   	        textView7.setText(String.valueOf(A));
   	  
   	        TextView textView8 = (TextView) findViewById(R.id.textView8);
        	 textView8.setText(String.valueOf(result));
   	 
   	         TextView textView9 = (TextView) findViewById(R.id.textView9);
              textView9.setText(String.valueOf(B));
   	 
   	         TextView textView10 = (TextView) findViewById(R.id.textView10);
   	          textView10.setText(String.valueOf(C));
   	 
   	 errorflag = 2;
		}
	}
	
	public void check2(int answer){
		
		if (answer == result){
			
			finishok();
		}
		else {
			TextView textView7 = (TextView) findViewById(R.id.textView7);
			textView7.setTextColor(Color.parseColor("#FFFFFF"));
			
			TextView textView8 = (TextView) findViewById(R.id.textView8);
			textView8.setTextColor(Color.parseColor("#FF0000"));
			
			TextView textView9 = (TextView) findViewById(R.id.textView9);
			textView9.setTextColor(Color.parseColor("#FFFFFF"));
			
			TextView textView10 = (TextView) findViewById(R.id.textView10);
			textView10.setTextColor(Color.parseColor("#FFFFFF"));
			
			
			Toast toast = Toast.makeText(getApplicationContext(), R.string.thinkmore, Toast.LENGTH_SHORT);
			toast.show();
	    	 errorflag = 3;
		}		
	}
	
	public void check3(int answer){
		if (answer == result){
	    	
			finishok();
		}
		else {
            
			Toast toast = Toast.makeText(getApplicationContext(),  R.string.thinkmore, Toast.LENGTH_SHORT);
			toast.show();
		}	
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}
	

	
	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise1, menu);
		return true;
	}
	
	
	

}
