package sm.mm.schedule_manager;

import java.util.ArrayList;
import java.util.List;




import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class Find_question extends Activity {
	
	Button search_result_btn;
	TextView name_result;
	static String info_s[] = new String [3]; 
	private Toast parseToast;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_question);

		customActionBar();
		name_result = (TextView)findViewById(R.id.textNameResult);
		name_result.setText(Find.search_name);
				
		search_result_btn = (Button) findViewById(R.id.result_btn);
		search_result_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String searchName = Find.search_name;
				searchName.trim();
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereEqualTo("username",searchName.toString());
				query.findInBackground(new FindCallback<ParseUser>() {
					@Override
					public void done(List<ParseUser> userList, ParseException e) {
						
						if (e == null) {
							ParseUser currentUser = ParseUser.getCurrentUser();
							//for(int i=0; i<userList.size(); i++) {
								ParseObject p = userList.get(0);
								info_s[1] = p.getString("username");
								info_s[2] = p.getString("phoneNumber");
								if (p.getList("monday")==null&&p.getList("tuesday")==null&&p.getList("wednesday")==null&&p.getList("thursday")==null&&p.getList("friday")==null) {

									parseToast = Toast.makeText(
											getApplicationContext(),
											"상대방이 시간표를 입력하지 않았습니다.",
											Toast.LENGTH_SHORT);
									parseToast.show();
									return;

								}

								if (currentUser.getList("monday")==null&&currentUser.getList("tuesday")==null&&currentUser.getList("wednesday")==null&&currentUser.getList("thursday")==null&&currentUser.getList("friday")==null) {
									parseToast = Toast.makeText(
											getApplicationContext(),
											"시간표를 입력하고 다시 시도하세요.",
											Toast.LENGTH_SHORT);
									parseToast.show();
									return;
								}
								Intent intent = new Intent(Find_question.this,
										Find_emptyTime.class);
								startActivity(intent);
								overridePendingTransition(
										R.anim.default_start_enter,
										R.anim.default_start_exit);
								finish();
							}
						}
					});
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

