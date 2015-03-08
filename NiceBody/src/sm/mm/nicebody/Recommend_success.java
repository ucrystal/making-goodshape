package sm.mm.nicebody;

import java.util.List;

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
import android.widget.ImageView;
import android.widget.TextView;



public class Recommend_success extends Activity {
	Button btn_backtomain;
	ImageView trophy;
	Sound mSound;
	TextView text_cal;
	double cal;
	
	private BackPressCloseHandler backPressCloseHandler;
	Integer[] icon_trophy = { 
			R.drawable.icon_trophy1, 
			R.drawable.icon_trophy2, 
			R.drawable.icon_trophy3,
			R.drawable.icon_trophy4, 
			R.drawable.icon_trophy5, 
			R.drawable.icon_trophy6,
			R.drawable.icon_trophy7,
			R.drawable.icon_trophy8,
			R.drawable.icon_trophy9,
			R.drawable.icon_trophy10};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_success1);
		
		trophy = (ImageView)findViewById(R.id.imageView1);
		text_cal = (TextView) findViewById(R.id.text_cal);
		
		
		if(Recommend_list.choiceCh == 1){
			cal = Calorie.cal_neeling(2);
			Calorie.cal_fushUp(3);
			Calorie.cal_legRaise(6);
		}
		
		
		for(int i=1; i<11; i++){
			if (Recommend_list.choiceCh == i) {
				
				trophy.setImageResource(icon_trophy[i-1]);
				
				cal = Calorie.cal_fushUp(Recommend_record.arr[i-1][0]) 
						+ Calorie.cal_neeling(Recommend_record.arr[i-1][1]) 
						+ Calorie.cal_legRaise(Recommend_record.arr[i-1][2] + Recommend_record.arr[i-1][3]);
				
				text_cal.setText("총 "+ (int) cal + "cal를 소모하셨습니다.");
				
			}
		}
		
		
		mSound = new Sound(this, R.raw.success);
		if(Free_record.sound_ch%2 == 0){
			mSound.play();
		}
		
		
		customActionBar();

		backPressCloseHandler = new BackPressCloseHandler(this);

		int i = Recommend_list.choiceCh + 1;
		List<RecommendData> Recommend_result = Profile.db
				.getRecommendDatasById(i);
		
		if (Recommend_result.size() == 0) {
			RecommendData recommendData = new RecommendData(1);
			Profile.db.openNext(recommendData);
			
			List<RecommendData> Recommend_results = Profile.db.getAllRecommendDatas();
			Log.v("Test", "리스트 사이즈 : "+Recommend_results.size());
		}
		
		Log.v("Test", "추천운동성공여부는"+Recommend_result.size());

		btn_backtomain = (Button) findViewById(R.id.recommend_backtomain_btn);

		btn_backtomain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Recommend_success.this, Main.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
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
		textviewTitle.setText(R.string.title_activity_recommend_success);
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
