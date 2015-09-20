package com.main.pirateisland;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Exercise1 extends Activity {
	
	int errorflag = 0; 
	int A;
	int MyException;
	//String aaa;
	logindatabaseadapter loginDataBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise1);
		
		
		//monimes metavlites, den allazoun
		//gia allagi me to patioma enos koumpiou, orizoume mesa sta koumpia k oxi final
		
		
		//aaa =(TextView)findViewById(R.id.textview11);
		//Intent i= getIntent();
		//aaa.setText(i.getStringExtra("name"));
	    
		//to Onoma tou xristi einai stin "name"
		
		final int  random1 = (int) ((Math.random() * 10 ) + 1);
		final int  random2 = (int) ((Math.random() * 10 ) + 1);
		
		final int  result = random1 +random2;
		
		 final int A = result - 3;
         final int B = result - 1;
    	 final int C = result + 1;
		
    	 TextView textView1 = (TextView) findViewById(R.id.textView1);
	     textView1.setText(String.valueOf(random1)); 
	     
	     TextView textView3 = (TextView) findViewById(R.id.textView3);
	     textView3.setText(String.valueOf(random2));
	     
	     TextView textView4 = (TextView) findViewById(R.id.textView4);
	     textView4.setText("+");
	     
	     TextView textView5 = (TextView) findViewById(R.id.textView5);
	     textView5.setText("=");
    	 
	     //loginDataBaseAdapter=new logindatabaseadapter(this);
			//loginDataBaseAdapter=loginDataBaseAdapter.open();
    	 
		
	     
	     Button button2 = (Button) findViewById(R.id.button2);
	     button2.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		
	    		EditText editText1 = (EditText) findViewById(R.id.editText1);
	    		
	    	   //int  result = random1 +random2;
	    		
	    		int answer = Integer.parseInt(editText1.getText().toString());
	    		
	     
	    		//if (answer == result){
			    	 
			    	// TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 //textView6.setText("Σωστά!");
			     //}
	    		
	    		
	    		
	    		
                if (errorflag == 3){
	    			
	    			if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("Σωστά!!!!");	
				    	 
				    	// loginDataBaseAdapter.updateEX(errorflag);
						// Toast.makeText(getApplicationContext(), "Success ", Toast.LENGTH_LONG).show();

				    	 
				    	 
	    			}
	    			else {
	                     TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("Για σκέψου το καλύτερα!");
	    			}
	    			
	    			
	    		}
	    		
	    		if (errorflag == 2){
	    			
	    			if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("Σωστά!!!!");	
				    	 
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
	    				
	    				TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("Για σκέψου το καλύτερα!");
				    	 errorflag = 3;
	    			}
	    			
	    			
	    		}
	    		
	    		
	    		if (errorflag == 1) {
	    			
	    			if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("Σωστά!!!!");
				    	 }
	    			
	    			else {
	    			 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("Λάθος. Επέλεξε έναν από τους 4 αριθμούς");
		           
			    	 
			    	 //int A = result - 3;
			         //int B = result - 1;
			    	 //int C = result + 1;
			    
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
	    		
	    		
	    		
	    		
	    		if (errorflag == 0) {
	    			
	    			
	    		if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("Σωστά!");
	    		}
			     
	             if (answer > result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("Λαθος, δοκίμασε ένα ποιό μικρό νούμερο.");
			    	 errorflag = 1;
			     }
	             
	             if (answer < result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("Λάθος, δοκίμασε ένα ποιό μεγάλο νούμερο.");
			    	 errorflag = 1;
			    	 
			     }
	    		}
	    		
	    		
				
			}
		}); 
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise1, menu);
		return true;
	}

}
