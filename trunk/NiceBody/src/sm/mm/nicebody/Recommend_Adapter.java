package sm.mm.nicebody;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
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

	ImageView img;
		
	public Recommend_Adapter(Context context, int rowLayoutResource,
			ArrayList<Recommend_list_model> objects) {
		super(context, rowLayoutResource, objects);
		this.mContext = context;
		this.mLayoutResource = rowLayoutResource;
		this.mList = objects;
		this.mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Recommend_list_model getItem(int position) {
		return mList.get(position);
	}

	@Override
	public int getPosition(Recommend_list_model item) {
		return mList.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(mLayoutResource, null);
		}

		Recommend_list_model data = getItem(position);

		if (data != null) {
			ImageView ivImage = (ImageView) convertView.findViewById(R.id.item_icon);
			TextView tvTitle = (TextView) convertView.findViewById(R.id.item_title);

			ivImage.setImageResource(data.getImage());
			tvTitle.setText(data.getTitle());
		}
		
		List<RecommendData> Recommend_result = Profile.db
				.getAllRecommendDatas();

		if (Recommend_result.size() == 2) {
			convertView.setBackgroundColor((position == mList.size() - 4 | position == mList.size() - 3 | position == mList.size() - 2 | position == mList.size() - 1) ? 
					Color.parseColor("#C9C9C9") : Color.parseColor("#DEDEDE"));
		}
		else if (Recommend_result.size() == 3) {
			convertView.setBackgroundColor((position == mList.size() - 3 | position == mList.size() - 2 | position == mList.size() - 1) ? 
					Color.parseColor("#C9C9C9") : Color.parseColor("#DEDEDE"));
		}
		else if (Recommend_result.size() == 4) {
			convertView.setBackgroundColor((position == mList.size() - 2 | position == mList.size() - 1) ? 
					Color.parseColor("#C9C9C9") : Color.parseColor("#DEDEDE"));			
		}
		else if (Recommend_result.size() == 5) {
			convertView.setBackgroundColor((position == mList.size() - 1) ? 
					Color.parseColor("#C9C9C9") : Color.parseColor("#DEDEDE"));
		}
		else if (Recommend_result.size() == 6) {
			convertView.setBackgroundColor(Color.parseColor("#DEDEDE"));			
		}else if (Recommend_result.size() == 7) {
			convertView.setBackgroundColor(Color.parseColor("#DEDEDE"));			
		}else
			convertView.setBackgroundColor((position == mList.size() - 5 | position == mList.size() - 4 | position == mList.size() - 3 | position == mList.size() - 2 | position == mList.size() - 1) ? 
					Color.parseColor("#C9C9C9") : Color.parseColor("#DEDEDE"));

		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) { /*
											 * if position is last index or
											 * second last index of mStrings
											 * then return false
											 */
	
		List<RecommendData> Recommend_result = Profile.db
				.getAllRecommendDatas();

		
		if (Recommend_result.size() == 2) {
			return (position == mList.size() - 4 | position == mList.size() - 3
					| position == mList.size() - 2 | position == mList.size() - 1) ? false
					: true;
		}
		else if (Recommend_result.size() == 3) {
			return (position == mList.size() - 3 | position == mList.size() - 2 | position == mList
					.size() - 1) ? false : true;
		}
		else if (Recommend_result.size() == 4) {
			return (position == mList.size() - 2 | position == mList.size() - 1) ? false
					: true;
		}
		else if (Recommend_result.size() == 5) {
			return (position == mList.size() - 1) ? false : true;
		}
		else if (Recommend_result.size() == 6) {
			return true;
		}else if (Recommend_result.size() == 7) {
			return true;
		}

		// ±âº»
		return (position == mList.size() - 5 | position == mList.size() - 4
				| position == mList.size() - 3 | position == mList.size() - 2 | position == mList
				.size() - 1) ? false : true;
	}
}
