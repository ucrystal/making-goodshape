package sm.mm.nicebody;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
			R.drawable.icon_lock };

	Integer[] title = { 
			R.string.title_1, 
			R.string.title_2, 
			R.string.title_3,
			R.string.title_4, 
			R.string.title_5, 
			R.string.title_6 };

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
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(Recommend_list.this, Recommend_description1.class);
					startActivity(intent);
					break;		
				}
			}
		});
	}


	@Override
	protected void onResume() {
		super.onResume();

		mList.clear();

		mList.add(new Recommend_list_model(R.string.title_1, R.drawable.icon_unlock));
		mList.add(new Recommend_list_model(R.string.title_2, R.drawable.icon_lock));
		mList.add(new Recommend_list_model(R.string.title_3, R.drawable.icon_lock));
		mList.add(new Recommend_list_model(R.string.title_4, R.drawable.icon_lock));
		mList.add(new Recommend_list_model(R.string.title_5, R.drawable.icon_lock));
		mList.add(new Recommend_list_model(R.string.title_6, R.drawable.icon_lock));

		mCustomAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommend_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
