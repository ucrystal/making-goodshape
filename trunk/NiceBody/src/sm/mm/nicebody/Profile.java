package sm.mm.nicebody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends Activity {

	TextView tv_height;
	TextView tv_weight;
	
	static ImageView profilePhoto_default;
	
	Button modifyPro_btn;
	Button confirmPro_btn;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		tv_height = (TextView)findViewById(R.id.textView_height);
		tv_weight = (TextView)findViewById(R.id.textView_weight);
		
		profilePhoto_default = (ImageView)findViewById(R.id.profilePhoto_default);
		
		tv_height.setText(Profile_modify.Height);
		tv_weight.setText(Profile_modify.Weight);
		

		modifyPro_btn = (Button)findViewById(R.id.modifyProfile_btn);
		modifyPro_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Profile.this, Profile_modify.class);
	            startActivity(intent);
	         }
	      });  

		confirmPro_btn = (Button)findViewById(R.id.confirmProfile_btn);
		confirmPro_btn.setOnClickListener(new View.OnClickListener() {
	         @Override
	         public void onClick(View v) {
	            Intent intent = new Intent(Profile.this, Main.class);
	            startActivity(intent);
	         }
	      });  
	}
}
		

