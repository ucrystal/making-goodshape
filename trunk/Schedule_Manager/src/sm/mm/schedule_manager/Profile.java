package sm.mm.schedule_manager;

import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {

	TextView tv_univ;
	TextView tv_phone;
	TextView tv_name;

	static Database db;
	static ImageView profilePhoto_default;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		customActionBar();

		tv_univ = (TextView) findViewById(R.id.textView_univ);
		tv_phone = (TextView) findViewById(R.id.textView_phone);
		tv_name = (TextView) findViewById(R.id.textView_name);

		profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);

		// db에 접속여부 저장, 처음이라면 0 출려
		// 프로필 값이 저장되어 있다면 1 출력

		if (db.checkTable() == 0) {

			tv_univ.setText("  우지만대학교");
			tv_phone.setText("  821012346789");
			tv_name.setText(" 이름 ");

			Profile.db.dropProfileTable();
			Profile.db.createProfileTable();

		} else if (db.checkTable() == 1) {

			List<ProfileData> ProfileDatas = new LinkedList<ProfileData>();
			ProfileDatas = Profile.db.getAllProfileDatas();
			int d_size = ProfileDatas.size() - 1;
			ProfileData profile_pd = ProfileDatas.get(d_size);

			tv_name.setText("  " + profile_pd.getName());
			tv_univ.setText("  " + profile_pd.getUniv());
			tv_phone.setText("  " + profile_pd.getPhone());

			profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);

			if (profile_pd.getPhoto() == null) {
				profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);
			} else if (profile_pd.getPhoto() != null) {
				byte[] drawableIconByteArray = profile_pd.getPhoto();
				Bitmap d = BitmapFactory.decodeByteArray(drawableIconByteArray,
						0, drawableIconByteArray.length);

				profilePhoto_default.setImageBitmap(d);
			}

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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
		case R.id.action_modify:
			intent = new Intent(this, Profile_modify.class);
			startActivity(intent);
			overridePendingTransition(R.anim.default_start_enter,
					R.anim.default_start_exit);
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
		textviewTitle.setText(R.string.title_activity_profile);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
}
