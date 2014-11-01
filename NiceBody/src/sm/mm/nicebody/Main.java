package sm.mm.nicebody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
