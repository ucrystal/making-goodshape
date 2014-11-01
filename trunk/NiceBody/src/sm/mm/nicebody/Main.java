package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class Main extends Activity {

	ImageView logoImageview;
	Button free_btn, recommend_btn,schedule_btn,profile_btn;
	Toast mainToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		free_btn = (Button)findViewById(R.id.free_btn);
		free_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {

	        	 
	        	 if(Profile_modify.Height == null && Profile_modify.Weight == null){
	        		 
		        	 
		        	 Profile_modify.Height = "168";
		        	 Profile_modify.Weight = "50";
		     			
	        		 /* 
		        	 //일단 편의상 열어논다 
	        		 	mainToast = Toast.makeText(getApplicationContext(), "프로필을 입력해주세용", Toast.LENGTH_LONG); 
	        		 	mainToast.show();
		     			return;
		     		*/
	     		}

	        	 
	            Intent intent = new Intent(Main.this, Free_menu.class);
	            startActivity(intent);
	         }
	      });   
		
		recommend_btn = (Button)findViewById(R.id.recommend_btn);
		recommend_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	        	 
	        	 

	        	 if(Profile_modify.Height == null && Profile_modify.Weight == null){
	        	 
	        	 		Profile_modify.Height = "168";
		        	 	Profile_modify.Weight = "50";
		     			
			        	 /*
			        	  
			        	 //일단 편의상 열어논다 
	        		 	mainToast = Toast.makeText(getApplicationContext(), "프로필을 입력해주세용", Toast.LENGTH_LONG); 
	        		 	mainToast.show();
		     			return;
		     			
		     			*/
	     		}

	        	 
	            Intent intent = new Intent(Main.this, Recommend_list.class);
	            startActivity(intent);
	         }
	      });   
		
		schedule_btn = (Button)findViewById(R.id.schedule_btn);
		schedule_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Main.this, Schedule_calendar.class);
	            startActivity(intent);
	         }
	      });   
		
		profile_btn = (Button)findViewById(R.id.profile_btn);
		profile_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Main.this, Profile.class);
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
