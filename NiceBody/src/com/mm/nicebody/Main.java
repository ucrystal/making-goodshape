package com.mm.nicebody;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class Main extends Activity {

	ImageView logoImageview;
	Button free_btn, recommend_btn,schedule_btn,profile_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		  
	
		free_btn = (Button)findViewById(R.id.free_btn);
		free_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Main.this, Free_menu.class);
	            startActivity(intent);
	         }
	      });   
		
		recommend_btn = (Button)findViewById(R.id.recommend_btn);
		recommend_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Main.this, Free_menu.class);
	            startActivity(intent);
	         }
	      });   
		
		schedule_btn = (Button)findViewById(R.id.schedule_btn);
		schedule_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Main.this, Free_menu.class);
	            startActivity(intent);
	         }
	      });   
		
		profile_btn = (Button)findViewById(R.id.profile_btn);
		profile_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Main.this, Free_menu.class);
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
