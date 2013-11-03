package cdl.android.ui.bazaar;

import java.util.ArrayList;

import cdl.android.R;
import cdl.android.general.SummaryItem;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SummaryAdapter extends BaseAdapter{
	private ArrayList<SummaryItem> mItems;
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
		
		
		final SummaryItemView item;

		item = new SummaryItemView(mContext, mItems.get(index));
		String action = mArgs.getString("action");
		if(action != null){
			item.setOnClickListener(new View.OnClickListener(){
				public void onClick(View v) {
					String action = mArgs.getString("action");
					if(action.equals("castspell")){
						int playerId = mArgs.getInt("playerID");
						final int count = item.getItem().getAmountInt();
						
						
						SummaryFragment.castSpell(playerId, mItems.get(index).getId(), mContext, new Method(){
							public void handleStuff(){
								if(count > 0){
									TextView amount = (TextView) item.findViewById(R.id.amount);
									amount.setText("" + (count - 1));
									item.refreshDrawableState();
								}
							}
						});
					}
				}});
				item.setClickable(true);
	
			
		}
		return item;
	}
		
}