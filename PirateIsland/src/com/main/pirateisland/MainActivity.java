package com.main.pirateisland;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.widget.TextView;

public class MainActivity extends Activity {
	protected Boolean STEREOTYPO_LOT_OF_FAILS;
	protected Boolean STEREOTYPO_BETTER_THAN_THIS;
	private logindatabaseadapter DataBase;
	private User curuser;
	TextView textname, textpass;
	Button button1;
	private int placementscount = 6, move = -1, hop = 50;

	private Point placement[];
	private Point maxres, hopres;
	int fails = 3;
	MyFrame myView;

	// create movement
	private Handler mHandler;
	private Runnable mUpdate = new Runnable() {
		public void run() {
			move = move * -1;
			switch (move) {
			case 1:
				move = 0;
				break;
			case 0:
				move = -1;
				break;
			case -1:
				move = 1;
				break;
			default:
				move = 1;
				break;
			}
			myView.invalidate();
			mHandler.postDelayed(this, 500);

		}
	};

	protected User EvaluateUser(User tempuser) {
		STEREOTYPO_LOT_OF_FAILS = true;
		STEREOTYPO_BETTER_THAN_THIS = false;
		if (tempuser._DIFFICULTY == 0) {
			tempuser._DIFFICULTY = 1;
		}
		if (tempuser._MAXLEVEL == 0) {
			tempuser._MAXLEVEL = 1;
		}
		// // check if he in "LOT_OF_FAILS" Stereotipo
		if (tempuser._MAXLEVEL >= 7) {
			STEREOTYPO_BETTER_THAN_THIS = true;
			tempuser._MAXLEVEL = 6;
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
		// create view
		// get resolution before draw view
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		maxres = new Point(displaymetrics.widthPixels,
				displaymetrics.heightPixels);

		hop = (int) displaymetrics.heightPixels / 60; //
		hopres = new Point((int) displaymetrics.widthPixels / 60,
				(int) displaymetrics.heightPixels / 60);
		placement = new Point[6];
		placement[0] = new Point(hopres.x * 6, hopres.y * 20);

		placement[1] = new Point(hopres.x * 15, hopres.y * 45);
		placement[2] = new Point(hopres.x * 21, hopres.y * 56);
		placement[3] = new Point(hopres.x * 40, hopres.y * 40);
		placement[4] = new Point(hopres.x * 30, hopres.y * 20);
		placement[5] = new Point(hopres.x * 23, hopres.y * 15);
		
		// get Instance of Database Adapter
		DataBase = new logindatabaseadapter(this);
		DataBase = DataBase.open();
		
		// get user
		Intent i = getIntent();
		String curname = i.getStringExtra("name");
		String curpass = i.getStringExtra("pass");
		//demo if no user
		if (curname == null || curpass == null) {
			curname = "no";
			curpass = "user";
			curuser = DataBase.getUser(curname, curpass);
			if (curuser._USERNAME != "no")
			{	DataBase.insertEntry(curname, curpass);}
		}

        //get user
		curuser = DataBase.getUser(curname, curpass);

		//demo if no user
		if (curname == "no" && curpass == "user") {
			curuser._MAXLEVEL = 4;
			curuser._FAILSLEVEL1 = 3;
			curuser._FAILSLEVEL2 = 3;

			curuser._FAILSLEVEL3 = 2;
			curuser._FAILSLEVEL4 = 3;

			curuser._FAILSLEVEL5 = 1;
			curuser._FAILSLEVEL6 = 1;
			curuser._DIFFICULTY = 2;
			DataBase.updateAll(curuser);
			curuser = DataBase.getUser(curname, curpass);
		}

		// fix user
		curuser = EvaluateUser(curuser);

		myView = new MyFrame(this);
		// start view
		setContentView(myView);

		// print user
		//PrintUser(curuser);

		// start moves
		mHandler = new Handler();
		mHandler.post(mUpdate);

	}

	// draw screen
	public class MyFrame extends View {

		private Bitmap myBitmap, coinBitmap, starwin, starloose;
		private Paint paint = new Paint();

		public MyFrame(Context context) {
			super(context);
			starwin = BitmapFactory.decodeResource(getResources(),
					com.main.pirateisland.R.drawable.starok);
			starwin = Bitmap
					.createScaledBitmap(starwin, 3 * hop, 3 * hop, true);
			starloose = BitmapFactory.decodeResource(getResources(),
					com.main.pirateisland.R.drawable.starfail);
			starloose = Bitmap.createScaledBitmap(starloose, 3 * hop, 3 * hop,
					true);

			myBitmap = BitmapFactory.decodeResource(getResources(),
					com.main.pirateisland.R.drawable.neverlandmap);
			myBitmap = Bitmap.createScaledBitmap(myBitmap, maxres.x, maxres.y,
					true);
			coinBitmap = BitmapFactory.decodeResource(getResources(),
					com.main.pirateisland.R.drawable.goldencoin);
			coinBitmap = Bitmap.createScaledBitmap(coinBitmap, 4 * hop,
					4 * hop, true);

			// Toast.makeText(getApplicationContext(),
			//
			// "maxx=" + maxres.x + "\nmaxy=" + maxres.y + "\nhop=" + hop,
			// Toast.LENGTH_LONG).show();
			// TODO Auto-generated constructor stub
		}

		@SuppressLint({ "DrawAllocation", "UseValueOf" })
		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawBitmap(myBitmap, 1, 1, null);
			paint.setColor(Color.BLACK);
			paint.setTextSize(4 * hop);
			paint.setStrokeWidth(4);

			int score = (6 * 24 * curuser._DIFFICULTY)
					+ (curuser._MAXLEVEL * 4 - curuser._USERNEGATIVESCORE);
			canvas.drawBitmap(coinBitmap, 4, hop, null);

			canvas.drawText(":" + new Integer(score).toString(), 4 + (4 * hop),
					(4 * hop) + 4, paint);

			for (int i = 0; i < placementscount; i++) {

				if (i + 1 <= curuser._MAXLEVEL) {
					// paint fails

					if (i == 0) {
						fails = curuser._FAILSLEVEL1;
					} else if (i == 1) {
						fails = curuser._FAILSLEVEL2;
					} else if (i == 2) {
						fails = curuser._FAILSLEVEL3;
					} else if (i == 3) {
						fails = curuser._FAILSLEVEL4;
					} else if (i == 4) {
						fails = curuser._FAILSLEVEL5;
					} else if (i == 5) {
						fails = curuser._FAILSLEVEL6;
					} else {
						fails = 0;
					}

					Point star = new Point(placement[i].x + (3 * hop),
							placement[i].y - 2 * hop);
					if (fails <= 2) {
						canvas.drawBitmap(starwin, star.x, star.y, null);
					} else {
						canvas.drawBitmap(starloose, star.x, star.y, null);
					}
					if (fails <= 1) {
						canvas.drawBitmap(starwin, star.x + (3 * hop), star.y,
								null);
					} else {
						canvas.drawBitmap(starloose, star.x + (3 * hop),
								star.y, null);
					}
					if (fails == 0) {
						canvas.drawBitmap(starwin, star.x + (6 * hop), star.y,
								null);
					} else {
						canvas.drawBitmap(starloose, star.x + (6 * hop),
								star.y, null);
					}

					if (i + 1 == curuser._MAXLEVEL) {

						// paint triangle
						paint.setColor(Color.GREEN);
						paint.setStyle(Paint.Style.FILL_AND_STROKE);
						paint.setAntiAlias(true);
						Point s = new Point(placement[i].x, placement[i].y
								- (4 * hop + move * hop));
						Point a = new Point(0, 0);
						Point b = new Point(-1 * hop, -2 * hop);
						Point c = new Point(+1 * hop, -2 * hop);
						Path path = new Path();
						path.setFillType(FillType.EVEN_ODD);
						path.moveTo(s.x + b.x, s.y + b.y);
						path.lineTo(s.x + c.x, s.y + c.y);
						path.lineTo(s.x + a.x, s.y + a.y);
						path.lineTo(s.x + b.x, s.y + b.y);
						path.close();
						canvas.drawPath(path, paint);
					}
					paint.setColor(Color.GREEN);
				} else {
					paint.setColor(Color.GRAY);
				}
				canvas.drawCircle(placement[i].x, placement[i].y, 2 * hop,
						paint);
				if (i + 1 <= curuser._MAXLEVEL) {
					paint.setColor(Color.WHITE);
				} else {
					paint.setColor(Color.DKGRAY);
				}
				canvas.drawCircle(placement[i].x, placement[i].y,
						(int) (1.5 * hop), paint);
				paint.setColor(Color.BLACK);
				canvas.drawCircle(placement[i].x, placement[i].y, hop, paint);

				if (i != 0) {
					canvas.drawLine(placement[i - 1].x, placement[i - 1].y,
							placement[i].x, placement[i].y, paint);
				}

			}

		}
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {

		super.onPause();
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {

		int eventaction = event.getAction();

		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN:
			// touch down so check if the finger is on
			for (int i = 0; i < placementscount && i < curuser._MAXLEVEL ; i++) {
				if (Math.abs(X - placement[i].x) < 4 * hop
						&& Math.abs(Y - placement[i].y) < 4 * hop) {
					// here i is touched ara i+1 pista
					// Call the level
					curuser._CURRENTLEVEL = i + 1;
					DataBase.updateAll(curuser);
					curuser = DataBase.getUser(curuser._USERNAME, curuser._AGE);
					if (i + 1 == 1 || i + 1 == 3 || i + 1 == 5) {
						Intent a = new Intent(MainActivity.this,
								Exercise1.class);
						a.putExtra("name", curuser._USERNAME);
						a.putExtra("pass", curuser._AGE);
						startActivity(a);
					} else {
						Intent a = new Intent(MainActivity.this,
								SplitActivity.class);
						a.putExtra("name", curuser._USERNAME);
						a.putExtra("pass", curuser._AGE);
						startActivity(a);
					}
					finish();
				}
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger

			break;

		case MotionEvent.ACTION_UP:
			// touch drop - just do things here after dropping

			break;
		}
		// redraw the canvas

		return true;

	}

}
