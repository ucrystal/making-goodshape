package sm.mm.schedule_manager;

import java.util.ArrayList;
import java.util.LinkedList;
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

	List<ProfileData> profileDatas;
	private Toast parseToast;
	private TextView searchedName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_emptytime);
		
		Parse.initialize(this, "JSemUvMrzikXlTudSXUZEqpwhpJomzymZIXnMK0m",
				"g244BplyVOkZ5tZc0fkXKoDHz2SjXfC6iAXaYH8l");

		customActionBar();
		
		profileDatas = new LinkedList<ProfileData>();
		profileDatas = Profile.db.getAllProfileDatas();

		mondayCommon = (TextView) findViewById(R.id.mon_common);
		tuesdayCommon = (TextView) findViewById(R.id.tue_common);
		wednesdayCommon = (TextView) findViewById(R.id.wed_common);
		thursdayCommon = (TextView) findViewById(R.id.thu_common);
		fridayCommon = (TextView) findViewById(R.id.fri_common);
		
		searchedName = (TextView)findViewById(R.id.textNameResult_empty);
		searchedName.setText(Find.search_name);
		
		compareUserSchedule("monday",Find.search_name);
		compareUserSchedule("tuesday",Find.search_name);
		compareUserSchedule("wednesday",Find.search_name);
		compareUserSchedule("thursday",Find.search_name);
		compareUserSchedule("friday",Find.search_name);
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
		//현재 로컬사용자의 프로필 받아와서 이름 비교
		final ProfileData namebefore = profileDatas.get(profileDatas.size() - 1);
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		
		//query.whereEqualTo("username", namebefore.getName());
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
					
					String dayString = ""; //출력될 값 저장하는 스트링변수 초기화
					int j = 0;
					if (dayList_searcheduser != null && dayList_currentuser != null) {
						for (int i = 0; i < 8; i++) {
							//각 요일필드에 저장된 값을 불러올 때 true인 값의 인덱스+1을 출력
							if (dayList_searcheduser.get(i) == false && dayList_currentuser.get(i) == false) {
								j = i+1;
								dayString += "  " + j;
							} else {
								if (dayName == "monday")
									mondayCommon.setText("공강없음");
								else if (dayName == "tuesday")
									tuesdayCommon.setText("공강없음");
								else if (dayName == "wednesday")
									wednesdayCommon.setText("공강없음");
								else if (dayName == "thursday")
									thursdayCommon.setText("공강없음");
								else if (dayName == "friday")	
									fridayCommon.setText("공강없음");
							}
						}
							if(dayName=="monday")
								mondayCommon.setText(dayString+" 교시");
							else if(dayName=="tuesday")
								tuesdayCommon.setText(dayString+" 교시");
							else if(dayName=="wednesday")
								wednesdayCommon.setText(dayString+" 교시");
							else if(dayName=="thursday")
								thursdayCommon.setText(dayString+" 교시");
							else if(dayName=="friday")
								fridayCommon.setText(dayString+" 교시");
							
					} else if (dayList_currentuser == null) {
						parseToast = Toast.makeText(getApplicationContext(), "시간표를 입력하고 다시 시도하세요.",Toast.LENGTH_LONG);
						parseToast.show();
					} else if (dayList_searcheduser == null) {
						parseToast = Toast.makeText(getApplicationContext(), "상대방이 시간표를 입력하지 않았습니다.",Toast.LENGTH_LONG);
						parseToast.show();
					}
				} else {
					Log.v("test", "Error: " + e.getMessage());
					// Alert.alertOneBtn(getActivity(),"Something went wrong!");
				}
			}
		});
	}
}
