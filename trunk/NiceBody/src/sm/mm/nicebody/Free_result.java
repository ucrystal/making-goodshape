package sm.mm.nicebody;

import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Free_result extends Activity {

	Button result_pushup_gomain;
	Button result_record;

	TextView free_resultNum;
	TextView free_resultTimer;
	TextView free_resultCal;
	double calCalorie;

	Toast recordToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_result);

		customActionBar();

		free_resultNum = (TextView) findViewById(R.id.resultNum);
		free_resultTimer = (TextView) findViewById(R.id.resultTimer);
		free_resultCal = (TextView) findViewById(R.id.resultCal);

		if (Free_record.countResult < 10)
			free_resultNum.setText("0" + Free_record.countResult);
		else
			free_resultNum.setText("" + Free_record.countResult);

		free_resultTimer.setText(Free_record.timerResult + " 소요");

		if (Free_menu.choiceEx == 1) {

			calCalorie = Calorie.cal_fushUp(Free_record.countResult);
			free_resultCal.setText((int) calCalorie + " 칼로리 소모");

		} else if (Free_menu.choiceEx == 2) {

			calCalorie = Calorie.cal_lunge(Free_record.countResult);
			free_resultCal.setText((int) calCalorie + " 칼로리 소모");

		} else if (Free_menu.choiceEx == 3) {

			calCalorie = Calorie.cal_legRaise(Free_record.countResult);
			free_resultCal.setText((int) calCalorie + " 칼로리 소모");

		}

		result_record = (Button) findViewById(R.id.result_record);
		result_record.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Profile.db.addFreeData(new FreeData(Free_menu.choiceEx,
						Free_record.countResult));
				List<FreeData> printAll = Profile.db.getAllFreeDatas();
				for (int i = 0; i < printAll.size(); i++) {
					String log_s = "";
					if (printAll.get(i).getType() == 1) {
						log_s = "윗몸일으키기";
					}else if (printAll.get(i).getType() == 2) {
						log_s = "런지";
					}else if (printAll.get(i).getType() == 3) {
						log_s = "레그레이즈";
					}
					
					Log.v("free", log_s +","+ printAll.get(i).getCount() +","+ printAll.get(i).getDate());
				}

				recordToast = Toast.makeText(getApplicationContext(),
						"기록되었습니다!!!!!!!!!", Toast.LENGTH_LONG);
				recordToast.show();

			}
		});

		result_pushup_gomain = (Button) findViewById(R.id.result_gomain);
		result_pushup_gomain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Free_result.this, Main.class);
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
		textviewTitle.setText(R.string.title_activity_free_success);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}

}
