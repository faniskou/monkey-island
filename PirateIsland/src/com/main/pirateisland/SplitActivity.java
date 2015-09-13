package com.main.pirateisland;

import android.app.Activity;
import android.content.Context;
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

	// ----- activity params ------
	// sensor describe
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	// Perigrafh antikeimenwn
	private int placementscount, minusplacementscount, resultplacementscount,
			askedresultplacementscount = 0;
	private int drawchoice, drawchoice2;
	private int balID = -1;
	private Point placement[];
	private Point maxres, halfres, basketplace, placeme, textplace;
	private int fontssize;
	private int gamestate = 0;
	private int hop= 50;

	MyFrame myView;

	public SplitActivity() {
		placementscount = (int) ((Math.random() * 10) + 1);
		askedresultplacementscount = (int) ((Math.random() * placementscount) + 1);
		resultplacementscount = placementscount;
		placement = new Point[placementscount];
		drawchoice = R.drawable.baby;
		drawchoice2 = R.drawable.basket;
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
			if (mAccel > 10) {
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
			if (Y > textplace.y - 1 * (fontssize + 5)) {
				checkwin();
				myView.invalidate();
			} else {
				for (int i = 0; i < placementscount; i++) {
					// check all the bounds of the ball
					if (X > placement[i].x - 20 && X < placement[i].x + 70
							&& Y > placement[i].y - 10
							&& Y < placement[i].y + 90) {
						if (basketplace.x > placement[i].x - 10
								&& basketplace.x < placement[i].x + 10
								&& basketplace.y > placement[i].y - 10
								&& basketplace.y < placement[i].y + 10) {

							minusplacementscount--;
							resultplacementscount++;
							askedresultplacementscount--;

						}
						balID = i;
						break;
					}
				}
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger
			if (balID != -1) {
				if (Math.abs(X - placement[balID].x) < 80
						&& Math.abs(Y - placement[balID].y) < 80) {
					placement[balID] = new Point(X, Y);
					myView.invalidate();
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			// touch drop - just do things here after dropping
			if (balID != -1) {
				if (Math.abs(basketplace.x - placement[balID].x) < 80
						&& Math.abs(basketplace.y - placement[balID].y) < 80) {
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
		myView = new MyFrame(this);
		setContentView(myView);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		maxres = new Point(displaymetrics.widthPixels,
				displaymetrics.heightPixels);
        hop  = 40 ; //
        
		halfres = new Point(maxres.x / 2, maxres.y / 2);
		int orientation = this.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			placeme = new Point(maxres.x, halfres.y);
			textplace = new Point(0, maxres.y);
			hop = (int) ((double)halfres.x / 20) ;
			basketplace = new Point(halfres.x + (5 * hop), halfres.y);
		} else {
			placeme = new Point(halfres.x, maxres.y);

			textplace = new Point(halfres.x, halfres.y);
			hop = (int) ((double)halfres.y / 20) ;
			basketplace = new Point(halfres.x, halfres.y + (4* hop));
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
		/* sensor initialize */
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

		private Bitmap myBitmap, basketBitmap;
		private Paint paint = new Paint();

		public MyFrame(Context context) {
			super(context);
			Bitmap bitmapSource = BitmapFactory.decodeResource(getResources(), drawchoice);
			myBitmap = Bitmap.createScaledBitmap(bitmapSource, 2*hop , 2*hop, true);
			bitmapSource = BitmapFactory.decodeResource(getResources(),	drawchoice2);
			basketBitmap = Bitmap.createScaledBitmap(bitmapSource, 3*hop , 3*hop, true);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onDraw(Canvas canvas) {
			fontssize = 2* hop;
			if (gamestate == 1) {
				paint.setColor(Color.LTGRAY);
				paint.setTextSize(fontssize);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y,
						paint);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y
						+ 1 * (fontssize + 5), paint);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y
						- 1 * (fontssize + 5), paint);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y
						+ 2 * (fontssize + 5), paint);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y
						- 2 * (fontssize + 5), paint);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y
						+ 3 * (fontssize + 5), paint);
				canvas.drawText("Συνχαρητήρια κερδίσατε !!! ", 10, halfres.y
						- 3 * (fontssize + 5), paint);

			} else {
				canvas.drawBitmap(basketBitmap, basketplace.x, basketplace.y,
						null);
				for (int i = 0; i < placementscount; i++) {
					canvas.drawBitmap(myBitmap, placement[i].x, placement[i].y,
							null);
				}
				paint.setColor(Color.LTGRAY);
				paint.setTextSize(fontssize);
				int a = (placementscount - askedresultplacementscount);
				canvas.drawText("Πρέπει να βάλουμε ", textplace.x + 10,
						textplace.y - 6 * (fontssize + 5), paint);
				canvas.drawText(a + " μωρά για υπνο. ", textplace.x + 10,
						textplace.y - 5 * (fontssize + 5), paint);
				canvas.drawText(placementscount + "  -  "
						+ minusplacementscount + "  =   "
						+ resultplacementscount, textplace.x + 10, textplace.y
						- 3 * (fontssize + 5), paint);
				canvas.drawText("Κουνήστε για επαλήθευση ", textplace.x + 10,
						textplace.y - 2 * (fontssize + 5), paint);
				paint.setColor(Color.RED);
				canvas.drawText("Πατήστε για επαλήθευση ", textplace.x + 10,
						textplace.y - 1 * (fontssize + 5), paint);
			}
		}
	}
}
