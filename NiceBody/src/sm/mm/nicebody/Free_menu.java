package sm.mm.nicebody;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Free_menu extends Activity {

	static int choiceEx = 0; 
	Button free_pushup_btn, free_lunge_btn,free_legraise_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_menu);
		
		free_pushup_btn = (Button)findViewById(R.id.free_pushup_btn);
		free_pushup_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            
	        	choiceEx = 1;
	        	
	        	Intent intent = new Intent(Free_menu.this, Free_explain.class);
	            startActivity(intent);
	            
	         }
	      });   
		free_lunge_btn = (Button)findViewById(R.id.free_lunge_btn);
		free_lunge_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	        	 
	        	choiceEx = 2;
	        	 
	        	Intent intent = new Intent(Free_menu.this, Free_explain.class);
	            startActivity(intent);
	         }
	      });
		free_legraise_btn = (Button)findViewById(R.id.free_legraise_btn);
		free_legraise_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	           
	        	choiceEx = 3;
	        	 
	        	Intent intent = new Intent(Free_menu.this, Free_explain.class);
	            startActivity(intent);
	         }
	      });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
