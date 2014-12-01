package sm.mm.nicebody;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Recommend_description1 extends Activity {
	Button recommend_btn1,recommend_btn2,recommend_btn3,recommend_btn4,recommend_btn5,recommend_btn6;
	
	private BackPressCloseHandler backPressCloseHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Recommend_list.choiceCh == 1){
			setContentView(R.layout.recommend_description1);}
		else if (Recommend_list.choiceCh == 2){
			setContentView(R.layout.recommend_description2);}
		else if (Recommend_list.choiceCh == 3){
			setContentView(R.layout.recommend_description3);}
		else if (Recommend_list.choiceCh == 4){
			setContentView(R.layout.recommend_description4);}
		else if (Recommend_list.choiceCh == 5){
			setContentView(R.layout.recommend_description5);}
		else if (Recommend_list.choiceCh == 6){
			setContentView(R.layout.recommend_description6);}
		
		customActionBar();

		backPressCloseHandler = new BackPressCloseHandler(this);
		
		recommend_btn1 = (Button)findViewById(R.id.recommendlist_btn1);
		//recommend_btn1.setBackgroundResource(R.drawable.btn_start);
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

		case R.id.action_settings:
			intent = new Intent(this, Main.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

	public void customActionBar() {
		// Customize the ActionBar
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		// abar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background));//line
		// under the action bar
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				// Center the textview in the ActionBar !
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_recommend_description1);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
	
	@Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
