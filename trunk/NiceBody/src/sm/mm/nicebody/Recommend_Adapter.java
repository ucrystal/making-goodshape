package sm.mm.nicebody;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Recommend_Adapter extends ArrayAdapter<Recommend_list_model> {
	private Context mContext;
	private int mLayoutResource;
	private ArrayList<Recommend_list_model> mList;
	
	private LayoutInflater mInflater;
	
	public Recommend_Adapter(Context context, int rowLayoutResource, ArrayList<Recommend_list_model> objects)
	{
		super(context, rowLayoutResource, objects);
		this.mContext = context;
		this.mLayoutResource = rowLayoutResource;
		this.mList = objects;
		this.mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public Recommend_list_model getItem(int position)
	{
		return mList.get(position);
	}

	@Override
	public int getPosition(Recommend_list_model item)
	{
		return mList.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		if(convertView == null)
		{
			convertView = mInflater.inflate(mLayoutResource, null);
		}
		
		Recommend_list_model data = getItem(position);
		
		if(data != null)
		{
			ImageView ivImage = (ImageView)convertView.findViewById(R.id.item_icon);
			TextView tvTitle = (TextView)convertView.findViewById(R.id.item_title);
			
			ivImage.setImageResource(data.getImage());
			tvTitle.setText(data.getTitle());
		}

		return convertView;
	}
	
	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}
	
	@Override
	public boolean isEnabled(int position)
	{  /* if position is last index or second last index of mStrings then return false*/
		return (position==mList.size()-4|position==mList.size()-3|position==mList.size()-2|position==mList.size()-1)?false:true;
	}
}
