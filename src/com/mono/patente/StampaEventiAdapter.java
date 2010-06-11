package com.mono.patente;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StampaEventiAdapter extends BaseAdapter {
	private Context context;
	private List<StampaEventi> listOfEventi;
	
	public StampaEventiAdapter(Context context, List<StampaEventi> listOfEventi){
		this.context = context;
		this.listOfEventi = listOfEventi;
	}
	public int getCount() {
		return listOfEventi.size();
	}

	public Object getItem(int position) {
		return listOfEventi.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
		StampaEventi entry = listOfEventi.get(position);
		return new StampaEventiAdapterView(context,entry);
	}

}
