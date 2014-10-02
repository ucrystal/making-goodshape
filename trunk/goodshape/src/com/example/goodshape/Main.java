package com.example.goodshape;
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
	Button free_btn, recommend_btn,record_btn,profile_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		  
		logoImageview = (ImageView)findViewById(R.id.imageView1);
		free_btn = (Button)findViewById(R.id.free_btn);
		recommend_btn = (Button)findViewById(R.id.recommend_btn);
		record_btn = (Button)findViewById(R.id.record_btn);
		profile_btn = (Button)findViewById(R.id.profile_btn);
		  
		free_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setContentView(R.layout.free_menu);
			}	
		});
		
		recommend_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setContentView(R.layout.free_menu);
			}	
		});
		
		record_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setContentView(R.layout.free_menu);
			}	
		});
		
		record_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				setContentView(R.layout.free_menu);
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
