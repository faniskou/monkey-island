package com.main.pirateisland;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class Exercise1 extends Activity {
	
	int errorflag = 0; 
	int A;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise1);
		
		
		//monimes metavlites, den allazoun
		//gia allagi me to patioma enos koumpiou, orizoume mesa sta koumpia k oxi final
		
		final int  random1 = (int) ((Math.random() * 10 ) + 1);
		final int  random2 = (int) ((Math.random() * 10 ) + 1);
		
		final int  result = random1 +random2;
		
		 final int A = result - 3;
         final int B = result - 1;
    	 final int C = result + 1;
		
		 Button button1 = (Button) findViewById(R.id.button1);
	     button1.setOnClickListener(new OnClickListener() {
			
	    	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
	    		 
	    		//int  random1 = (int) ((Math.random() * 10 ) + 1);
	    		//int  random2 = (int) ((Math.random() * 10 ) + 1);
	    		
				TextView textView1 = (TextView) findViewById(R.id.textView1);
			     textView1.setText(String.valueOf(random1)); 
			     
			     TextView textView3 = (TextView) findViewById(R.id.textView3);
			     textView3.setText(String.valueOf(random2));
				
			}
		});
	     
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
			    	 //textView6.setText("�����!");
			     //}
	    		
	    		if (errorflag == 2){
	    			
	    			if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("�����!!!!");	
				    	 
	    			}
	    			
	    			
	    		}
	    		
	    		
	    		if (errorflag == 1) {
	    			
	    			if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("�����!!!!");
				    	 }
	    			
	    			else {
	    			 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("��� �������� ����. ���� �� ���� ������� ���� ��� ���� 4 ��������");
		           
			    	 
			    	 //int A = result - 3;
			         //int B = result - 1;
			    	 //int C = result + 1;
			    
			    	 TextView textView7 = (TextView) findViewById(R.id.textView7);
			    	 textView7.setText(String.valueOf(A));
			    	 
			    	 TextView textView8 = (TextView) findViewById(R.id.textView8);
			    	 textView8.setText(String.valueOf(B));
			    	 
			    	 TextView textView9 = (TextView) findViewById(R.id.textView9);
			         textView9.setText(String.valueOf(result));
			    	 
			    	 TextView textView10 = (TextView) findViewById(R.id.textView10);
			    	 textView10.setText(String.valueOf(C));
			    	 
			    	 errorflag = 2;
	    			}
			    	 
                    
	    		
			    	 
		    		}
	    		
	    		
	    		
	    		
	    		if (errorflag == 0) {
	    			
	    			
	    		if (answer == result){
				    	 
				    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
				    	 textView6.setText("�����!");
	    		}
			     
	             if (answer > result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("������, ������ �����. �������� ��� ���� ����� �������.");
			    	 errorflag = 1;
			     }
	             
	             if (answer < result){
			    	 
			    	 TextView textView6 = (TextView) findViewById(R.id.textView6);
			    	 textView6.setText("������, ������ �����. �������� ��� ���� ������ �������.");
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
