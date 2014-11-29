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

	Button modi_btn;
	Button main_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		tv_univ = (TextView) findViewById(R.id.textView_univ);
		tv_phone = (TextView) findViewById(R.id.textView_phone);
		tv_name = (TextView) findViewById(R.id.textView_name);

		profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);

		// db에 접속여부 저장, 처음이라면 0 출려
		// 프로필 값이 저장되어 있다면 1 출력

		if (db.checkTable() == 0) {

			tv_univ.setText("  우지만대학교");
			tv_phone.setText("  821012346789");
			tv_name.setText(" 홍길동 ");

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

		modi_btn = (Button) findViewById(R.id.button_modi);
		modi_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Profile.this, Profile_modify.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();

			}
		});
		
		main_btn = (Button) findViewById(R.id.button_main);
		main_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Profile.this, Main.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				finish();

			}
		});


	}
}
