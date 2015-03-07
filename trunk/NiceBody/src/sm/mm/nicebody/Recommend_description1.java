package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Recommend_description1 extends Activity {
	Button recommend_btn1,recommend_btn2,recommend_btn3,recommend_btn4,recommend_btn5,recommend_btn6;
	TextView description1, description2, description3;
	Recommend_list list;
	Integer[] description_second = { 
			R.string.ch1_2, 
			R.string.ch2_2, 
			R.string.ch3_2,
			R.string.ch4_2, 
			R.string.ch5_2, 
			R.string.ch6_2,
			R.string.ch7_2,
			R.string.ch8_2,
			R.string.ch9_2,
			R.string.ch10_2};
	
	Integer[] description_third = { 
			R.string.ch1_3, 
			R.string.ch2_3, 
			R.string.ch3_3,
			R.string.ch4_3, 
			R.string.ch5_3, 
			R.string.ch6_3,
			R.string.ch7_3,
			R.string.ch8_3,
			R.string.ch9_3,
			R.string.ch10_3};
	
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.recommend_description1);
		description1 = (TextView)findViewById(R.id.tv_ch1);
		description2 = (TextView)findViewById(R.id.tv_ch2);
		description3 = (TextView)findViewById(R.id.tv_ch3);
 
		for(int i=1; i<=10; i++) {
			if(Recommend_list.choiceCh==i) {
				int stage = i;
				description1.setText(stage+"단계 입니다.");
				description2.setText(description_second[i-1]);
				description3.setText(description_third[i-1]);
			}
		}
				
		customActionBar();

		backPressCloseHandler = new BackPressCloseHandler(this);
		
		recommend_btn1 = (Button)findViewById(R.id.recommendlist_btn1);
		recommend_btn1.setOnClickListener(new OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Recommend_description1.this, Recommend_record.class);
	            startActivity(intent);
	            overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
	            finish();
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
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, Main.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		case R.id.action_profile:
			intent = new Intent(this, Profile.class);
			startActivity(intent);
			finish();
			break;

		case R.id.action_schedule:
			intent = new Intent(this, Schedule_calendar.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

	public void customActionBar() {
		// Customize the ActionBar
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#67C6E5")));
		// under the action bar
		View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_recommend_description1);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		abar.setHomeButtonEnabled(true);
	}
	
	@Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
