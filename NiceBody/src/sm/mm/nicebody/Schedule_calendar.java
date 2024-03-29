package sm.mm.nicebody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sm.mm.nicebody.R;
import sm.mm.nicebody.Schedule_calendar_adapter;
import sm.mm.nicebody.Schedule_calendar_day;
import android.app.ActionBar;
import android.app.Activity;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class Schedule_calendar extends Activity implements OnItemClickListener,
		OnClickListener {
	public static int SUNDAY = 1;
	public static int MONDAY = 2;
	public static int TUESDAY = 3;
	public static int WEDNSESDAY = 4;
	public static int THURSDAY = 5;
	public static int FRIDAY = 6;
	public static int SATURDAY = 7;

	private TextView calendar_title;
	private GridView calendar_view;

	private static ArrayList<Schedule_calendar_day> DayList;
	private Schedule_calendar_adapter Calendar_adapter;

	Calendar LastMonthCalendar;
	Calendar ThisMonthCalendar;
	Calendar NextMonthCalendar;

	ListView listView;
	ArrayList<String> resultList;
	ArrayAdapter<String> list_adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_calendar);

		customActionBar();

		Button bLastMonth = (Button) findViewById(R.id.calendar_btn01);
		Button bNextMonth = (Button) findViewById(R.id.calendar_btn02);

		calendar_title = (TextView) findViewById(R.id.calendar_title);
		calendar_view = (GridView) findViewById(R.id.schedule_calendarView);

		bLastMonth.setOnClickListener(this);
		bNextMonth.setOnClickListener(this);
		calendar_view.setOnItemClickListener(this);

		DayList = new ArrayList<Schedule_calendar_day>();
		listView = (ListView) findViewById(R.id.result_listView);

		resultList = new ArrayList<String>();
		list_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, resultList);

		// listview 객체에 아답터 객체 연결
		listView.setAdapter(list_adapter);
		listView.setDivider(new ColorDrawable(Color.WHITE));

	}

	@Override
	protected void onResume() {
		super.onResume();

		// 이번달의 캘린더 인스턴스를 생성
		ThisMonthCalendar = Calendar.getInstance();
		ThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
		getCalendar(ThisMonthCalendar);

	}

	// 달력셋팅
	private void getCalendar(Calendar calendar) {
		int lastMonthStartDay;
		int dayOfMonth;
		int thisMonthLastDay;

		DayList.clear();

		// 이번달 시작일의 요일을 구함. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꿈
		dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// 지난달 마지막 날
		lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (dayOfMonth == SUNDAY) {
			dayOfMonth += 7;
		}

		lastMonthStartDay -= (dayOfMonth - 1) - 1;

		// 캘린더 타이틀 세팅
		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년 "
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

		Schedule_calendar_day day;

		Calendar todayCalendar = Calendar.getInstance();

		for (int i = 0; i < dayOfMonth - 1; i++) {
			int date = lastMonthStartDay + i;
			day = new Schedule_calendar_day();
			day.setDay(Integer.toString(date));
			day.setInMonth(false);

			DayList.add(day);
		}

		for (int i = 1; i <= thisMonthLastDay; i++) {
			day = new Schedule_calendar_day();
			String Info_year = String.valueOf(ThisMonthCalendar
					.get(Calendar.YEAR));
			String Info_month = String.valueOf(ThisMonthCalendar
					.get(Calendar.MONTH) + 1);

			if (i < 10 && ThisMonthCalendar.get(Calendar.MONTH) < 10) {
				String Info_date = Info_year + "0" + Info_month + "0"
						+ String.valueOf(i);
				List<FreeData> Info_gym = Profile.db.getFreeDatasByDate("'"
						+ Info_date + "'");
				List<RecommendData> Info_gym_r = Profile.db
						.getRecommendDatasByDate("'" + Info_date + "'");

				if (Info_gym.size() > 0 || Info_gym_r.size() > 1) {

					day.setInfoGym(true);
					Profile.db.updateRecordData(0, Info_date);
				}
			} else if (i >= 10 && ThisMonthCalendar.get(Calendar.MONTH) < 10) {
				String Info_date = Info_year + "0" + Info_month
						+ String.valueOf(i);
				List<FreeData> Info_gym = Profile.db.getFreeDatasByDate("'"
						+ Info_date + "'");
				List<RecommendData> Info_gym_r = Profile.db
						.getRecommendDatasByDate("'" + Info_date + "'");

				if (Info_gym.size() > 0 || Info_gym_r.size() > 1) {

					day.setInfoGym(true);
					Profile.db.updateRecordData(0, Info_date);
				}
			} else if (i < 10 && ThisMonthCalendar.get(Calendar.MONTH) >= 10) {
				String Info_date = Info_year + Info_month + "0"
						+ String.valueOf(i);
				List<FreeData> Info_gym = Profile.db.getFreeDatasByDate("'"
						+ Info_date + "'");
				List<RecommendData> Info_gym_r = Profile.db
						.getRecommendDatasByDate("'" + Info_date + "'");

				if (Info_gym.size() > 0 || Info_gym_r.size() > 1) {

					day.setInfoGym(true);
					Profile.db.updateRecordData(0, Info_date);
				}

			} else if (i >= 10 && ThisMonthCalendar.get(Calendar.MONTH) >= 10) {
				String Info_date = Info_year + Info_month + String.valueOf(i);
				List<FreeData> Info_gym = Profile.db.getFreeDatasByDate("'"
						+ Info_date + "'");

				List<RecommendData> Info_gym_r = Profile.db
						.getRecommendDatasByDate("'" + Info_date + "'");

				if (Info_gym.size() > 0 || Info_gym_r.size() > 1) {

					day.setInfoGym(true);
					Profile.db.updateRecordData(0, Info_date);
				}
			}

			day.setDay(Integer.toString(i));

			ThisMonthCalendar.set(Calendar.DAY_OF_MONTH, i);
			day.setIsToday(isToday(todayCalendar, ThisMonthCalendar));

			day.setInMonth(true);
			DayList.add(day);

		}

		for (int i = 1; i < 42 - (thisMonthLastDay + dayOfMonth - 1) + 1; i++) {
			day = new Schedule_calendar_day();
			day.setDay(Integer.toString(i));
			day.setInMonth(false);
			DayList.add(day);
		}

		initCalendarAdapter();
	}

	private boolean isToday(Calendar todayCalendar, Calendar ThisMonthCalendar) {
		if (todayCalendar.get(Calendar.YEAR) == ThisMonthCalendar
				.get(Calendar.YEAR)
				&& todayCalendar.get(Calendar.MONTH) == ThisMonthCalendar
						.get(Calendar.MONTH)
				&& todayCalendar.get(Calendar.DAY_OF_MONTH) == ThisMonthCalendar
						.get(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}

	// 지난달의 Calendar 객체를 반환
	private Calendar getLastMonth(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.add(Calendar.MONTH, -1);
		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년 "
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
		return calendar;
	}

	// 다음달의 Calendar 객체를 반환
	private Calendar getNextMonth(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.add(Calendar.MONTH, +1);
		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년 "
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
		return calendar;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		resultList.clear();

		// 클릭된 그리드뷰 아이템의 포지션을 이용해 어댑터뷰에서 아이템을 꺼내온다.
		Schedule_calendar_day data = (Schedule_calendar_day) parent
				.getItemAtPosition(position);
		String date = data.getDay();

		if (data.isInMonth() == true) {
			String year = String.valueOf(ThisMonthCalendar.get(Calendar.YEAR));
			String month = String
					.valueOf(ThisMonthCalendar.get(Calendar.MONTH) + 1);

			String date_result = null;
			if (Integer.parseInt(date) < 10 && Integer.parseInt(month) < 10) {
				date_result = year + "0" + month + "0" + date;
			} else if (Integer.parseInt(date) >= 10
					&& Integer.parseInt(month) < 10) {
				date_result = year + "0" + month + date;
			} else if (Integer.parseInt(date) < 10
					&& Integer.parseInt(month) >= 10) {
				date_result = year + month + "0" + date;
			} else if (Integer.parseInt(date) >= 10
					&& Integer.parseInt(month) >= 10) {
				date_result = year + month + date;
			}

			List<FreeData> Schedule_result = Profile.db.getFreeDatasByDate("'"
					+ date_result + "'");

			List<RecommendData> Schedule_result_r = Profile.db
					.getRecommendDatasByDate("'" + date_result + "'");
			List<RecommendData> Schedule_check = Profile.db
					.getAllRecommendDatas();

			// 운동별 횟수 출력
			int totalCount1 = 0;
			int totalCount2 = 0;
			int totalCount3 = 0;
			double total_cal = 0;

			if (date_result == null || date_result.equals(""))
				return;

			for (int i = 0; i < Schedule_result.size(); i++) {

				if (Schedule_result.get(i).getType() == 1) {
					totalCount1 += Schedule_result.get(i).getCount();
				}
				if (Schedule_result.get(i).getType() == 2) {
					totalCount2 += Schedule_result.get(i).getCount();
				}
				if (Schedule_result.get(i).getType() == 3) {
					totalCount3 += Schedule_result.get(i).getCount();
				}
			}

			if (totalCount1 != 0) {
				// 리스트 객체에 데이터 추가
				resultList.add(String.valueOf("자유운동 :             팔굽혀펴기     "
						+ totalCount1 + "회"));
				total_cal += Calorie.cal_pushUp(totalCount1);
			}
			if (totalCount2 != 0) {
				resultList.add(String
						.valueOf("자유운동 :             닐링 레그 리프트    "
								+ totalCount2 + "회"));
				total_cal += Calorie.cal_neeling(totalCount2);
			}
			if (totalCount3 != 0) {
				resultList.add(String.valueOf("자유운동 :             레그레이즈     "
						+ totalCount3 + "회"));
				total_cal += Calorie.cal_legRaise(totalCount3);
			}

			String rec_s = "";
			Log.v("Test", "스케줄러 사이즈 :" + Schedule_result_r.size());

			if (Schedule_check.size() > 1) {

				for (int i = 0; i < Schedule_result_r.size(); i++) {

					if (Schedule_result_r.get(i).getId() != 0) {
						if (i < Schedule_result_r.size() - 1
								&& Schedule_result_r.get(i).getId() != 1) {

							rec_s += Schedule_result_r.get(i).getId() - 1
									+ ", ";

							total_cal += Calorie
									.cal_pushUp(Recommend_record.arr[Schedule_result_r
											.get(i).getId() - 2][0])
									+ Calorie
											.cal_neeling(Recommend_record.arr[Schedule_result_r
													.get(i).getId() - 2][1])
									+ Calorie
											.cal_legRaise(Recommend_record.arr[Schedule_result_r
													.get(i).getId() - 2][2]
													+ Recommend_record.arr[Schedule_result_r
															.get(i).getId() - 2][3]);

						} else if (i == Schedule_result_r.size() - 1) {

							rec_s += Schedule_result_r.get(i).getId() - 1 + " ";
							total_cal += Calorie
									.cal_pushUp(Recommend_record.arr[Schedule_result_r
											.get(i).getId() - 2][0])
									+ Calorie
											.cal_neeling(Recommend_record.arr[Schedule_result_r
													.get(i).getId() - 2][1])
									+ Calorie
											.cal_legRaise(Recommend_record.arr[Schedule_result_r
													.get(i).getId() - 2][2]
													+ Recommend_record.arr[Schedule_result_r
															.get(i).getId() - 2][3]);

						}

					}
				}
			}

			if (Schedule_result_r.size() > 0 && Schedule_check.size() > 1) {
				resultList.add(String.valueOf("추천운동 :              " + rec_s
						+ "단계     성공"));
			}

			if (total_cal > 0) {
				resultList.add(String.valueOf("총 소비 칼로리 :                   "
						+ (int) total_cal + "cal"));
			}

		}

		// 갱신
		list_adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.calendar_btn01:
			ThisMonthCalendar = getLastMonth(ThisMonthCalendar);
			getCalendar(ThisMonthCalendar);
			break;
		case R.id.calendar_btn02:
			ThisMonthCalendar = getNextMonth(ThisMonthCalendar);
			getCalendar(ThisMonthCalendar);
			break;
		}
	}

	private void initCalendarAdapter() {
		Calendar_adapter = new Schedule_calendar_adapter(this,
				R.layout.schedule_calendar_day, DayList);
		calendar_view.setAdapter(Calendar_adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void finish() {
		super.finish();
	}

	public void customActionBar() {
		final ActionBar abar = getActionBar();
		abar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#67C6E5")));
		View viewActionBar = getLayoutInflater().inflate(
				R.layout.actionbar_layout, null);
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.WRAP_CONTENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
		TextView textviewTitle = (TextView) viewActionBar
				.findViewById(R.id.actionbar_textview);
		textviewTitle.setText(R.string.title_activity_schedule_calendar);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayUseLogoEnabled(true);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		abar.setHomeButtonEnabled(true);
	}

}