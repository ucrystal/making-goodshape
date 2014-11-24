package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
					.setTitle("����")
					.setMessage("���� �Ͻðڽ��ϱ�?")
					.setPositiveButton("��",
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
							}).setNegativeButton("�ƴϿ�", null).show();
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

		Profile.db = new FreeDatabase(this);
		
		free_btn = (Button) findViewById(R.id.free_btn);
		free_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				/*
				 * //�ϴ� ���ǻ� ������ mainToast =
				 * Toast.makeText(getApplicationContext(), "�������� �Է����ּ���",
				 * Toast.LENGTH_LONG); mainToast.show(); return;
				 */

				Intent intent = new Intent(Main.this, Free_menu.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
			}
		});

		recommend_btn = (Button) findViewById(R.id.recommend_btn);
		recommend_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Main.this, Recommend_list.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
			}
		});

		schedule_btn = (Button) findViewById(R.id.schedule_btn);

		schedule_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Main.this, Schedule_calendar.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
			}
		});

		profile_btn = (Button) findViewById(R.id.profile_btn);
		profile_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

/*

				List<FreeData> printByDate = Profile.db
						.getFreeDatasByDate("20141120");

				for (int i = 0; i < printByDate.size(); i++) {
					Log.v("DB_Test", printByDate.get(i).getType() + ","
							+ printByDate.get(i).getCount() + ","
							+ printByDate.get(i).getDate());
							
				}
*/
				Intent intent = new Intent(Main.this, Profile.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
