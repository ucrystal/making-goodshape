package sm.mm.schedule_manager;

import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseInstallation;

public class Main extends Activity {
	Button time_btn, find_btn, list_btn, profile_btn, info_btn;
	Toast mainToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		
		Profile.db = new Database(this);
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		//Profile.db.dropPromiseTable();
		//Profile.db.createPromiseTable();
		time_btn = (Button) findViewById(R.id.time_btn);
		time_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				

				if (Profile.db.checkTable() == 0) {
					mainToast = Toast.makeText(getApplicationContext(),
							"프로필을 입력해주세용", Toast.LENGTH_LONG);
					mainToast.show();
					return;
				}
				
				Intent intent = new Intent(Main.this, Time_menu.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				//finish();
			
			}
		});

		find_btn = (Button) findViewById(R.id.find_btn);
		find_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				

				if (Profile.db.checkTable() == 0) {
					mainToast = Toast.makeText(getApplicationContext(),
							"프로필을 입력해주세용", Toast.LENGTH_LONG);
					mainToast.show();
					return;
				}
				
				Intent intent = new Intent(Main.this, Find.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				//finish();

			}
		});

		list_btn = (Button) findViewById(R.id.list_btn);
		list_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				List<PromiseData> PromiseDatas = new LinkedList<PromiseData>();
				PromiseDatas = Profile.db.getAllPromiseDatas();
				
				if(PromiseDatas.size() == 0){
					mainToast = Toast.makeText(getApplicationContext(),
							"예정된 약속이 없습니다.", Toast.LENGTH_LONG);
					mainToast.show();
					return;
				}
				Intent intent = new Intent(Main.this, Find_list.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);

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

		info_btn = (Button) findViewById(R.id.info_btn);
		info_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(Main.this, Developer.class);
				startActivity(intent);
				overridePendingTransition(R.anim.default_start_enter,
						R.anim.default_start_exit);
				//finish();


			}
		});
	}

}
