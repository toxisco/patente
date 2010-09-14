package com.mono.patente;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class StampaEventiAdapterView extends LinearLayout {

	public StampaEventiAdapterView(Context context, StampaEventi entry) {
		super(context);
		
		this.setOrientation(VERTICAL);
		this.setTag(entry);
		
		if(entry.getData6()==null)
		{
			View v = inflate(context, R.layout.eventilist, null);
	
			TextView tvContact = (TextView)v.findViewById(R.id.Desc);
			tvContact.setText(entry.getName());
			
			TextView tvPhone = (TextView)v.findViewById(R.id.Datavar);
			tvPhone.setText(entry.getPhone());
	
			TextView tvMail = (TextView)v.findViewById(R.id.Puntivar);
			tvMail.setText(entry.getMail());
			
			TextView DataEvento = (TextView)v.findViewById(R.id.DataEventovar);
			DataEvento.setText(entry.getDataE());
			
			TextView Ente = (TextView)v.findViewById(R.id.Entevar);
			Ente.setText(entry.getEnte());
			
			addView(v);
		}else
		{
			View v = inflate(context, R.layout.veicolilist, null);
			
			TextView tvContact = (TextView)v.findViewById(R.id.Veicolo);
			tvContact.setText(entry.getName());
			
			TextView tvPhone = (TextView)v.findViewById(R.id.Targavar);
			tvPhone.setText(entry.getPhone());
	
			TextView tvMail = (TextView)v.findViewById(R.id.Revvar);
			tvMail.setText(entry.getMail());
			
			TextView DataEvento = (TextView)v.findViewById(R.id.Potenzavar);
			DataEvento.setText(entry.getDataE()+"kw");
			
			TextView Ente = (TextView)v.findViewById(R.id.Classevar);
			Ente.setText(entry.getEnte());
			
			TextView Tipo = (TextView)v.findViewById(R.id.Tipovar);
			Tipo.setText(entry.getData6());
			
			addView(v);
		}
		
	}

}
