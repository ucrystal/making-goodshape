package sm.mm.schedule_manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Find_list extends Activity {

	ListView lv_name;
	int selectedImageId;
	String name_tvfl;
	List<PromiseData> PromiseDatas;

	ArrayList<String> arrList;
	ArrayAdapter<String> myAdapter;
	static String info_s[] = new String[3];
	// id, 이름, 전화번호 저장하기 -> 나중에 데이터 비교용으로 쓰일 것

	private Toast listToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_list);
		
		customActionBar();

		lv_name = (ListView) findViewById(R.id.ListView1);
		selectedImageId = 0;

		final ArrayList<Person> m_orders = new ArrayList<Person>();

		PromiseDatas = new LinkedList<PromiseData>();
		PromiseDatas = Profile.db.getAllPromiseDatas();

		if (PromiseDatas.size() != 0) {

			int p_size = PromiseDatas.size() - 1;
			for (int i = 0; i < PromiseDatas.size(); i++) {

				PromiseData promise_d = PromiseDatas.get(i);
				String is =String.valueOf(promise_d.getId());
				String ns =promise_d.getName();
				String ds =promise_d.getDay();
				Log.v("삭제 확인", "id값"+is +",이름"+ns+", 요일"+ds);
				
				Person p1 = new Person(is,
						promise_d.getName(), promise_d.getDay(),
						promise_d.getTime());
				m_orders.add(p1);
			}

		}
		
		Log.v("삭제 확인", "값"+m_orders.size());
		
		final PersonAdapter m_adapter = new PersonAdapter(Find_list.this,
				R.layout.row, m_orders);

		// 어댑터를 생성합니다.
		lv_name.setAdapter(m_adapter);

		lv_name.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				name_tvfl = m_orders.get(position).getName();

				final String value = m_orders.get(position).getId();
				final String name_value = m_orders.get(position).getName();
				

				DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						PromiseDatas = new LinkedList<PromiseData>();
						PromiseDatas = Profile.db.getAllPromiseDatas();
						Profile.db.deletePromise(value);
						
						PromiseDatas = Profile.db.getAllPromiseDatas();
						
						String ls = name_value +"님과의 약속이 삭제되었습니다!";
						
						listToast = Toast.makeText(getApplicationContext(),
								ls, Toast.LENGTH_LONG);
						listToast.show();
						
						
						Intent intent = new Intent(Find_list.this, Find_list.class);
						startActivity(intent);
						finish();
						
						
					}
				};

				DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				};

				new AlertDialog.Builder(Find_list.this)
						.setTitle("약속을 삭제하시겠습니까?")
						.setNeutralButton("약속 삭제", albumListener)
						.setNegativeButton("삭제 취소", cancelListener).show();

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
					bt.setText(p.getDay() + "요일, " + p.getTime()
							+ "교시에 약속이 잡혀있습니다.");
				}
			}
			return v;
		}
	}

	class Person {

		private String id;
		private String Name;
		private String Day;
		private String Time;

		public Person(String id, String _Name, String _Day, String _Time) {
			this.id = id;
			this.Name = _Name;
			this.Day = _Day;
			this.Time = _Time;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return Name;
		}

		public String getDay() {
			return Day;
		}

		public String getTime() {
			return Time;
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
