package sm.mm.schedule_manager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Time_menu extends Activity {

	Button record_btn, check_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_menu);
		
		record_btn = (Button) findViewById(R.id.btn_record);
		record_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Time_menu.this, Time_record.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
				finish();
			}
		});
		check_btn = (Button) findViewById(R.id.btn_check);
		check_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				Intent intent = new Intent(Time_menu.this, Main.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
				finish();
			}
		});
		
	}


}
