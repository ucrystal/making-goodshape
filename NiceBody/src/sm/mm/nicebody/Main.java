package sm.mm.nicebody;

import java.util.HashMap;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Main extends Activity {

	ImageView logoImageview;
	Button free_btn, recommend_btn, schedule_btn, profile_btn, test_btn;
	Toast mainToast;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			new AlertDialog.Builder(this)
					.setTitle("종료")
					.setMessage("종료 하시겠습니까?")
					.setPositiveButton("예",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// ActivityManager activityManager =
									// (ActivityManager)
									// getSystemService(ACTIVITY_SERVICE);
									// activityManager.killBackgroundProcesses("sm.mm.nicebody");
									moveTaskToBack(true);
									finish();
								}
							}).setNegativeButton("아니오", null).show();
			return false;
		default:
			return false;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		Profile.db = new Database(this);
		
	
		free_btn = (Button) findViewById(R.id.free_btn);
		free_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (Profile.db.checkTable() == 0) {
					mainToast = Toast.makeText(getApplicationContext(),
							"프로필을 입력해주세용", Toast.LENGTH_LONG);
					mainToast.show();
					return;
				}

				Intent intent = new Intent(Main.this, Free_menu.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				//finish();
			}
		});

		recommend_btn = (Button) findViewById(R.id.recommend_btn);
		recommend_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Profile.db.checkTable() == 0) {
					mainToast = Toast.makeText(getApplicationContext(),
							"프로필을 입력해주세용", Toast.LENGTH_LONG);
					mainToast.show();
					return;
				}

				Intent intent = new Intent(Main.this, Recommend_list.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				//finish();
			}
		});

		schedule_btn = (Button) findViewById(R.id.schedule_btn);

		schedule_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, Schedule_calendar.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				//finish();
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
				//finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	/*
	 * @Override public void onDestroy() { super.onDestroy();
	 * android.os.Process.killProcess(android.os.Process.myPid()); }
	 */
}
