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

	// ----- activity params ------
	// sensor describe
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	// Perigrafh antikeimenwn
	private int placementscount, minusplacementscount, resultplacementscount,
			askedresultplacementscount = 0;
	private int drawchoice, drawchoice2, drawbackround;
	private int balID = -1;
	private Point placement[];
	private Point maxres, halfres, basketplace, placeme, textplace;
	private int fontssize;
	private int gamestate = 0;
	private int hop;
	private int helpused = 0;
	private logindatabaseadapter DataBase;
	private User curuser;
	String[] group;
	String[] backrounds = { "backround1", "backround2", "backround3",
			"backround4", "backround5" };

	String[][] Groups = {
			{ "bottle", "ship", " μπουκάλι", " μπουκάλια", " στο καράβι." },
			{ "goldencoin", "sentoukiicon", " νόμισμα", " νομίσματα",
					" στο σεντούκι." },
			{ "hook", "boy", " γάτζο", " γάτζους", " στον Πειρατή." },
			{ "mantili", "parrot", " μαντίλι", " μαντίλια", " στον παπαγάλο." },
			{ "banana", "monkey", " μπανάνα", " μπανάνες", " στο πιθηκάκι." },
			{ "peanut", "parrot", " φυστίκι", " φυστίκια", " στον παπαγάλο." },
			{ "piratehatbase", "monkey", " καπέλο", " καπέλα", " στο πιθηκάκι." },
			{ "ship", "boy", " καράβι", " καράβια", " στον Πειρατή." },
			{ "ic_launcher", "ship", " νησί", " νησιά", " στο καράβι." },
			{ "gold", "sentoukiicon", " χρυσό", " χρυσά", " στο σεντούκι." }
	// {"baby","basket"," μωρό"," μωρά"," στο κρεβάτι."}
	};

	MyFrame myView;

	public SplitActivity() {
		placementscount = (int) ((Math.random() * 10) + 1);
		askedresultplacementscount = (int) ((Math.random() * placementscount));
//if (placementscount == askedresultplacementscount) {askedresultplacementscount--;}
		resultplacementscount = placementscount;
		placement = new Point[placementscount];


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
			Toast.makeText(getApplicationContext(), "Συνέχισε να προσπαθείς.",
					Toast.LENGTH_LONG).show();
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
				// temp set user
				switch (curuser._CURRENTLEVEL) {
				case 1:
					curuser._FAILSLEVEL1 = helpused;
				case 2:
					curuser._FAILSLEVEL2 = helpused;
				case 3:
					curuser._FAILSLEVEL3 = helpused;
				case 4:
					curuser._FAILSLEVEL4 = helpused;
				case 5:
					curuser._FAILSLEVEL5 = helpused;
				case 6:
					curuser._FAILSLEVEL6 = helpused;
				default:
					// statements // they are executed if none of the above case
					// is satisfied
					break;
				}

				if (curuser._MAXLEVEL == curuser._CURRENTLEVEL) {
					curuser._MAXLEVEL = curuser._MAXLEVEL + 1;

					DataBase.updateAll(curuser);
				}
				Intent a = new Intent(SplitActivity.this, MainActivity.class);
				// we must change it accordingly
				a.putExtra("name", "no");
				a.putExtra("pass", "user");
				startActivity(a);

				finish();
			} else {
				if (Y <= textplace.y - 1 * (fontssize + 5)
						&& Y >= textplace.y - 3 * (fontssize + 5)
						&& X >= textplace.x + 10
						&& X <= textplace.x + 10 + (fontssize * 22)

				) {
					checkwin();
					myView.invalidate();
				} else {
					for (int i = 0; i < placementscount; i++) {
						// check all the bounds of the ball
						if (Math.abs(X - (placement[i].x + (9 * hop))) < 20 * hop
								&& Math.abs(Y - (placement[i].y + (9 * hop))) < 20 * hop) {
							if (Math.abs(placement[i].x - basketplace.x) < 20 * hop
									&& Math.abs(placement[i].y - basketplace.y) < 20 * hop) {

								minusplacementscount--;
								resultplacementscount++;
								askedresultplacementscount--;

							}
							balID = i;
							break;
						}
					}
				}
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger
			if (balID != -1) {
				if (Math.abs(X - (placement[balID].x + (9 * hop))) < 20 * hop
						&& Math.abs(Y - (placement[balID].y + (9 * hop))) < 20 * hop) {
					placement[balID] = new Point(X, Y);
					myView.invalidate();
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			// touch drop - just do things here after dropping
			if (balID != -1) {
				if (Math.abs(basketplace.x - (placement[balID].x + (12 * hop))) < 23 * hop
						&& Math.abs(basketplace.y
								- (placement[balID].y + (12 * hop))) < 23 * hop) {
					placement[balID] = new Point(basketplace.x, basketplace.y);
					minusplacementscount++;
					resultplacementscount--;
					askedresultplacementscount++;
				}
				balID = -1;
			}
			myView.invalidate();
			break;
		}
		// redraw the canvas

		return true;

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
			basketplace = new Point(halfres.x + (15 * hop), halfres.y);
		} else {
			placeme = new Point(halfres.x, maxres.y);

			textplace = new Point(halfres.x, halfres.y);
			hop = (int) ((double) halfres.y / 60);
			basketplace = new Point(halfres.x, halfres.y + (12 * hop));
		}
		if (maxres.x < 300 || maxres.y < 300) {
			Toast.makeText(getApplicationContext(),
					"Abord. We need more pixels.", Toast.LENGTH_LONG).show();
			placeme = new Point(100, 100);
		}
		for (int i = 0; i < placementscount; i++) {
			placement[i] = new Point(
					(int) ((Math.random() * (placeme.x - 100)) + 51),
					(int) ((Math.random() * (placeme.y - 100)) + 51));
		}
		
		group = Groups[new Random().nextInt(Groups.length)];
	//	group = Groups[1];
		drawchoice = getDrawable(this,group[0]);
		drawchoice2 = getDrawable(this,group[1]);
 		drawbackround = getDrawable(this,
  				(backrounds[new Random().nextInt(backrounds.length)]));
	//	drawbackround = getDrawable(this,backrounds[0]);
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

		private Bitmap myBitmap, basketBitmap, backround;
		private Paint paint = new Paint();

		public MyFrame(Context context) {
			super(context);
			myBitmap = BitmapFactory.decodeResource(getResources(), drawchoice);
			myBitmap = Bitmap.createScaledBitmap(myBitmap, 18 * hop, 18 * hop,
					true);
			basketBitmap = BitmapFactory.decodeResource(getResources(),
					drawchoice2);
			basketBitmap = Bitmap.createScaledBitmap(basketBitmap, 26 * hop,
					26 * hop, true);
			backround = BitmapFactory.decodeResource(getResources(),
					drawbackround);
			backround = Bitmap.createScaledBitmap(backround, maxres.x,
					maxres.y, true);

			// TODO Auto-generated constructor stub
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
				canvas.drawBitmap(backround, 1, 1, null);
				canvas.drawBitmap(basketBitmap, basketplace.x, basketplace.y,
						null);
				for (int i = 0; i < placementscount; i++) {
					canvas.drawBitmap(myBitmap, placement[i].x, placement[i].y,
							null);
				}
				paint.setColor(Color.WHITE);
				paint.setTextSize(fontssize);
				int a = (placementscount - askedresultplacementscount);
				canvas.drawText(getResources()
						.getString(R.string.moveitmessage), textplace.x + 10,
						textplace.y - 6 * (fontssize + 5), paint);
				String name;
				if (a<2){
					name = group[2];
				}else {name = group[3];}
				
				canvas.drawText(a + name + group[4],
						textplace.x + 10, textplace.y - 5 * (fontssize + 5),
						paint);
				canvas.drawText(placementscount + "  -  "
						+ minusplacementscount + "  =   "
						+ resultplacementscount, textplace.x + 10, textplace.y
						- 3 * (fontssize + 5), paint);
				canvas.drawText(getResources()
						.getString(R.string.movetocheck), textplace.x + 10,
						textplace.y - 2 * (fontssize + 5), paint);
				paint.setColor(Color.RED);
				canvas.drawText(getResources()
						.getString(R.string.hittocheck), textplace.x + 10,
						textplace.y - 1 * (fontssize + 5), paint);
			}
		}
	}
}
