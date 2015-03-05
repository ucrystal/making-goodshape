package sm.mm.nicebody;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Recommend_list extends Activity {
	Integer[] images = {
			R.drawable.icon_unlock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock,
			R.drawable.icon_lock};

	Integer[] title = { 
			R.string.title_1, 
			R.string.title_2, 
			R.string.title_3,
			R.string.title_4, 
			R.string.title_5, 
			R.string.title_6,
			R.string.title_7,
			R.string.title_8,
			R.string.title_9,
			R.string.title_10};

	static int choiceCh = 0;
	
	private ListView mLvData;
	private Recommend_Adapter mCustomAdapter;
	private ArrayList<Recommend_list_model> mList;

	static final int DIALOG_CUSTOM_ID = 0;
	int selectedImageId = 0;
	ImageView image;
	TextView title_history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recommend_list);
		
		customActionBar();
		
		mLvData = (ListView) findViewById(R.id.ListView1);
		mList = new ArrayList<Recommend_list_model>();

		mCustomAdapter = new Recommend_Adapter(this, R.layout.recommend_list_item, mList);

		mLvData.setAdapter(mCustomAdapter);

		mLvData.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				selectedImageId = (int) id;
				Intent intent = null;
            	
            	switch (position) {
				
				case 0:
					choiceCh = 1;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 1:
					choiceCh = 2;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 2:
					choiceCh = 3;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 3:
					choiceCh = 4;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 4:
					choiceCh = 5;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 5:
					choiceCh = 6;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 6:
					choiceCh = 7;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 7:
					choiceCh = 8;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 8:
					choiceCh = 9;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				case 9:
					choiceCh = 10;
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					overridePendingTransition(R.anim.default_start_enter, R.anim.default_start_exit);
					finish();
					break;
				}
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();

		mList.clear();

		List<RecommendData> Recommend_result = Profile.db.getAllRecommendDatas();

		if (Recommend_result.size() == 2) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 3) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 4) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 5) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 6) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 7) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 8) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 9) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		else if (Recommend_result.size() == 10 || Recommend_result.size() == 11) {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_unlock));
		}
		else {
			mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
			mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_7, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_8, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_9, R.drawable.icon_lock));
			mList.add(new Recommend_list_model(R.string.title_10, R.drawable.icon_lock));
		}
		mCustomAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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

		case R.id.action_settings:
			intent = new Intent(this, Main.class);
			startActivity(intent);
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
		textviewTitle.setText(R.string.title_activity_recommend_list);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
	

}
