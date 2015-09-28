package com.main.pirateisland;

import java.util.Random;
import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SplitActivity extends Activity {

	public static int getDrawable(Context context, String name) {
		Assert.assertNotNull(context);
		Assert.assertNotNull(name);

		return context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());
	}

	public static int getStringGroup(Context context, String name) {
		Assert.assertNotNull(context);
		Assert.assertNotNull(name);

		return context.getResources().getIdentifier(name, "array",
				context.getPackageName());
	}

	public static int[] addInt(int[] series, int newInt) {
		// create a new array with extra index
		int[] newSeries = new int[series.length + 1];

		// copy the integers from series to newSeries
		for (int i = 0; i < series.length; i++) {
			newSeries[i] = series[i];
		}
		// add the new integer to the last index
		newSeries[newSeries.length - 1] = newInt;
		return newSeries;
	}

	private void usehelp() {
		if (helpused < 3) {
			helpused = helpused + 1;
		}
		initgame();
		initplaces();
	}

	private void initgame() {
		// set numbers

		placementscount = new Random().nextInt(4) + (curuser._CURRENTLEVEL)
				+ ((10 * curuser._DIFFICULTY) - 1);

		if (placementscount <= 0) {
			placementscount = 1;
		}
		askedresultplacementscount = new Random().nextInt(placementscount);
		if (placementscount - askedresultplacementscount <= 0) {
			placementscount++;
		}
		resultplacementscount = placementscount;
		if (placementscount < 10) {
			monadescount = placementscount;
			placecount = monadescount;
		} else {
			if (placementscount % 10 == 0) {
				monadescount = 10;
				placecount = ((int) placementscount / 10) - 1 + monadescount;

			} else {
				monadescount = 10 + (placementscount % 10);
				placecount = ((int) placementscount / 10) - 1 + monadescount;
			}
		}
		placement = new Place[placecount];
		minusplacementscount = 0;

	}

	private void initplaces() {
		// if help
		if (helpused < 1) {
			for (int i = 0; i < placecount; i++) {
				if (i < monadescount) {
					placement[i] = new Place(
							(int) ((Math.random() * (placeme.x - 100)) + 51),
							(int) ((Math.random() * (placeme.y - 100)) + 51),
							1, false);
				} else {
					placement[i] = new Place(
							(int) ((Math.random() * (placeme.x - 100)) + 51),
							(int) ((Math.random() * (placeme.y - 100)) + 51),
							10, false);
				}
			}
		} else if (helpused >= 1) {
			int dekades = (int) ((placementscount - askedresultplacementscount) / 10);
			if (monadescount >= ((placementscount - askedresultplacementscount) % 10) + 10
					&& 10 <= placementscount - askedresultplacementscount) {
				dekades--;
			}
			for (int i = 0; i < placecount; i++) {
				if (i < monadescount) {
					if ((i < ((placementscount - askedresultplacementscount) % 10))
							|| (i < ((placementscount - askedresultplacementscount) % 10) + 10
									&& monadescount >= ((placementscount - askedresultplacementscount) % 10) + 10 && 10 <= placementscount
									- askedresultplacementscount)) {
						if (helpused >= 2) {
							placement[i] = new Place(
									(int) ((Math.random() * (placeme.x - 100 )) + 51),
									(int) ((Math.random() * (placeme.y - 100 - ((int)placeme.y/2))) + ((int)placeme.y/2) + 51),
									1, true);
						} else {
							placement[i] = new Place(
									(int) ((Math.random() * (placeme.x - 100)) + 51),
									(int) ((Math.random() * (placeme.y - 100)) + 51),
									1, true);
						}
					} else {
						if (helpused >= 2) {
						placement[i] = new Place(
								(int) ((Math.random() * (placeme.x - 100)) + 51),
								(int) ((Math.random() * (placeme.y - 100) - ((int)placeme.y/2)) + 51),
								1, false);
						} else {
							placement[i] = new Place(
									(int) ((Math.random() * (placeme.x - 100)) + 51),
									(int) ((Math.random() * (placeme.y - 100)) + 51),
									1, false);
						}
					}
				} else {
					if (dekades > 0) {
						if (helpused >= 2) {
						placement[i] = new Place(
								(int) ((Math.random() * (placeme.x - 100)) + 51),
								(int) ((Math.random() * (placeme.y - 100 - ((int)placeme.y/2)))+ ((int)placeme.y/2) + 51),
								10, true);
						} else {
							placement[i] = new Place(
									(int) ((Math.random() * (placeme.x - 100)) + 51),
									(int) ((Math.random() * (placeme.y - 100)) + 51),
									10, true);
						}
						dekades--;
					} else {
						if (helpused >= 2) {
						placement[i] = new Place(
								(int) ((Math.random() * (placeme.x - 100)) + 51),
								(int) ((Math.random() * (placeme.y - 100 - ((int)placeme.y/2) )) + 51),
								10, false);
						} else {
							placement[i] = new Place(
									(int) ((Math.random() * (placeme.x - 100)) + 51),
									(int) ((Math.random() * (placeme.y - 100)) + 51),
									10, false);
						}

					}
				}
			}

		}
	}

	// ----- activity params ------
	// sensor describe
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	// Perigrafh antikeimenwn
	private int placementscount, placecount, monadescount,
			minusplacementscount, resultplacementscount,
			askedresultplacementscount = 0;
	private int drawchoice, drawchoice2, drawbackround;
	private int balID = -1;

	private class Place {
		Point pos;
		int price;
		boolean solution;

		public Place(int x, int y, int p, boolean s) {
			pos = new Point(x, y);
			price = p;
			solution = s;

		}
	}

	private Place placement[];
	private Point maxres, halfres, basketplace, backplace, helpplace, placeme,
			textplace;
	private int fontssize;
	private int gamestate = 0;
	private int hop;
	private int helpused = 0;
	private logindatabaseadapter DataBase;
	private User curuser;
	String[] group;
	String[] backrounds = { "backround1", "backround2", "backround3",
			"backround4", "backround5" };

	String[] spltthemes = { "splttheme1", "splttheme2", "splttheme3",
			"splttheme4", "splttheme5", "splttheme6", "splttheme7",
			"splttheme8", "splttheme9", "splttheme10" };
	MyFrame myView;

	public SplitActivity() {

	}

	private final SensorEventListener mSensorListener = new SensorEventListener() {

		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta; // perform low-cut filter
			if (mAccel > 11) {
				checkwin();
				myView.invalidate();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

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

	public void checkwin() {
		if (placementscount == askedresultplacementscount) {
			gamestate = 1;
		} else {
			usehelp();
			Toast.makeText(getApplicationContext(),
					this.getResources().getString(R.string.resumeplease),
					Toast.LENGTH_LONG).show();
		}
	}

	// event on create screen
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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

		initgame();
		// INITIALIZE PARAMETERS FOR SCREEN
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		maxres = new Point(displaymetrics.widthPixels,
				displaymetrics.heightPixels);
		hop = 10;

		halfres = new Point(maxres.x / 2, maxres.y / 2);
		int orientation = this.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			placeme = new Point(maxres.x, halfres.y);
			textplace = new Point(0, maxres.y);
			hop = (int) ((double) halfres.x / 60);
			basketplace = new Point(33 * hop, halfres.y);
			helpplace = new Point(30 * hop, maxres.y - 15 * hop);
			backplace = new Point(50 * hop, maxres.y - 15 * hop);
		} else {
			placeme = new Point(halfres.x, maxres.y);

			textplace = new Point(halfres.x, halfres.y);
			hop = (int) ((double) halfres.y / 60);
			basketplace = new Point(halfres.x + (5 * hop), halfres.y
					+ (8 * hop));
			helpplace = new Point(maxres.x - (34 * hop), maxres.y - 17 * hop);
			backplace = new Point(maxres.x - (17 * hop), maxres.y - 17 * hop);
		}
		if (maxres.x < 300 || maxres.y < 300) {
			Toast.makeText(getApplicationContext(),
					"Abord. We need more pixels.", Toast.LENGTH_LONG).show();
			placeme = new Point(100, 100);
		}
		initplaces();

		int temprandom = new Random().nextInt(spltthemes.length);
		if (temprandom >= 10) {
			temprandom = 9;
		}
		Log.v("fanis", "current theme :" + String.valueOf(temprandom));
		int g = getStringGroup(this, spltthemes[temprandom]);
		String gs[] = this.getResources().getStringArray(g);
		group = gs;

		drawchoice = getDrawable(this, group[0]);
		drawchoice2 = getDrawable(this, group[1]);
		temprandom = new Random().nextInt(backrounds.length);
		if (temprandom >= 5) {
			temprandom = 4;
		}
		
		Log.v("fanis", "current backround :" + String.valueOf(temprandom));
		drawbackround = getDrawable(this, (backrounds[temprandom]));
		// SETVIEW
		myView = new MyFrame(this);
		setContentView(myView);

		// INITIALIZE PARAMETERS FOR sensor after because it crashes
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
	}

	// draw screen
	public class MyFrame extends View {

		private Bitmap myBitmap, basketBitmap, backround, helpbtmp, backbtmp;
		private Paint paint = new Paint();

		public MyFrame(Context context) {
			super(context);
			myBitmap = BitmapFactory.decodeResource(getResources(), drawchoice);
			myBitmap = Bitmap.createScaledBitmap(myBitmap, 18 * hop, 18 * hop,
					true);
			helpbtmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.help);
			helpbtmp = Bitmap.createScaledBitmap(helpbtmp, 14 * hop, 14 * hop,
					true);
			backbtmp = BitmapFactory.decodeResource(getResources(),
					R.drawable.back);
			backbtmp = Bitmap.createScaledBitmap(backbtmp, 14 * hop, 14 * hop,
					true);
			basketBitmap = BitmapFactory.decodeResource(getResources(),
					drawchoice2);
			basketBitmap = Bitmap.createScaledBitmap(basketBitmap, 26 * hop,
					26 * hop, true);
			backround = BitmapFactory.decodeResource(getResources(),
					drawbackround);
			backround = Bitmap.createScaledBitmap(backround, maxres.x,
					maxres.y, true);
		}

		@Override
		protected void onDraw(Canvas canvas) {

			fontssize = 6 * hop;
			if (gamestate >= 1) {
				paint.setColor(Color.WHITE);
				paint.setTextSize(fontssize);
				String mess = getResources().getString(R.string.winmessage);
				canvas.drawText(mess, 10, halfres.y, paint);
				paint.setColor(Color.CYAN);
				canvas.drawText(mess, 10, halfres.y + 1 * (fontssize + 5),
						paint);
				paint.setColor(Color.MAGENTA);
				canvas.drawText(mess, 10, halfres.y - 1 * (fontssize + 5),
						paint);
				paint.setColor(Color.YELLOW);
				canvas.drawText(mess, 10, halfres.y + 2 * (fontssize + 5),
						paint);
				paint.setColor(Color.BLUE);
				canvas.drawText(mess, 10, halfres.y - 2 * (fontssize + 5),
						paint);
				paint.setColor(Color.RED);
				canvas.drawText(mess, 10, halfres.y + 3 * (fontssize + 5),
						paint);
				paint.setColor(Color.DKGRAY);
				canvas.drawText(mess, 10, halfres.y - 3 * (fontssize + 5),
						paint);
				gamestate = 2;

			} else {
				paint.setColor(Color.WHITE);
				paint.setTextSize(fontssize);
				canvas.drawBitmap(backround, 1, 1, null);

				canvas.drawBitmap(helpbtmp, helpplace.x, helpplace.y, null);
				canvas.drawBitmap(backbtmp, backplace.x, backplace.y, null);
				paint.setTextSize(2 * fontssize);
				paint.setColor(Color.RED);
				paint.setStyle(Paint.Style.STROKE);
				paint.setStrokeWidth(hop);
				canvas.drawBitmap(basketBitmap, basketplace.x, basketplace.y,
						null);
				if (helpused >= 1) {
					canvas.drawCircle(basketplace.x + (13 * hop), basketplace.y
							+ (13 * hop), 13 * hop, paint);
				}
				for (int i = 0; i < placecount; i++) {
					if (placement[i].solution && helpused >= 1) {
						canvas.drawCircle(placement[i].pos.x + (9 * hop),
								placement[i].pos.y + (9 * hop), 9 * hop, paint);
					}
					if (placement[i].solution && helpused >= 3) {
						canvas.drawLine(placement[i].pos.x + (9 * hop),
								placement[i].pos.y + (9 * hop), basketplace.x
										+ (13 * hop), basketplace.y
										+ (13 * hop), paint);
					}
					canvas.drawBitmap(myBitmap, placement[i].pos.x,
							placement[i].pos.y, null);
					if (placement[i].price > 1) {
						paint.setColor(Color.BLACK);
						canvas.drawText(String.valueOf(placement[i].price),
								placement[i].pos.x + 3 * hop,
								placement[i].pos.y + 8 * hop, paint);
					}
				}
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
				paint.setStrokeWidth(((int) hop / 3) + 1);
				paint.setColor(Color.WHITE);
				paint.setTextSize(fontssize);

				int astring = (placementscount - askedresultplacementscount);
				canvas.drawText(getResources()
						.getString(R.string.moveitmessage), textplace.x + 10,
						textplace.y - 6 * (fontssize + 5), paint);
				String name;
				if (astring < 2) {
					name = group[2];
				} else {
					name = group[3];
				}

				canvas.drawText(astring + " " + name + " " + group[4],
						textplace.x + 10, textplace.y - 5 * (fontssize + 5),
						paint);
				if (astring != 0) {
					paint.setColor(Color.RED);
				}
				// basketplace.x, basketplace.y
				canvas.drawText(placementscount + "  -  "
						+ minusplacementscount + "  =   "
						+ askedresultplacementscount, textplace.x + 10,
						textplace.y - 3 * (fontssize + 5), paint);
				paint.setColor(Color.WHITE);
				canvas.drawText(String.valueOf(minusplacementscount),
						basketplace.x + 5, basketplace.y + fontssize + 5, paint);
				canvas.drawText(getResources().getString(R.string.movetocheck),
						textplace.x + 10, textplace.y - 2 * (fontssize + 5),
						paint);
				paint.setColor(Color.RED);
				canvas.drawText(getResources().getString(R.string.hittocheck),
						textplace.x + 10, textplace.y - 1 * (fontssize + 5),
						paint);
			}
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {

		int eventaction = event.getAction();

		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
			if (gamestate == 2) {
				if (curuser._CURRENTLEVEL== 1 )
				{
					curuser._FAILSLEVEL1 = helpused;
				}
				else if (curuser._CURRENTLEVEL== 2 )
				{
					curuser._FAILSLEVEL2 = helpused;
				}
				else if (curuser._CURRENTLEVEL== 3 )
				{
					curuser._FAILSLEVEL3 = helpused;
				}
				else if (curuser._CURRENTLEVEL== 4 )
				{
					curuser._FAILSLEVEL4 = helpused;
				}
				else if (curuser._CURRENTLEVEL== 5 )
				{
					curuser._FAILSLEVEL5 = helpused;
				}
				else if (curuser._CURRENTLEVEL== 6 )
				{
					curuser._FAILSLEVEL6 = helpused;
				}


				if (curuser._MAXLEVEL == curuser._CURRENTLEVEL) {
					curuser._MAXLEVEL = curuser._MAXLEVEL + 1;

				}
				DataBase.updateAll(curuser);
				Intent a = new Intent(SplitActivity.this, MainActivity.class);
				// we must change it accordingly
				a.putExtra("name", curuser._USERNAME);
				a.putExtra("pass", curuser._AGE );
				startActivity(a);

				finish();
			} else {

				for (int i = 0; i < placecount; i++) {
					// check all the bounds of the ball
					if (Math.abs(X - (placement[i].pos.x + (9 * hop))) < 10 * hop
							&& Math.abs(Y - (placement[i].pos.y + (9 * hop))) < 10 * hop) {
						if (Math.abs(placement[i].pos.x - basketplace.x) < 3
								&& Math.abs(placement[i].pos.y - basketplace.y) < 3) {

							minusplacementscount = minusplacementscount
									- placement[i].price;
							resultplacementscount = resultplacementscount
									+ placement[i].price;
							askedresultplacementscount = askedresultplacementscount
									- placement[i].price;

						}
						balID = i;
						break;
					}
				}

				// check for buttons
				if (balID == -1) {
					if (Y <= textplace.y - 1 * (fontssize + 5)
							&& Y >= textplace.y - 3 * (fontssize + 5)
							&& X >= textplace.x + 10
							&& X <= textplace.x + 10 + (fontssize * 22)) {
						checkwin();
						myView.invalidate();
					} else if (Math.abs(X - (helpplace.x + (7 * hop))) < 8 * hop
							&& Math.abs(Y - (helpplace.y + (7 * hop))) < 8 * hop) {
						usehelp();
					} else if (Math.abs(X - (backplace.x + (7 * hop))) < 8 * hop
							&& Math.abs(Y - (backplace.y + (7 * hop))) < 8 * hop) {
						Intent a = new Intent(SplitActivity.this,
								MainActivity.class);
						a.putExtra("name", curuser._USERNAME);
						a.putExtra("pass", curuser._AGE );
						startActivity(a);

						finish();
					}

				}

			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger
			if (balID != -1) {
				if (Math.abs(X - (placement[balID].pos.x + (9 * hop))) < 20 * hop
						&& Math.abs(Y - (placement[balID].pos.y + (9 * hop))) < 20 * hop) {
					placement[balID] = new Place(X, Y, placement[balID].price,
							placement[balID].solution);
					myView.invalidate();
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			// touch drop - just do things here after dropping
			if (balID != -1) {
				if (Math.abs((basketplace.x + (4 * hop))
						- placement[balID].pos.x) < 10 * hop
						&& Math.abs((basketplace.y + (4 * hop))
								- placement[balID].pos.y) < 10 * hop) {
					minusplacementscount = minusplacementscount
							+ placement[balID].price;
					resultplacementscount = resultplacementscount
							- placement[balID].price;
					askedresultplacementscount = askedresultplacementscount
							+ placement[balID].price;
					placement[balID] = new Place(basketplace.x, basketplace.y,
							placement[balID].price, placement[balID].solution);

				}
				balID = -1;
			}
			myView.invalidate();
			break;
		}
		// redraw the canvas

		return true;

	}

}
