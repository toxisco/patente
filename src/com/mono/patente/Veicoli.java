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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class Veicoli extends Activity {
	
    List<StampaEventi> listOfEventi = new ArrayList<StampaEventi>();

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.veicoli);
	        
			SharedPreferences prefsevento = getSharedPreferences("Veicoli0", Context.MODE_PRIVATE);
	        
	        ListView list = (ListView)findViewById(R.id.ListView01);
	        	        
	        String veicolo = "";
	        String revisione = "";
	        
	    	for(int i=1;prefsevento.getString("Tipo","")!="";i++)
	    	{
	    		veicolo = prefsevento.getString("Veicolo", "");
	    		String targa = prefsevento.getString("Targa","");
	    		revisione = prefsevento.getString("Revisione","");
	    		String euro = prefsevento.getString("Euro","");
	    		String tipo = prefsevento.getString("Tipo","");
	    		String potenza = prefsevento.getString("Potenza","");

	    		listOfEventi.add(new StampaEventi(veicolo,targa,revisione,euro,potenza,tipo));

	    		
	    		prefsevento = getSharedPreferences("Evento"+i, Context.MODE_PRIVATE);
	    	}

	    	StampaEventiAdapter adapter = new StampaEventiAdapter(this, listOfEventi);
	        list.setAdapter(adapter);	  
	        list.setItemsCanFocus(true);
	        list.setOnItemLongClickListener(new OnItemLongClickListener() {
	            
	        	@Override
	            public boolean onItemLongClick(AdapterView<?> list, View view, int position, long id) {
	                StampaEventi veicolo = listOfEventi.get(position);
	        		
	    	    	//creo la data e il titolo dell'evento da aggiungere
	    	        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    	        long inizioT=0;
	    	        try {
	    				inizioT = df.parse(veicolo.getMail()).getTime();
	    			} catch (java.text.ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}           	        
	    	        final String titoloCal = "Scadenza revisione veicolo "+ veicolo.getName();
	    	        final long inizio=inizioT;
	        		
	        		addToCalendar(getApplicationContext(), titoloCal, inizio, inizio+86400000);
	        		
	                return true;
	                }

	            
	        });
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
		
		//Codice per aggiungere un evento al calendario scelto dall'utente.
		//Grazie a Andrea Baccega vekexasia[#AT#]gmail[#DOT#]com per aver condiviso il codice
		//http://www.andreabaccega.com/blog/2010/08/09/add-events-on-google-calendar-on-android-froyo/
		public void addToCalendar(Context ctx, final String title, final long dtstart, final long dtend) {
			final ContentResolver cr = ctx.getContentResolver();
			Cursor cursor ;
			if (Integer.parseInt(Build.VERSION.SDK) == 8 )
				cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{ "_id", "displayname" }, null, null, null);
			else
				cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{ "_id", "displayname" }, null, null, null);
			if ( cursor.moveToFirst() ) {
				final String[] calNames = new String[cursor.getCount()];
				final int[] calIds = new int[cursor.getCount()];
				for (int i = 0; i < calNames.length; i++) {
					calIds[i] = cursor.getInt(0);
					calNames[i] = cursor.getString(1);
					cursor.moveToNext();
				}

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setSingleChoiceItems(calNames, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,	int which) {
						ContentValues cv = new ContentValues();
						cv.put("calendar_id", calIds[which]);
						cv.put("title", title);
						cv.put("dtstart", dtstart );
						cv.put("hasAlarm", 1);
						cv.put("dtend", dtend);

						Uri newEvent ;
						if (Integer.parseInt(Build.VERSION.SDK) == 8 )
							newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), cv);
						else
							newEvent = cr.insert(Uri.parse("content://calendar/events"), cv);

						if (newEvent != null) {
							long id = Long.parseLong( newEvent.getLastPathSegment() );
							ContentValues values = new ContentValues();
							values.put( "event_id", id );
							values.put( "method", 1 );
							values.put( "minutes", 15 ); // 15 minuti
							if (Integer.parseInt(Build.VERSION.SDK) == 8 )
								cr.insert( Uri.parse( "content://com.android.calendar/reminders" ), values );
							else
								cr.insert( Uri.parse( "content://calendar/reminders" ), values );

						}
		        		Context context = getApplicationContext();
		        		CharSequence text = "Scadenza revisione aggiunta nel calendario";
		        		int duration = Toast.LENGTH_SHORT;

		        		Toast.makeText(context, text, duration).show();
						dialog.cancel();
					}

				});

				builder.create().show();
			}
			cursor.close();
		}
}
