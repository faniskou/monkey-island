package com.main.pirateisland;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import android.widget.TextView;

public class MainActivity extends Activity {
	protected Boolean STEREOTYPO_LOT_OF_FAILS;
	protected Boolean STEREOTYPO_BETTER_THAN_THIS;
	User curuser;
	TextView textname, textpass;
	Button button1;

	protected User EvaluateUser(User tempuser) {
		if (tempuser._DIFFICULTY == 0) {
			tempuser._DIFFICULTY = 1;
		}
		if (tempuser._MAXLEVEL == 0) {
			tempuser._MAXLEVEL = 1;
		}
		// // check if he in "LOT_OF_FAILS" Stereotipo
		if (tempuser._MAXLEVEL >= 7) {
			STEREOTYPO_BETTER_THAN_THIS = true;
		}
		tempuser._USERNEGATIVESCORE = tempuser._FAILSLEVEL1
				+ tempuser._FAILSLEVEL2 + tempuser._FAILSLEVEL3
				+ tempuser._FAILSLEVEL4 + tempuser._FAILSLEVEL5
				+ tempuser._FAILSLEVEL6;
		// // check if he in "BETTER_THAN_THIS" Stereotipo
		if (tempuser._USERNEGATIVESCORE <= (int) (tempuser._MAXLEVEL * 3 / 2)) {
			STEREOTYPO_BETTER_THAN_THIS = true;
		}
		// go to the next dificulty level
		if (STEREOTYPO_BETTER_THAN_THIS && !STEREOTYPO_LOT_OF_FAILS) {
			tempuser._DIFFICULTY = tempuser._DIFFICULTY + 1;
			tempuser._FAILSLEVEL1 = 0;
			tempuser._FAILSLEVEL2 = 0;
			tempuser._FAILSLEVEL3 = 0;
			tempuser._FAILSLEVEL4 = 0;
			tempuser._FAILSLEVEL5 = 0;
			tempuser._FAILSLEVEL6 = 0;
			tempuser._USERNEGATIVESCORE = 0;
			tempuser._MAXLEVEL = 1;
		}
		return tempuser;
	}

	protected void PrintUser(User tempuser) {
		Toast.makeText(
				getApplicationContext(),
				("_USERNAME=" + tempuser._USERNAME + "\n" + "_AGE="
						+ tempuser._AGE + "\n" + "_FAILSLEVEL1="
						+ tempuser._FAILSLEVEL1 + "\n" + "_FAILSLEVEL2="
						+ tempuser._FAILSLEVEL2 + "\n" + "_FAILSLEVEL3="
						+ tempuser._FAILSLEVEL3 + "\n" + "_FAILSLEVEL4="
						+ tempuser._FAILSLEVEL4 + "\n" + "_FAILSLEVEL5="
						+ tempuser._FAILSLEVEL5 + "\n" + "_FAILSLEVEL6="
						+ tempuser._FAILSLEVEL6 + "\n" + "_USERNEGATIVESCORE="
						+ tempuser._USERNEGATIVESCORE + "\n" + "_MAXLEVEL="
						+ tempuser._MAXLEVEL + "\n" + "_CURRENTLEVEL="
						+ tempuser._CURRENTLEVEL + "\n" + "_DIFFICULTY=" + tempuser._DIFFICULTY),
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		STEREOTYPO_LOT_OF_FAILS = false;
		STEREOTYPO_BETTER_THAN_THIS = false;
		// get user
		Intent i = getIntent();
		String curname = i.getStringExtra("name");
		String curpass = i.getStringExtra("pass");
		if (curname == null || curpass == null) {
			curname = "no";
			curpass = "user";
		}

		// get Instance of Database Adapter
		logindatabaseadapter DataBase;
		DataBase = new logindatabaseadapter(this);
		DataBase = DataBase.open();
		curuser = DataBase.getUser(curname, curpass);

		// fix user
		curuser = EvaluateUser(curuser);

		// print user
		PrintUser(curuser);



				// Start NewActivity.class
				//Intent myIntent = new Intent(MainActivity.this, Exercise1.class);
				//startActivity(myIntent);

	}

}
