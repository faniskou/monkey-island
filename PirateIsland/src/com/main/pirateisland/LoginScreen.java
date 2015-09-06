package com.main.pirateisland;
import java.sql.*;
//import javax.swing.*;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginScreen extends Activity {
	
	EditText editTextUserName,editTextPassword,editTextConfirmPassword;
	Button btnCreateAccount,btnSignIn;
	
	logindatabaseadapter loginDataBaseAdapter;
	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);
		// get Instance  of Database Adapter
		loginDataBaseAdapter=new logindatabaseadapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();
 
		// Get Refferences of Views
		editTextUserName=(EditText)findViewById(R.id.editTextUserNameToLogin);
		editTextPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
		//fanis to be deleted
		editTextConfirmPassword=(EditText)findViewById(R.id.editTextPasswordToLogin);
 
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
			Toast.makeText(getApplicationContext(), "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
			//TODO here is actual coding for this Activity 
			Intent a = new Intent(LoginScreen.this, MainMenu.class);
            a.putExtra("name",userName);
            a.putExtra("pass",password);
            startActivity(a);

            finish();
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), "User Name or Password does not match", Toast.LENGTH_LONG).show();
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
}
