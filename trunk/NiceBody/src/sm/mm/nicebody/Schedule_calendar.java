package sm.mm.nicebody;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
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
	Calendar Today;

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
	}

	@Override
	protected void onResume() {
		super.onResume();

		ThisMonthCalendar = Calendar.getInstance();
		ThisMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
		getCalendar(ThisMonthCalendar);
	}

	private void getCalendar(Calendar calendar) {
		int lastMonthStartDay;
		int dayOfMonth;
		int thisMonthLastDay;

		DayList.clear();

		dayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
		thisMonthLastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		lastMonthStartDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		if (dayOfMonth == SUNDAY) {
			dayOfMonth += 7;
		}

		lastMonthStartDay -= (dayOfMonth - 1) - 1;

		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년"
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");

		Schedule_calendar_day day;

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

	private Calendar getLastMonth(Calendar calendar) {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.add(Calendar.MONTH, -1);
		calendar_title.setText(ThisMonthCalendar.get(Calendar.YEAR) + "년"
				+ (ThisMonthCalendar.get(Calendar.MONTH) + 1) + "월");
		return calendar;
	}

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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, Main.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.action_profile:
			intent = new Intent(this, Profile.class);
			startActivity(intent);
			break;

		case R.id.action_schedule:
			intent = new Intent(this, Schedule_calendar.class);
			startActivity(intent);
			break;

		case R.id.action_settings:
			intent = new Intent(this, Main.class);
			startActivity(intent);
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
		textviewTitle.setText(R.string.title_activity_schedule_calendar);
		abar.setCustomView(viewActionBar, params);
		abar.setDisplayShowCustomEnabled(true);
		abar.setDisplayShowTitleEnabled(false);
		abar.setDisplayHomeAsUpEnabled(true);
		// abar.setIcon(R.color.transparent);
		abar.setHomeButtonEnabled(true);
	}
}