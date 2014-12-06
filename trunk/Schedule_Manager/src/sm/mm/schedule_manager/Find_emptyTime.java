package sm.mm.schedule_manager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Find_emptyTime extends Activity {

	private TextView mondayCommon;
	private TextView tuesdayCommon;
	private TextView wednesdayCommon;
	private TextView thursdayCommon;
	private TextView fridayCommon;
	private EditText empty_et, empty_ed;

	private Button make_app;
	List<ProfileData> profileDatas;
	List<PromiseData> promiseDatas;
	private Toast parseToast;
	private TextView searchedName;
	static String emptyDay, emptyTime;

	private Handler mHandler;
	private ProgressDialog mProgressDialog;

	List<Boolean> dayarr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_emptytime);

		// 로딩
		mHandler = new Handler();
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgressDialog = ProgressDialog.show(Find_emptyTime.this, "",
						"잠시만 기다려 주세요.", true);
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							if (mProgressDialog != null
									&& mProgressDialog.isShowing()) {
								mProgressDialog.dismiss();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, 3000);
			}
		});
		customActionBar();

		dayarr = new ArrayList<Boolean>();
		
		profileDatas = new LinkedList<ProfileData>();
		profileDatas = Profile.db.getAllProfileDatas();

		promiseDatas = new LinkedList<PromiseData>();
		promiseDatas = Profile.db.getAllPromiseDatas();
		
		mondayCommon = (TextView) findViewById(R.id.mon_common);
		tuesdayCommon = (TextView) findViewById(R.id.tue_common);
		wednesdayCommon = (TextView) findViewById(R.id.wed_common);
		thursdayCommon = (TextView) findViewById(R.id.thu_common);
		fridayCommon = (TextView) findViewById(R.id.fri_common);

		make_app = (Button) findViewById(R.id.make_app);
		empty_ed = (EditText) findViewById(R.id.empty_editDay);
		empty_et = (EditText) findViewById(R.id.empty_editTime);

		compareUserSchedule("monday", Find.search_name);
		compareUserSchedule("tuesday", Find.search_name);
		compareUserSchedule("wednesday", Find.search_name);
		compareUserSchedule("thursday", Find.search_name);
		compareUserSchedule("friday", Find.search_name);

		make_app = (Button) findViewById(R.id.make_app);
		make_app.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				emptyDay = empty_ed.getText().toString();
				emptyTime = empty_et.getText().toString();

				PromiseData promiseData = new PromiseData(Find_question.info_s[1], emptyDay, emptyTime);
				//PromiseData promiseBefore = promiseDatas.get(0);
				
				if(promiseDatas.size() != 0) {
					//PromiseData promiseBefore = promiseDatas.get(0);
					for(int i=0; i<promiseDatas.size(); i++) {
						PromiseData promiseBefore;
						promiseBefore = promiseDatas.get(i);
						if(promiseBefore.getDay().toString().equals(promiseData.getDay().toString()) && promiseBefore.getTime().toString().equals(promiseData.getTime().toString())) {
							parseToast = Toast.makeText(getApplicationContext(), "같은 시간에 약속이 잡혀있습니다. 다시 시도하세요.",Toast.LENGTH_LONG);
							parseToast.show();
							return;	
						}
					}
				}
				/*dayarr = arrayOfCommonSchedule("monday", Find.search_name);
				int index = Integer.parseInt(PromiseData.getTime());
				Log.v("test",dayarr.get(index).toString());
				if(dayarr != null && dayarr.get(index-1) != false) {
					parseToast = Toast.makeText(getApplicationContext(), "공통공강시간이 아닙니다. 다시 시도하세요.",Toast.LENGTH_LONG);
					parseToast.show();
					return;	
				}*/
				Profile.db.addPromiseData(promiseData);
				
				
				Intent intent = new Intent(Find_emptyTime.this, Find_plus.class);
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

	void compareUserSchedule(final String dayName, String searchName) {
		// 현재 로컬사용자의 프로필 받아와서 이름 비교
		final ProfileData namebefore = profileDatas
				.get(profileDatas.size() - 1);

		ParseQuery<ParseUser> query = ParseUser.getQuery();

		// query.whereEqualTo("username", namebefore.getName());
		query.whereEqualTo("username", searchName);
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> userList, ParseException e) {
				if (e == null) {
					ParseUser currentUser = ParseUser.getCurrentUser();
					try {
						currentUser = ParseUser.logIn(namebefore.getName(), "");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

					List<Boolean> dayList_searcheduser = new ArrayList<Boolean>();
					List<Boolean> dayList_currentuser = new ArrayList<Boolean>();

					ParseObject p = userList.get(0);
					dayList_searcheduser = p.getList(dayName);
					dayList_currentuser = currentUser.getList(dayName);

					String dayString = ""; // 출력될 값 저장하는 스트링변수 초기화
					int j = 0;
					if (dayList_searcheduser != null && dayList_currentuser != null) {
						for (int i = 0; i < 8; i++) {
							// 각 요일필드에 저장된 값을 불러올 때 true인 값의 인덱스+1을 출력
							if (dayList_searcheduser.get(i) == false && dayList_currentuser.get(i) == false) {
								j = i + 1;
								dayString += "  " + j;
							}
						}
						if (dayString == "") {
							if (dayName == "monday")
								mondayCommon.setText("");
							else if (dayName == "tuesday")
								tuesdayCommon.setText("");
							else if (dayName == "wednesday")
								wednesdayCommon.setText("");
							else if (dayName == "thursday")
								thursdayCommon.setText("");
							else if (dayName == "friday")
								fridayCommon.setText("");
							return;
						}
						if (dayName == "monday")
							mondayCommon.setText(dayString + " 교시");
						else if (dayName == "tuesday")
							tuesdayCommon.setText(dayString + " 교시");
						else if (dayName == "wednesday")
							wednesdayCommon.setText(dayString + " 교시");
						else if (dayName == "thursday")
							thursdayCommon.setText(dayString + " 교시");
						else if (dayName == "friday") {
							fridayCommon.setText(dayString + " 교시");
						}
					} else {
						Log.v("test", "Error: " + e.getMessage());
						// Alert.alertOneBtn(getActivity(),"Something went wrong!");
					}
				}
			}
		});
	}
	
	List<Boolean> arrayOfCommonSchedule(final String dayName, String searchName) {
		// 현재 로컬사용자의 프로필 받아와서 이름 비교
		final ProfileData namebefore = profileDatas.get(profileDatas.size() - 1);
		final List<Boolean> resultarr = new ArrayList<Boolean>();
		ParseQuery<ParseUser> query = ParseUser.getQuery();

		query.whereEqualTo("username", searchName);
		query.findInBackground(new FindCallback<ParseUser>() {
			@SuppressWarnings("null")
			@Override
			public void done(List<ParseUser> userList, ParseException e) {
				if (e == null) {
					ParseUser currentUser = ParseUser.getCurrentUser();
					try {
						currentUser = ParseUser.logIn(namebefore.getName(), "");
					} catch (ParseException e1) {
						e1.printStackTrace();
					}

					List<Boolean> dayList_searcheduser = new ArrayList<Boolean>();
					List<Boolean> dayList_currentuser = new ArrayList<Boolean>();
					
					ParseObject p = userList.get(0);
					dayList_searcheduser = p.getList(dayName);
					dayList_currentuser = currentUser.getList(dayName);

					//String dayString = ""; // 출력될 값 저장하는 스트링변수 초기화
					//int j = 0;
					if (dayList_searcheduser != null && dayList_currentuser != null) {
						for (int i = 0; i < 8; i++) {
							// 각 요일필드에 저장된 값을 불러올 때 true인 값의 인덱스+1을 출력
							if (dayList_searcheduser.get(i) == false && dayList_currentuser.get(i) == false) {
								//j = i + 1;
								resultarr.add(i, false);
								
								//dayString += "  " + j;
							} else
								resultarr.add(i, true);
						}
						
					} else {
						Log.v("test", "Error: " + e.getMessage());
						// Alert.alertOneBtn(getActivity(),"Something went wrong!");
					}
				}
			}
		});
		return resultarr;
	}
}
