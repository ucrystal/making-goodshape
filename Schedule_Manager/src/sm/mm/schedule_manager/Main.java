package sm.mm.schedule_manager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class Main extends Activity {
	Button time_btn, find_btn, list_btn, profile_btn, info_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Parse.initialize(this, "JSemUvMrzikXlTudSXUZEqpwhpJomzymZIXnMK0m", "g244BplyVOkZ5tZc0fkXKoDHz2SjXfC6iAXaYH8l");
		//Parse.initialize(this, "X5FUfboYlxLwgVL0DO6b2TXVJOPtc6Yj3TSs7Un1", "fUqCluAQOhwRyxGCE5y5mb7cuu8HVCMabxw6nlz4");

		initializePushNotification();
 
		Profile.db = new Database(this);
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		time_btn = (Button) findViewById(R.id.time_btn);
		time_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Main.this, Time_menu.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();
			
			}
		});

		find_btn = (Button) findViewById(R.id.find_btn);
		find_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Main.this, Find.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();

			}
		});

		list_btn = (Button) findViewById(R.id.list_btn);
		list_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		profile_btn = (Button) findViewById(R.id.profile_btn);
		profile_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Main.this, Profile.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();

			}
		});

		info_btn = (Button) findViewById(R.id.info_btn);
		info_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Main.this, Developer.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();


			}
		});
	}

	public void initializePushNotification() {
		ParseInstallation installation = ParseInstallation
				.getCurrentInstallation();
		installation.put("phoneNumber", "821096627226");
		installation.put("wantPush", true);
		installation.saveInBackground();
	}
}
