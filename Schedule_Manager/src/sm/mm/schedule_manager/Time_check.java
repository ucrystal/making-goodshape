package sm.mm.schedule_manager;

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
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class Time_check extends Activity {

	private TextView mondayTxt;
	private TextView tuesdayTxt;
	private TextView wednesdayTxt;
	private TextView thursdayTxt;
	private TextView fridayTxt;

	List<ProfileData> profileDatas;

	private Handler mHandler;
    private ProgressDialog mProgressDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_check);
		Parse.initialize(this, "JSemUvMrzikXlTudSXUZEqpwhpJomzymZIXnMK0m",
				"g244BplyVOkZ5tZc0fkXKoDHz2SjXfC6iAXaYH8l");
		//로딩
		mHandler = new Handler();
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mProgressDialog = ProgressDialog.show(Time_check.this,"", 
                        "잠시만 기다려 주세요.",true);
                mHandler.postDelayed( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            if (mProgressDialog!=null&&mProgressDialog.isShowing()){
                                mProgressDialog.dismiss();
                            }
                        }
                        catch ( Exception e )
                        {
                            e.printStackTrace();
                        }
                    }
                }, 3000);
            }
        } );
		customActionBar();
		
		profileDatas = new LinkedList<ProfileData>();
		profileDatas = Profile.db.getAllProfileDatas();

		mondayTxt = (TextView) findViewById(R.id.mon_text);
		tuesdayTxt = (TextView) findViewById(R.id.tue_text);
		wednesdayTxt = (TextView) findViewById(R.id.wed_text);
		thursdayTxt = (TextView) findViewById(R.id.thu_text);
		fridayTxt = (TextView) findViewById(R.id.fri_text);


		retrieveUserSchedule("monday");
		retrieveUserSchedule("tuesday");
		retrieveUserSchedule("wednesday");
		retrieveUserSchedule("thursday");
		retrieveUserSchedule("friday");
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
	
	void retrieveUserSchedule(final String dayName) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();

		ProfileData namebefore = profileDatas.get(profileDatas.size() - 1);
		Log.v("test", namebefore.getName());
		query.whereEqualTo("username", namebefore.getName());
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> userList, ParseException e) {
				if (e == null) {
					List<Boolean> mondayList = new ArrayList<Boolean>();
					ParseObject p = userList.get(0);
					mondayList = p.getList(dayName);
					String dayString = ""; //출력될 값 저장하는 스트링변수 초기화
					if (mondayList != null) {
						for (int i = 0; i < 8; i++) {
							//각 요일필드에 저장된 값을 불러올 때 true인 값의 인덱스+1을 출력
							if (mondayList.get(i) == true) {
								int j = i+1;
								dayString += "  " + j;
							}
						}
						if(dayString=="") {
							if(dayName=="monday")
								mondayTxt.setText("");
							else if(dayName=="tuesday")
								tuesdayTxt.setText("");
							else if(dayName=="wednesday")
								wednesdayTxt.setText("");
							else if(dayName=="thursday")
								thursdayTxt.setText("");
							else if(dayName=="friday")
								fridayTxt.setText("");
							return;
						}
						if(dayName=="monday")
							mondayTxt.setText(dayString+" 교시");
						else if(dayName=="tuesday")
							tuesdayTxt.setText(dayString+" 교시");
						else if(dayName=="wednesday")
							wednesdayTxt.setText(dayString+" 교시");
						else if(dayName=="thursday")
							thursdayTxt.setText(dayString+" 교시");
						else if(dayName=="friday")
							fridayTxt.setText(dayString+" 교시");
					}
				} else {
					Log.v("test", "Error: " + e.getMessage());
					// Alert.alertOneBtn(getActivity(),"Something went wrong!");
				}
			}
		});
	}
}
