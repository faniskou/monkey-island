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
	int MyException;
	final int  random1 = (int) ((Math.random() * 10 ) + 1);
	final int  random2 = (int) ((Math.random() * 10 ) + 1);
	final int  result = random1 +random2;
	final int A = result - 3;
    final int B = result - 1;
	final int C = result + 1;
	
	logindatabaseadapter loginDataBaseAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise1);
		
		//to Onoma tou xristi einai stin "name"
	
         TextView textView1 = (TextView) findViewById(R.id.textView1);
	     textView1.setText(String.valueOf(random1)); 
	     
	     TextView textView3 = (TextView) findViewById(R.id.textView3);
	     textView3.setText(String.valueOf(random2));
    	 
	   
	     Button button2 = (Button) findViewById(R.id.button2);
	     button2.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		
	    		EditText editText1 = (EditText) findViewById(R.id.editText1);
	    		
	    		int answer = Integer.parseInt(editText1.getText().toString());
	    		
	    		if (errorflag == 3){
	    			check3(answer);
	    		}
	    		if (errorflag == 2){
	    			check2(answer);
	    		}
	    		if (errorflag == 1) {
	    			check1(answer);
			    }
	    		if (errorflag == 0) {
	    			check0(answer);	    		}
			    }
		}); 	
	}
	
	
	public void check0(int answer) {
		
		if (answer == result){
			Toast toast = Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT);
			toast.show();
		}
		if (answer > result){
			Toast toast = Toast.makeText(getApplicationContext(), "�����, �������� ��� ���� ����� �������.", Toast.LENGTH_SHORT);
    			toast.show();
	    	 errorflag = 1;
	     }
		if (answer < result){
			Toast toast = Toast.makeText(getApplicationContext(), "�����, �������� ��� ���� ������ �������.", Toast.LENGTH_SHORT);
    			toast.show();
        	 errorflag = 1;
	    }
     }

	public void check1 (int answer){
		if (answer == result){
			 Toast toast = Toast.makeText(getApplicationContext(), "�����!", Toast.LENGTH_SHORT);
   			toast.show();
	    	 }
		
		else {
			 Toast toast = Toast.makeText(getApplicationContext(), "�����. ������� ���� ��� ���� 4 ��������", Toast.LENGTH_SHORT);
   			toast.show();
   
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
	
	public void check2(int answer){
		
		if (answer == result){
			
			Toast toast = Toast.makeText(getApplicationContext(), "�����!", Toast.LENGTH_SHORT);
			toast.show();
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
			
			
			Toast toast = Toast.makeText(getApplicationContext(), "��� ������ �� ��������!", Toast.LENGTH_SHORT);
			toast.show();
	    	 errorflag = 3;
		}		
	}
	
	public void check3(int answer){
		if (answer == result){
	    	
			Toast toast = Toast.makeText(getApplicationContext(), "�����!", Toast.LENGTH_SHORT);
			toast.show(); 
		}
		else {
            
			Toast toast = Toast.makeText(getApplicationContext(), "��� ������ �� ��������!", Toast.LENGTH_SHORT);
			toast.show();
		}	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exercise1, menu);
		return true;
	}

}
