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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class Time_record extends Activity {

	Button time_finish;
	CheckBox moncb[] = new CheckBox[8];
	CheckBox tuecb[] = new CheckBox[8];
	CheckBox wedcb[] = new CheckBox[8];
	CheckBox thucb[] = new CheckBox[8];
	CheckBox fricb[] = new CheckBox[8];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_record);
		
		moncb[0] = (CheckBox) findViewById(R.id.mon_rb1);
		moncb[1] = (CheckBox) findViewById(R.id.mon_rb2);
		moncb[2] = (CheckBox) findViewById(R.id.mon_rb3);
		moncb[3] = (CheckBox) findViewById(R.id.mon_rb4);
		moncb[4] = (CheckBox) findViewById(R.id.mon_rb5);
		moncb[5] = (CheckBox) findViewById(R.id.mon_rb6);
		moncb[6] = (CheckBox) findViewById(R.id.mon_rb7);
		moncb[7] = (CheckBox) findViewById(R.id.mon_rb8);
		
		tuecb[0] = (CheckBox) findViewById(R.id.tue_rb1);
		tuecb[1] = (CheckBox) findViewById(R.id.tue_rb2);
		tuecb[2] = (CheckBox) findViewById(R.id.tue_rb3);
		tuecb[3] = (CheckBox) findViewById(R.id.tue_rb4);
		tuecb[4] = (CheckBox) findViewById(R.id.tue_rb5);
		tuecb[5] = (CheckBox) findViewById(R.id.tue_rb6);
		tuecb[6] = (CheckBox) findViewById(R.id.tue_rb7);
		tuecb[7] = (CheckBox) findViewById(R.id.tue_rb8);
		
		wedcb[0] = (CheckBox) findViewById(R.id.wed_rb1);
		wedcb[1] = (CheckBox) findViewById(R.id.wed_rb2);
		wedcb[2] = (CheckBox) findViewById(R.id.wed_rb3);
		wedcb[3] = (CheckBox) findViewById(R.id.wed_rb4);
		wedcb[4] = (CheckBox) findViewById(R.id.wed_rb5);
		wedcb[5] = (CheckBox) findViewById(R.id.wed_rb6);
		wedcb[6] = (CheckBox) findViewById(R.id.wed_rb7);
		wedcb[7] = (CheckBox) findViewById(R.id.wed_rb8);
		
		thucb[0] = (CheckBox) findViewById(R.id.thu_rb1);
		thucb[1] = (CheckBox) findViewById(R.id.thu_rb2);
		thucb[2] = (CheckBox) findViewById(R.id.thu_rb3);
		thucb[3] = (CheckBox) findViewById(R.id.thu_rb4);
		thucb[4] = (CheckBox) findViewById(R.id.thu_rb5);
		thucb[5] = (CheckBox) findViewById(R.id.thu_rb6);
		thucb[6] = (CheckBox) findViewById(R.id.thu_rb7);
		thucb[7] = (CheckBox) findViewById(R.id.thu_rb8);

		fricb[0] = (CheckBox) findViewById(R.id.fri_rb1);
		fricb[1] = (CheckBox) findViewById(R.id.fri_rb2);
		fricb[2] = (CheckBox) findViewById(R.id.fri_rb3);
		fricb[3] = (CheckBox) findViewById(R.id.fri_rb4);
		fricb[4] = (CheckBox) findViewById(R.id.fri_rb5);
		fricb[5] = (CheckBox) findViewById(R.id.fri_rb6);
		fricb[6] = (CheckBox) findViewById(R.id.fri_rb7);
		fricb[7] = (CheckBox) findViewById(R.id.fri_rb8);
		
		
		time_finish = (Button) findViewById(R.id.time_finish);
		time_finish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Time_record.this, Main.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
				finish();
			}
		});
	

	}
}

