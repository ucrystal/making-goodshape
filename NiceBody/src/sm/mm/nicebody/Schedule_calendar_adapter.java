package sm.mm.nicebody;

import java.util.ArrayList;

import sm.mm.nicebody.R;
import sm.mm.nicebody.Schedule_calendar_day;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.LinearLayout;
import android.widget.TextView;

public class Schedule_calendar_adapter extends BaseAdapter {
	private ArrayList<Schedule_calendar_day> DayList;
	private Context Context;
	private int Resource;
	private LayoutInflater Inflater;

	/**
	 * Adpater ������
	 * 
	 * @param context
	 *            ���ؽ�Ʈ
	 * @param textResource
	 *            ���̾ƿ� ���ҽ�
	 * @param dayList
	 *            ��¥������ ����ִ� ����Ʈ
	 */
	public Schedule_calendar_adapter(Context context, int textResource,
			ArrayList<Schedule_calendar_day> dayList) {
		this.Context = context;
		this.DayList = dayList;
		this.Resource = textResource;
		this.Inflater = (LayoutInflater) Context
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return DayList.size();
	}

	@Override
	public Object getItem(int position) {
		return DayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Schedule_calendar_day day = DayList.get(position);

		DayViewHolde dayViewHolder;

		if (convertView == null) {
			convertView = Inflater.inflate(Resource, null);

			dayViewHolder = new DayViewHolde();

			dayViewHolder.Background = (LinearLayout) convertView
					.findViewById(R.id.day_cell_background);
			dayViewHolder.Day = (TextView) convertView
					.findViewById(R.id.day_cell);

			convertView.setTag(dayViewHolder);
		} else {
			dayViewHolder = (DayViewHolde) convertView.getTag();
		}

		if (day != null) {
			dayViewHolder.Day.setText(day.getDay());

			if (day.isToday()) {
				dayViewHolder.Background
						.setBackgroundResource(R.drawable.circle);
			}

			if (day.isInMonth()) {
				dayViewHolder.Day.setTextColor(Color.BLACK);

			} else {
				dayViewHolder.Day.setTextColor(Color.LTGRAY);
			}

		}

		return convertView;
	}

	public class DayViewHolde {
		public LinearLayout Background;
		public TextView Day;

	}
}