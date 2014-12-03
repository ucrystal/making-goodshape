package sm.mm.schedule_manager;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class Time_record extends Activity {
	private Boolean mon[] = new Boolean[8];
	private Boolean tue[] = new Boolean[8];
	private Boolean wed[] = new Boolean[8];
	private Boolean thu[] = new Boolean[8];
	private Boolean fri[] = new Boolean[8];

	Toast parseToast;

	Button time_finish;
	CheckBox moncb[] = new CheckBox[8];
	CheckBox tuecb[] = new CheckBox[8];
	CheckBox wedcb[] = new CheckBox[8];
	CheckBox thucb[] = new CheckBox[8];
	CheckBox fricb[] = new CheckBox[8];
	
	List<ProfileData> profileDatas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.time_record);
		findCheckBoxId();	
		customActionBar();
	}

	void checkBoxToBool() {
		for (int i = 0; i < 8; i++) {
			mon[i] = moncb[i].isChecked();
			tue[i] = tuecb[i].isChecked();
			wed[i] = wedcb[i].isChecked();
			thu[i] = thucb[i].isChecked();
			fri[i] = fricb[i].isChecked();
		}
	}

	void findCheckBoxId() {
		
		moncb[0] = (CheckBox) findViewById(R.id.mon_rb1);
		moncb[1] = (CheckBox) findViewById(R.id.mon_rb2);
		moncb[2] = (CheckBox) findViewById(R.id.mon_rb3);
		moncb[3] = (CheckBox) findViewById(R.id.mon_rb4);
		moncb[4] = (CheckBox) findViewById(R.id.mon_rb5);
		moncb[5] = (CheckBox) findViewById(R.id.mon_rb6);
		moncb[6] = (CheckBox) findViewById(R.id.mon_rb7);
		moncb[7] = (CheckBox) findViewById(R.id.mon_rb8);

		tuecb[0] = (CheckBox) findViewById(R.id.tue_rb1);
		tuecb[1] = (CheckBox) findViewById(R.id.tue_rb2);
		tuecb[2] = (CheckBox) findViewById(R.id.tue_rb3);
		tuecb[3] = (CheckBox) findViewById(R.id.tue_rb4);
		tuecb[4] = (CheckBox) findViewById(R.id.tue_rb5);
		tuecb[5] = (CheckBox) findViewById(R.id.tue_rb6);
		tuecb[6] = (CheckBox) findViewById(R.id.tue_rb7);
		tuecb[7] = (CheckBox) findViewById(R.id.tue_rb8);

		wedcb[0] = (CheckBox) findViewById(R.id.wed_rb1);
		wedcb[1] = (CheckBox) findViewById(R.id.wed_rb2);
		wedcb[2] = (CheckBox) findViewById(R.id.wed_rb3);
		wedcb[3] = (CheckBox) findViewById(R.id.wed_rb4);
		wedcb[4] = (CheckBox) findViewById(R.id.wed_rb5);
		wedcb[5] = (CheckBox) findViewById(R.id.wed_rb6);
		wedcb[6] = (CheckBox) findViewById(R.id.wed_rb7);
		wedcb[7] = (CheckBox) findViewById(R.id.wed_rb8);

		thucb[0] = (CheckBox) findViewById(R.id.thu_rb1);
		thucb[1] = (CheckBox) findViewById(R.id.thu_rb2);
		thucb[2] = (CheckBox) findViewById(R.id.thu_rb3);
		thucb[3] = (CheckBox) findViewById(R.id.thu_rb4);
		thucb[4] = (CheckBox) findViewById(R.id.thu_rb5);
		thucb[5] = (CheckBox) findViewById(R.id.thu_rb6);
		thucb[6] = (CheckBox) findViewById(R.id.thu_rb7);
		thucb[7] = (CheckBox) findViewById(R.id.thu_rb8);

		fricb[0] = (CheckBox) findViewById(R.id.fri_rb1);
		fricb[1] = (CheckBox) findViewById(R.id.fri_rb2);
		fricb[2] = (CheckBox) findViewById(R.id.fri_rb3);
		fricb[3] = (CheckBox) findViewById(R.id.fri_rb4);
		fricb[4] = (CheckBox) findViewById(R.id.fri_rb5);
		fricb[5] = (CheckBox) findViewById(R.id.fri_rb6);
		fricb[6] = (CheckBox) findViewById(R.id.fri_rb7);
		fricb[7] = (CheckBox) findViewById(R.id.fri_rb8);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_modify, menu);
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
		case R.id.action_ok:
			
			// checkbox가 체크되어있는 여부에 따라 true or false값으로 저장
			checkBoxToBool();

			//ProfileData namebefore = profileDatas.get(profileDatas.size() - 1);
			ParseUser user = ParseUser.getCurrentUser();
			/*try {
				user = ParseUser.logIn(namebefore.getName(), "");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}*/
			if(user.getList("monday")!=null) {
				user.remove("monday");
				user.remove("tuesday");
				user.remove("wednesday");
				user.remove("thursday");
				user.remove("friday");
			}
			
			user.saveInBackground();
			// parse의 user객체의 값들 없데이트
			// ParseQuery<ParseUser> query = ParseUser.getQuery();
			if (user != null) {
				user.addAll("monday", Arrays.asList(mon[0], mon[1], mon[2],	mon[3], mon[4], mon[5], mon[6], mon[7]));
				user.addAll("tuesday", Arrays.asList(tue[0], tue[1],tue[2], tue[3], tue[4], tue[5], tue[6], tue[7]));
				user.addAll("wednesday", Arrays.asList(wed[0], wed[1],wed[2], wed[3], wed[4], wed[5], wed[6], wed[7]));
				user.addAll("thursday", Arrays.asList(thu[0], thu[1],thu[2], thu[3], thu[4], thu[5], thu[6], thu[7]));
				user.addAll("friday", Arrays.asList(fri[0], fri[1], fri[2],	fri[3], fri[4], fri[5], fri[6], fri[7]));
				user.saveInBackground(new SaveCallback() {
					public void done(com.parse.ParseException e) {
						if (e == null) {
							// Save was successful!
							Log.v("test", "Succesfully Updated!");
						} else {
							// Save failed. Inspect e for details.
							Log.v("test", e.getMessage());
						}
					}
				});
			}
			
			intent = new Intent(Time_record.this, Main.class);
			startActivity(intent);
			overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
			finish();
			
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
		textviewTitle.setText(R.string.title_activity_time_menu);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
}
