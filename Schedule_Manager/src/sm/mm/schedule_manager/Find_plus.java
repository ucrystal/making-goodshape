package sm.mm.schedule_manager;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_plus);
		
		ParseObject testObject;

        testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        
        plus_name = (TextView)findViewById(R.id.plus_name);
        plus_day = (TextView)findViewById(R.id.plus_day);
        plus_time = (TextView)findViewById(R.id.plus_time);
        
        plus_name.setText(Find_list.info_s[1]);
        plus_day.setText(Find_emptyTime.emptyDay);
        plus_time.setText(Find_emptyTime.emptyTime);


		customActionBar();
		// name_result.setText(Find_list.info_s[1]);

		send_msg = (Button) findViewById(R.id.send_msg);
		send_msg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
		        ParseCloud.callFunctionInBackground("hello", new HashMap<String, Object>(), new FunctionCallback<String>() {
		            public void done(String result, ParseException e) {
		                if (e == null) {
		                    Log.v("parseTest", "result: <" + result + ">");
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
		        
		        String msg_s = n+"님이 "+d+"요일 "+t+"교시에 "+f+"님과 약속을 잡았습니다.";
		        
		        HashMap<String, Object> params = new HashMap<String, Object>();
		        params.put("targetPhoneNumber", "821096627226");
		        params.put("msg", msg_s);
		        ParseCloud.callFunctionInBackground("testSms", params, new FunctionCallback<String>() {
		            public void done(String result, ParseException e) {
		                if (e == null) {
		                    Log.v("parseTest", "sms result: <" + result + ">");
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
