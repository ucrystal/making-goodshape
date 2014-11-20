package sm.mm.nicebody;

import android.app.ActionBar;
import android.app.Activity;
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

	TextView tv_height;
	TextView tv_weight;
	TextView tv_name;
	
	static FreeDatabase db;

	static ImageView profilePhoto_default;

	Button modifyPro_btn;
	Button confirmPro_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		customActionBar();

		tv_height = (TextView) findViewById(R.id.textView_height);
		tv_weight = (TextView) findViewById(R.id.textView_weight);

		profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);


		tv_height = (TextView) findViewById(R.id.textView_height);
		tv_weight = (TextView) findViewById(R.id.textView_weight);
		tv_name = (TextView) findViewById(R.id.textView_name);

		//db에 접속여부 저장, 처음이라면 0 출려
		//프로필 값이 저장되어 있다면 1 출력
		
		
		//Profile.db.dropProfileTable();
		
		if(db.checkTable() == 0){
			
			tv_height.setText("  00 cm");
			tv_weight.setText("  00 kg");
			tv_name.setText(" 홍길동 ");
			
			Profile.db.dropProfileTable();
			Profile.db.createProfileTable();
			
		
		}else if(db.checkTable() == 1) {
			
			ProfileData profile_pd = db.getProfileData();
			tv_name.setText("  "+profile_pd.getName());
			tv_height.setText("  "+profile_pd.getHeight()+" cm");
			tv_weight.setText("  "+profile_pd.getWeight()+" kg");
			
			try {
				profilePhoto_default = (ImageView) findViewById(R.id.profilePhoto_default);
				String imgpath = "data/data/sm.mm.nicebody/files/profile.png";
				Bitmap bmp = BitmapFactory.decodeFile(imgpath);
				profilePhoto_default.setImageBitmap(bmp);
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "load error",
						Toast.LENGTH_SHORT).show();
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
			break;
		case R.id.action_modify:
			intent = new Intent(this, Profile_modify.class);
			startActivity(intent);
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
