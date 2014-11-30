package sm.mm.schedule_manager;

import java.util.ArrayList;




import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_list);
		
		lv_name = (ListView) findViewById(R.id.ListView1);

		ArrayList<Person> m_orders = new ArrayList<Person>();
		//mList = new ArrayList<Recommend_list_model>();

		Person p1 = new Person("유수정", "010-9662-6727");
		// 리스트에 추가할 객체입니다.
		Person p2 = new Person("허예슬", "010-4274-6727");
		// 리스트에 추가할 객체입니다.

		m_orders.add(p1);
		// 리스트에 객체를 추가합니다.
		m_orders.add(p2);
		// 리스트에 객체를 추가합니다.

		final PersonAdapter m_adapter = new PersonAdapter(this, R.layout.row, m_orders);
		//mCustomAdapter = new Recommend_Adapter(this, R.layout.recommend_list_item, mList);
		// 어댑터를 생성합니다.
		lv_name.setAdapter(m_adapter);
		//mLvData.setAdapter(mCustomAdapter);
		
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
}
