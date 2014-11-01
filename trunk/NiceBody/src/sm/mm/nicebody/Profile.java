package sm.mm.nicebody;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {

	TextView tv_height;
	TextView tv_weight;
	TextView tv_name;

	static ImageView profilePhoto_default;

	Button modifyPro_btn;
	Button confirmPro_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		tv_height = (TextView) findViewById(R.id.textView_height);
		tv_weight = (TextView) findViewById(R.id.textView_weight);
		tv_name = (TextView) findViewById(R.id.textView_name);



		tv_height.setText(Profile_modify.Height);
		tv_weight.setText(Profile_modify.Weight);
		tv_name.setText(Profile_modify.Name);

		
		//file에 저장된 이미지 불러오는 과정 
		if(Profile_modify.checkInt == 0){
		
		}else if(Profile_modify.checkInt == 1) {
			
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
		

		modifyPro_btn = (Button) findViewById(R.id.modifyProfile_btn);
		modifyPro_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Profile.this, Profile_modify.class);
				startActivity(intent);
			}
		});

		confirmPro_btn = (Button) findViewById(R.id.confirmProfile_btn);
		confirmPro_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Profile.this, Main.class);
				startActivity(intent);
			}
		});
	}
}
