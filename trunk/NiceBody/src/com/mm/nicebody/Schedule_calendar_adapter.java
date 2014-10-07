package com.mm.nicebody;
import java.util.ArrayList;
import java.util.Calendar;

import com.mm.nicebody.R;
import com.mm.nicebody.Schedule_calendar_day;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Schedule_calendar_adapter extends BaseAdapter
{
	private ArrayList<Schedule_calendar_day> DayList;
	private Context Context;
	private int Resource;
	private LayoutInflater Inflater;

	/**
	 * Adpater 생성자
	 * 
	 * @param context
	 *            컨텍스트
	 * @param textResource
	 *            레이아웃 리소스
	 * @param dayList
	 *            날짜정보가 들어있는 리스트
	 */
	public Schedule_calendar_adapter(Context context, int textResource, ArrayList<Schedule_calendar_day> dayList)
	{
		this.Context = context;
		this.DayList = dayList;
		this.Resource = textResource;
		this.Inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getCount()
	{
		return DayList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return DayList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Schedule_calendar_day day = DayList.get(position);

		DayViewHolde dayViewHolder;

		if(convertView == null)
		{
			convertView = Inflater.inflate(Resource, null);

			if(position % 7 == 6)
			{
				convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP()+getRestCellWidthDP(), getCellHeightDP()));
			}
			else
			{
				convertView.setLayoutParams(new GridView.LayoutParams(getCellWidthDP(), getCellHeightDP()));	
			}
			
			
			dayViewHolder = new DayViewHolde();

			dayViewHolder.Background = (LinearLayout)convertView.findViewById(R.id.day_cell_background);
			dayViewHolder.Day = (TextView) convertView.findViewById(R.id.day_cell);
			
			convertView.setTag(dayViewHolder);
		}
		else
		{
			dayViewHolder = (DayViewHolde) convertView.getTag();
		}

		if(day != null)
		{
			dayViewHolder.Day.setText(day.getDay());

			if(day.isInMonth())
			{	
					dayViewHolder.Day.setTextColor(Color.BLACK);

			}
			else
			{
				dayViewHolder.Day.setTextColor(Color.LTGRAY);
			}

		}

		return convertView;
	}

	public class DayViewHolde
	{
		public LinearLayout Background;
		public TextView Day;
		
	}

	private int getCellWidthDP()
	{
//		int width = Context.getResources().getDisplayMetrics().widthPixels;
		int cellWidth = 480/7;
		
		return cellWidth;
	}
	
	private int getRestCellWidthDP()
	{
//		int width = Context.getResources().getDisplayMetrics().widthPixels;
		int cellWidth = 480%7;
		
		return cellWidth;
	}
	
	private int getCellHeightDP()
	{
	//	int height = Context.getResources().getDisplayMetrics().widthPixels;
		int cellHeight = 480/6;
		
		return cellHeight;
	}
	
}
