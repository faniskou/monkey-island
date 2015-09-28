package com.main.pirateisland;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
//import javax.swing.*;


public class LoginScreen extends Activity {
	
	EditText editTextUserName,editTextPassword,editTextConfirmPassword;
	Button btnCreateAccount,btnSignIn;
	private MediaPlayer mp;
	public MediaPlayer mPlayer;
	logindatabaseadapter loginDataBaseAdapter;
	GPS gps;
	String cityName = null;
	Button btnShowLocation;
	ImageButton imageButton1, imageButton2, imageButton3;
	
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		
		StartPlayer();
		
		// get Instance  of Database Adapter
		loginDataBaseAdapter=new logindatabaseadapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();
 
		// Get Refferences of Views
		editTextUserName=(EditText)findViewById(R.id.editTextUserNameToLogin);
		editTextPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
		//fanis to be deleted
		editTextConfirmPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
 
		/*
		
		btnSignIn=(Button)findViewById(R.id.buttonSignIn);
		btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
 
		public void onClick(View v) {
 
			String userName=editTextUserName.getText().toString();
			String password=editTextPassword.getText().toString();
			String confirmPassword=editTextConfirmPassword.getText().toString();
 
			// check if any of the fields are vaccant
			if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
			{
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
			}
			// check if both password matches
			if(!password.equals(confirmPassword))
			{
				Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
				return;
			}
			else
			{
			    // Save the Data in Database
			    loginDataBaseAdapter.insertEntry(userName, password);
			    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
			    login(v);
			}
			
		}
		
		
		});
		// Set On ClickListener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// get The User name and Password
				login(v);
			}
		
		}); */
		
		imageButton1 = (ImageButton) findViewById(R.id.imageButton1);	
		imageButton1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				gps= new GPS(LoginScreen.this);
				
				//if (gps.canGetLocation()){
				    
					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();
					Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());
					
					List<Address> addresses;
					try {
						addresses = gcd.getFromLocation(latitude, longitude, 1);
						
						 if (addresses.size() > 0)
				                System.out.println(addresses.get(0).getLocality());
				            cityName = addresses.get(0).getLocality();
				            Toast.makeText(getApplicationContext(), R.string.tripfrom + cityName + R.string.tripdest, Toast.LENGTH_LONG).show();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//if (addresses.size() > 0) 
					   // System.out.println(addresses.get(0).getLocality());
					
					//Toast.makeText(getApplicationContext(), "Eisai edo -\nlat: " + latitude +"-\nlong:" + longitude, Toast.LENGTH_LONG).show();
			//	} 
			//else{
				//	gps.showsettingsAlert();
				//}
			}
		
		});
		
		
		imageButton2 = (ImageButton) findViewById(R.id.imageButton2);	
		imageButton2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// get The User name and Password
				login(v);
			}
		
		});
		
		imageButton3 = (ImageButton) findViewById(R.id.imageButton3);	
		imageButton3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				String userName=editTextUserName.getText().toString();
				String password=editTextPassword.getText().toString();
				String confirmPassword=editTextConfirmPassword.getText().toString();
	 
				// check if any of the fields are vaccant
				if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
				{
						Toast.makeText(getApplicationContext(), R.string.fieldvaccant, Toast.LENGTH_LONG).show();
						return;
				}
				// check if both password matches
				if(!password.equals(confirmPassword))
				{
					Toast.makeText(getApplicationContext(), R.string.passworddoesnotmatch, Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
				    // Save the Data in Database
				    loginDataBaseAdapter.insertEntry(userName, password);
				    Toast.makeText(getApplicationContext(), R.string.accountsuccessfullycreated, Toast.LENGTH_LONG).show();
				    login(v);
				}
				
				
				
				
			}
		
		});
		
		
		
		
	}
	private void login (View v){
		String userName=editTextUserName.getText().toString();
		String password=editTextPassword.getText().toString();

		// fetch the Password form database for respective user name
		String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

		// check if the Stored password matches with  Password entered by user
		if(password.equals(storedPassword))
		{
			Toast.makeText(getApplicationContext(), R.string.loginsuccessfull, Toast.LENGTH_LONG).show();
			//TODO here is actual coding for this Activity 
			Intent a = new Intent(LoginScreen.this, MainActivity.class);
            a.putExtra("name",userName);
            a.putExtra("pass",password);
            startActivity(a);

            finish();
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), R.string.nameorpassword , Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_screen, menu);
		return true;
	}


	Connection connection=null;
	public LoginScreen(){
		//initialize
		//connection=sqlliteconnection.dbConnector();
		
	}
	
	public  void StartPlayer(){
		MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.song2);


		            // TODO Auto-generated method stub
		            mPlayer = MediaPlayer.create(getApplicationContext(),R.raw.song2);
		            mPlayer.start();//Start playing the music

		}
		public void StopPlayer(){
		            if(mPlayer!=null && mPlayer.isPlaying()){//If music is playing already
		                mPlayer.stop();//Stop playing the music
		          }
		}
	
	
	@Override
	 protected void onDestroy() {
	   if(null!=mp){
	  mp.release();
	   }
	   super.onDestroy();
	 }
	/*
	@Override
	public void onResume() {
       super.onResume();
     StartPlayer();
    }
	
	*/
	
	@Override
	public void onPause() {
       super.onPause();
     StopPlayer();
    }
    
		
		
	
}
