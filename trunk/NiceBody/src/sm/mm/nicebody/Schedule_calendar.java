package sm.mm.nicebody;

import java.util.ArrayList;
import java.util.Calendar;

import sm.mm.nicebody.R;
import sm.mm.nicebody.Schedule_calendar_adapter;
import sm.mm.nicebody.Schedule_calendar_day;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
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

	private ArrayList<Schedule_calendar_day> DayList;
	private Schedule_calendar_adapter Calendar_adapter;

	Calendar LastMonthCalendar;
	Calendar ThisMonthCalendar;
	Calendar NextMonthCalendar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule_calendar);

		Button bLastMonth = (Button) findViewById(R.id.calendar_btn01);
		Button bNextMonth = (Button) findViewById(R.id.calendar_btn02);

		calendar_title = (TextView) findViewById(R.id.calendar_title);
		calendar_view = (GridView) findViewById(R.id.schedule_calendarView);

		bLastMonth.setOnClickListener(this);
		bNextMonth.setOnClickListener(this);
		calendar_view.setOnItemClickListener(this);

		DayList = new ArrayList<Schedule_calendar_day>();

	}

	@Override
	protected void onResume() {
		super.onResume();

		// 이번달 의 캘린더 인스턴스를 생성
		ThisMonthCalendar = Calendar.getInstance();
		ThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
		getCalendar(ThisMonthCalendar);

	}

	/**
	 * 달력을 셋팅
	 * 
	 * @param calendar
	 *            달력에 보여지는 이번달의 Calendar 객체
	 */

	private void getCalendar(Calendar calendar) {
		int lastMonthStartDay;
		int dayOfMonth;
		int thisMonthLastDay;

		DayList.clear();

		// 이번달 시작일의 요일을 구함. 시작일이 일요일인 경우 인덱스를 1(일요일)에서 8(다음주 일요일)로 바꾼다

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
		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년"
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
		return calendar;
	}

	// 다음달의 Calendar 객체를 반환
	private Calendar getNextMonth(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.add(Calendar.MONTH, +1);
		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년"
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
		return calendar;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position,
			long arg3) {

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
}