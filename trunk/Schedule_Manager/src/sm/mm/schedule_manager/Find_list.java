package sm.mm.schedule_manager;

import java.util.ArrayList;





import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Find_list extends Activity {

	ListView lv_name;

	ArrayList<String> arrList;
	ArrayAdapter<String> myAdapter;
	static String info_s[] = new String [3]; 
	//id, 이름, 전화번호 저장하기 -> 나중에 데이터 비교용으로 쓰일 것

	private Toast parseToast;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_list);
		Parse.initialize(this, "JSemUvMrzikXlTudSXUZEqpwhpJomzymZIXnMK0m",
				"g244BplyVOkZ5tZc0fkXKoDHz2SjXfC6iAXaYH8l");
		
		customActionBar();
		
		lv_name = (ListView) findViewById(R.id.ListView1);
		
		String searchName = Find.search_name;
		searchName.trim();
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username",searchName.toString());
		query.findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> userList, ParseException e) {
				ArrayList<Person> m_orders = new ArrayList<Person>();
				if (e == null) {
					for(int i=0; i<userList.size(); i++) {
						String otherUsername, otherUserPhone;
						ParseObject p = userList.get(i);
						otherUsername = p.getString("username");
						Log.v("test",otherUsername);
						otherUserPhone = p.getString("phoneNumber");
						Person p1 = new Person(otherUsername, otherUserPhone);
						
						m_orders.add(p1);
					}
				} else {
					Log.v("test", "Error: " + e.getMessage());
					// Alert.alertOneBtn(getActivity(),"Something went wrong!");
				}
				final PersonAdapter m_adapter = new PersonAdapter(Find_list.this, R.layout.row, m_orders);
				//mCustomAdapter = new Recommend_Adapter(this, R.layout.recommend_list_item, mList);
				// 어댑터를 생성합니다.
				lv_name.setAdapter(m_adapter);
				
				lv_name.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						
						//Person choice_p = m_orders.get(position);
						Person choice_p = m_adapter.items.get(position);

						info_s[0] = "1";
						info_s[1] =choice_p.getName(); 
						info_s[2] =choice_p.getNumber(); 

						
						Intent intent = new Intent(Find_list.this, Find_question.class);
						startActivity(intent);
						overridePendingTransition(R.anim.default_start_enter,
								R.anim.default_start_exit);
						finish();

					}
				});
			}
		});

	}

	private class PersonAdapter extends ArrayAdapter<Person> {

		private ArrayList<Person> items;

		public PersonAdapter(Context context, int textViewResourceId,
				ArrayList<Person> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Person p = items.get(position);
			if (p != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				if (tt != null) {
					tt.setText(p.getName());
				}
				if (bt != null) {
					bt.setText("전화번호: " + p.getNumber());
				}
			}
			return v;
		}
	}

	class Person {

		private String Name;
		private String Number;

		public Person(String _Name, String _Number) {
			this.Name = _Name;
			this.Number = _Number;
		}

		public String getName() {
			return Name;
		}

		public String getNumber() {
			return Number;
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.developer, menu);
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
		textviewTitle.setText(R.string.title_activity_find);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
	
}
