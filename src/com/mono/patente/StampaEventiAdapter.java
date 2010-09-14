/* Copyright (C) 2010 Lorenzo Braghetto
 * 
 * This file is part of Patente.
 * 
 *   Patente is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *   
 *  Patente is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
*/

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
