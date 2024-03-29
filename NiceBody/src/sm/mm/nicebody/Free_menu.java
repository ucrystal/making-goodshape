package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Free_menu extends Activity {

	static int choiceEx = 0;
	Button free_pushup_btn, free_lunge_btn, free_legraise_btn;
	private BackPressCloseHandler backPressCloseHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.free_menu);

		customActionBar();

		free_pushup_btn = (Button) findViewById(R.id.free_pushup_btn);
		free_pushup_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				choiceEx = 1;

				Intent intent = new Intent(Free_menu.this, Free_explain.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);

			}
		});
		free_lunge_btn = (Button) findViewById(R.id.free_lunge_btn);
		free_lunge_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				choiceEx = 2;

				Intent intent = new Intent(Free_menu.this, Free_explain.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
			}
		});
		free_legraise_btn = (Button) findViewById(R.id.free_legraise_btn);
		free_legraise_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				choiceEx = 3;

				Intent intent = new Intent(Free_menu.this, Free_explain.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
		case R.id.action_profile:
			intent = new Intent(this, Profile.class);
			startActivity(intent);
			finish();
			break;

		case R.id.action_schedule:
			intent = new Intent(this, Schedule_calendar.class);
			startActivity(intent);
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void customActionBar() {
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_free_menu);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		abar.setHomeButtonEnabled(true);
	}

}
