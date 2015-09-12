package com.main.pirateisland;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.graphics.Point;

public class SplitActivity extends Activity {

	// ----- activity params ------
	// Perigrafh antikeimenwn

	private int placementscount;
	private int drawchoice;
	private int balID = -1;
	private Point placement[];
	private Point maxres, halfres;

	MyFrame myView;

	public SplitActivity() {
		placementscount = (int) ((Math.random() * 10) + 1);
		placement = new Point[placementscount];
		drawchoice = R.drawable.baby;
	}
	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction();

		int X = (int) event.getX();
		int Y = (int) event.getY();

		switch (eventaction) {

		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on
										// a ball
			for (int i = 0; i < placementscount; i++) {
				// check all the bounds of the ball
				if (X > placement[i].x && X < placement[i].x + 50
						&& Y > placement[i].y && Y < placement[i].y + 50) {
					balID = i;
					break;
				}
			}

			break;

		case MotionEvent.ACTION_MOVE: // touch drag with the ball
			// move the balls the same as the finger
			if (balID != -1) {
				if (placement[balID].x - X > 50 || placement[balID].x + X > 50
						|| placement[balID].y - Y > 50
						|| placement[balID].y + Y > 50) {
					placement[balID] = new Point(X, Y);
					myView.invalidate();
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			// touch drop - just do things here after dropping
			balID = -1;
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

		halfres = new Point(maxres.x / 2, maxres.y / 2);
		Point placeme;
		int orientation = this.getResources().getConfiguration().orientation;
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			placeme = new Point(maxres.x, halfres.y);
		} else {
			placeme = new Point(halfres.x, maxres.y);
		}
		if (maxres.x < 110 || maxres.y < 110) {
			Toast.makeText(getApplicationContext(),
					"Abord. We need more pixels.", Toast.LENGTH_LONG).show();
			placeme = new Point(100, 100);
		}
		for (int i = 0; i < placementscount; i++) {
			placement[i] = new Point(
					(int) ((Math.random() * (placeme.x - 100)) + 51),
					(int) ((Math.random() * (placeme.y - 100)) + 51));
		}
	}
    // draw screen
	public class MyFrame extends View {
		private Bitmap myBitmap;

		public MyFrame(Context context) {
			super(context);
			myBitmap = BitmapFactory.decodeResource(getResources(), drawchoice);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onDraw(Canvas canvas) {
			for (int i = 0; i < placementscount; i++) {
				canvas.drawBitmap(myBitmap, placement[i].x, placement[i].y,
						null);
			}
			
		}
	}
}
