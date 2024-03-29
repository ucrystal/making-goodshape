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
	private BackPressCloseHandler backPressCloseHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_result);

		customActionBar();
		backPressCloseHandler = new BackPressCloseHandler(this);

		free_resultNum = (TextView) findViewById(R.id.resultNum);
		free_resultTimer = (TextView) findViewById(R.id.resultTimer);
		free_resultCal = (TextView) findViewById(R.id.resultCal);

		if (Free_record.countResult < 10)
			free_resultNum.setText("0" + Free_record.countResult);
		else
			free_resultNum.setText("" + Free_record.countResult);

		free_resultTimer.setText(Free_record.timerResult + " 소요");

		if (Free_menu.choiceEx == 1) {

			calCalorie = Calorie.cal_pushUp(Free_record.countResult);
			free_resultCal.setText((int) calCalorie + " cal 소모");

		} else if (Free_menu.choiceEx == 2) {

			calCalorie = Calorie.cal_neeling(Free_record.countResult);
			free_resultCal.setText((int) calCalorie + " cal 소모");

		} else if (Free_menu.choiceEx == 3) {

			calCalorie = Calorie.cal_legRaise(Free_record.countResult);
			free_resultCal.setText((int) calCalorie + " cal 소모");

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
					} else if (printAll.get(i).getType() == 2) {
						log_s = "런지";
					} else if (printAll.get(i).getType() == 3) {
						log_s = "레그레이즈";
					}

					Log.v("free", log_s + "," + printAll.get(i).getCount()
							+ "," + printAll.get(i).getDate());
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

				Free_record.countResult = 0;
				Intent intent = new Intent(Free_result.this, Main.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_free_result);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		abar.setHomeButtonEnabled(true);
	}

	@Override
	public void onBackPressed() {
		backPressCloseHandler.onBackPressed();
	}
}
