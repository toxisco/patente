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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class Eventi extends Activity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.eventi);
	        
			SharedPreferences prefsevento = getSharedPreferences("Evento0", Context.MODE_PRIVATE);
	        
	        ListView list = (ListView)findViewById(R.id.ListView01);
	        
	        List<StampaEventi> listOfEventi = new ArrayList<StampaEventi>();
	        
	    	for(int i=1;prefsevento.getString("Data","")!="";i++)
	    	{
	    		String desc = prefsevento.getString("Descrizione", "");
	    		String data = prefsevento.getString("Data","");
	    		String puntiM = prefsevento.getString("Punti-","");
	    		String puntiP = prefsevento.getString("Punti+","");
	    		String dataevento = prefsevento.getString("DataEvento","");
	    		String ente = prefsevento.getString("Ente","");
	    		String punti="";
	    		if(puntiM=="")
	    		{
	    		
	    			punti=puntiP;
	    		}else
	    		{
	    			punti=puntiM;
	    		}
	    		
	    		listOfEventi.add(new StampaEventi(desc,data,punti,ente,dataevento,null));

	    		
	    		prefsevento = getSharedPreferences("Evento"+i, Context.MODE_PRIVATE);
	    	}
	        
	    	StampaEventiAdapter adapter = new StampaEventiAdapter(this, listOfEventi);
	        
	        list.setAdapter(adapter);
	 }
	 
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		 MenuInflater inflater = getMenuInflater();
		 inflater.inflate(R.menu.menu, menu);
		 return true;
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
	    	final SharedPreferences login = getSharedPreferences("Login", 0);
	    	final SharedPreferences sommario = getSharedPreferences("Sommario", 0);
		    switch (item.getItemId()) {
		    case R.id.Logout://Elimino dati utente
	            SharedPreferences.Editor editL = login.edit();
	            SharedPreferences.Editor editS = sommario.edit();
	            editL.clear();
	            editS.clear();
	            editL.commit();
	            editS.commit();
	            String EventiFile = "/data/data/com.mono.patente/shared_prefs/Evento0.xml";
	            File e = new File(EventiFile);
	            for(int i=1;e.exists();i++)
	            {
	                e.delete();
	                EventiFile = "/data/data/com.mono.patente/shared_prefs/Evento"+i+".xml";
	                e = new File(EventiFile);
	            }
	            String VeicoliFile = "/data/data/com.mono.patente/shared_prefs/Veicoli0.xml";
	            File v = new File(VeicoliFile);
	            for(int i=1;v.exists();i++)
	            {
	                v.delete();
	                VeicoliFile = "/data/data/com.mono.patente/shared_prefs/Veicoli"+i+".xml";
	                v = new File(VeicoliFile);
	            }
	        	Intent i = new Intent(getBaseContext(), Patente.class);
	        	startActivity(i);
			    break; 
		    }
		    return false;
		}
}
