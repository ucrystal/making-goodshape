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
import android.widget.TextView;

public class Recommend_success extends Activity {
	Button btn_backtomain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Recommend_list.choiceCh == 1) {
			setContentView(R.layout.recommend_success1);
		} else if (Recommend_list.choiceCh == 2) {
			setContentView(R.layout.recommend_success2);
		} else if (Recommend_list.choiceCh == 3) {
			setContentView(R.layout.recommend_success3);
		} else if (Recommend_list.choiceCh == 4) {
			setContentView(R.layout.recommend_success4);
		} else if (Recommend_list.choiceCh == 5) {
			setContentView(R.layout.recommend_success5);
		} else if (Recommend_list.choiceCh == 6) {
			setContentView(R.layout.recommend_success6);
		}

		customActionBar();
		
		int i = Recommend_list.choiceCh + 1;
		List<RecommendData> Recommend_result = Profile.db
				.getRecommendDatasById(i);
		
		if (Recommend_result.size() == 0) {
			RecommendData recommendData = new RecommendData(1);
			Profile.db.openNext(recommendData);
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
			break;
		case R.id.action_profile:
			intent = new Intent(this, Profile.class);
			startActivity(intent);
			break;

		case R.id.action_schedule:
			intent = new Intent(this, Schedule_calendar.class);
			startActivity(intent);
			break;

		case R.id.action_settings:
			intent = new Intent(this, Main.class);
			startActivity(intent);
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
}
