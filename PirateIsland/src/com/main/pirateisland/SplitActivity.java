package com.main.pirateisland;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.graphics.Point;

public class SplitActivity extends Activity {

	//----- activity params  ------
	// Perigrafh antikeimenwn

	private int placementscount;
	private Point placement[];

	public SplitActivity() {
        //run once at activity start 

		
		
		

		placementscount = (int) ((Math.random() * 10) + 1);
		placement = new Point[placementscount];
		float w, h, cx, cy;
		w = 110; //getWidth();
		h = 110; //getHeight();
		cx = w / 2;
		cy = h / 2;

		for (int i = 0; i < placementscount; i++) {
			placement[i] = new Point((int) ((Math.random() * cx) + 1),
					(int) ((Math.random() * h) + 1));
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyFrame(this));
	}

	public class MyFrame extends View {
		private Bitmap myBitmap;
		public MyFrame(Context context) {
			super(context);
			myBitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.baby);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onDraw(Canvas canvas) {

			for (int i = 0; i < placementscount; i++) {
				canvas.drawBitmap(myBitmap, placement[i].x, placement[i].y,
						null);
			}

			// TODO Auto-generated method stub
			// super.onDraw(canvas);
			// int x = getWidth();
			// int y = getHeight();
			// int radius;
			// radius = 100;
			// Paint paint = new Paint();
			// paint.setStyle(Paint.Style.FILL);
			// paint.setColor(Color.WHITE);
			// canvas.drawPaint(paint);
			// Use Color.parseColor to define HTML colors
			// paint.setColor(Color.parseColor("#CD5C5C"));
			// canvas.drawCircle(x / 2, y / 2, radius, paint);
		}
	}
}
