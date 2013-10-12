package cdl.android.ui.bazaar;

import java.util.ArrayList;

import cdl.android.general.BazaarItem;
import cdl.android.general.SummaryItem;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

public class SummaryAdapter extends BaseAdapter{
	private ArrayList<SummaryItem> mItems;
	private OnClickListener mOnItemClick;
	private Context mContext;
	private Bundle mArgs;
	
	
	public SummaryAdapter(Context context, ArrayList<SummaryItem> items, Bundle args) {
		mItems = new ArrayList<SummaryItem>();
		mArgs = args;
		mContext = context;
		mItems = (ArrayList<SummaryItem>) items.clone();
	}
	
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int index) {
		return mItems.get(index);
	}

	public void resetItems(ArrayList<SummaryItem> items){
		mItems = items;
	}
	
	@Override
	public long getItemId(int position) {
			return position;
	}

	@Override
	public View getView(final int index, View convertView, ViewGroup parent) {
		SummaryItemView item;

		item = new SummaryItemView(mContext, mItems.get(index));
		item.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				String action = mArgs.getString("action");
				if(action.equals("castspell")){
					int playerId = mArgs.getInt("playerID");
					SummaryFragment.castSpell(playerId, mItems.get(index).getId(), mContext);
				}
				
			}});
		item.setClickable(true);

		return item;
	}

}
