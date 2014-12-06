package sm.mm.schedule_manager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Find_plus extends Activity {

	Button send_msg;
	TextView plus_name, plus_day, plus_time;

	Calendar c;
	int Year, Month, Date, dayOfWeek;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_plus);

		ParseObject testObject;

		testObject = new ParseObject("TestObject");
		testObject.put("foo", "bar");
		testObject.saveInBackground();

		plus_name = (TextView) findViewById(R.id.plus_name);
		plus_day = (TextView) findViewById(R.id.plus_day);
		plus_time = (TextView) findViewById(R.id.plus_time);

		plus_name.setText(Find_question.info_s[1]);
		
		plus_day.setText(Find_emptyTime.emptyDay);
		plus_time.setText(Find_emptyTime.emptyTime);

		Calendar c = Calendar.getInstance();

		Year = c.get(Calendar.YEAR);
		Month = c.get(Calendar.MONTH) + 1;
		Date = c.get(Calendar.DAY_OF_MONTH);
		dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		customActionBar();
		// name_result.setText(Find_list.info_s[1]);

		send_msg = (Button) findViewById(R.id.send_msg);
		send_msg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				ParseCloud.callFunctionInBackground("hello",
						new HashMap<String, Object>(),
						new FunctionCallback<String>() {
							public void done(String result, ParseException e) {
								if (e == null) {
									Log.v("parseTest", "result: <" + result
											+ ">");
								}
							}
						});

				List<ProfileData> ProfileDatas = new LinkedList<ProfileData>();
				ProfileDatas = Profile.db.getAllProfileDatas();
				int d_size = ProfileDatas.size() - 1;
				ProfileData profile_pd = ProfileDatas.get(d_size);

				String n = profile_pd.getName();
				String f = plus_name.getText().toString();
				String d = plus_day.getText().toString();
				String t = plus_time.getText().toString();
				
				Log.v("push date", "교시 값:" + t);

				String sche_s = "";
				Schedule_push sp = new Schedule_push(dayOfWeek, d);

				int plus_d = Date + sp.getDay();
				int meet_time = Integer.parseInt(t);


				String push_t = "";
				if (meet_time == 1) {
					plus_d = plus_d - 1;
					push_t = "23:50:00Z";
				} else if (meet_time == 2) {
					push_t = "00:50:00Z";
				} else if (meet_time == 3) {
					push_t = "01:50:00Z";
				} else if (meet_time == 4) {
					push_t = "02:50:00Z";
				} else if (meet_time == 5) {
					push_t = "03:50:00Z";
				} else if (meet_time == 6) {
					push_t = "04:50:00Z";
				} else if (meet_time == 7) {
					push_t = "05:50:00Z";
				} else if (meet_time == 8) {
					push_t = "06:50:00Z";
				}
				
				Log.v("push date", "시간 값:" + push_t);

				// 월과 년도가 다음달로 넘어갔을 경우
				if (Month == 1 || Month == 3 || Month == 5 || Month == 7
						|| Month == 8 || Month == 10) {
					if (plus_d > 31) {
						plus_d = plus_d - 31;
						Month = Month + 1;
					}
				} else if (Month == 2) {

					if (plus_d > 28) {
						plus_d = plus_d - 28;
						Month = Month + 1;
					}

				} else if (Month == 4 || Month == 6 || Month == 9
						|| Month == 11) {
					if (plus_d > 31) {
						plus_d = plus_d - 30;
						Month = Month + 1;
					}

				} else if (Month == 12) {

					if (plus_d > 31) {
						plus_d = plus_d - 31;
						Month = Month + 1;
						Year = Year + 1;
					}
				}
				
				Log.v("push date", "date값:" + Date);
				Log.v("push date", "plus_d값:" + plus_d);

				// 스트링에 0더하는거
				if (plus_d < 10 && Month < 10) {
					sche_s = Year + "-0" + Month + "-0" + plus_d + "T" + push_t;
				}else if(plus_d < 10 && Month >= 10) {
					sche_s = Year + "-" + Month + "-0" + plus_d + "T" + push_t;
				}else if (plus_d >= 10 && Month < 10){
					sche_s = Year + "-0" + Month + "-" + plus_d + "T" + push_t;
				}else if (plus_d >= 10 && Month >= 10){
					sche_s = Year + "-" + Month + "-" + plus_d + "T" + push_t;
				}
					

				Log.v("push date", "날짜 입력 값:" + sche_s);

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("date", sche_s);
				// 12월 3일 22시 10분 - 12월 3일 13시 10분
				// 12월 4일 7시 10분 - 12월 3일 22시 10분

				ParseCloud.callFunctionInBackground("testPush", params,
						new FunctionCallback<String>() {
							public void done(String result, ParseException e) {
								if (e == null) {
									Log.v("parseTest", "sms result: <" + result
											+ ">");
								} else {
									Log.v("parseTest", "sms result: <" + result
											+ ">");
								}
							}
						});


				String msg_s = n + "님이 " + d + "요일 " + t + "교시에 " + f
						+ "님과 약속을 잡았습니다.";

				// HashMap<String, Object> params = new HashMap<String,
				// Object>();
				params.put("targetPhoneNumber", "821096627226");
				params.put("msg", msg_s);
				ParseCloud.callFunctionInBackground("testSms", params,
						new FunctionCallback<String>() {
							public void done(String result, ParseException e) {
								if (e == null) {
									Log.v("parseTest", "sms result: <" + result
											+ ">");
								}
							}
						});


				Intent intent = new Intent(Find_plus.this, Main.class);
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
		getMenuInflater().inflate(R.menu.developer, menu);
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
		textviewTitle.setText(R.string.title_activity_find);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}

}
