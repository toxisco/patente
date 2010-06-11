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

public class Veicoli extends Activity {
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.veicoli);
	        
			SharedPreferences prefsevento = getSharedPreferences("Veicoli0", Context.MODE_PRIVATE);
	        
	        ListView list = (ListView)findViewById(R.id.ListView01);
	        
	        List<StampaEventi> listOfEventi = new ArrayList<StampaEventi>();
	        
	    	for(int i=1;prefsevento.getString("Tipo","")!="";i++)
	    	{
	    		String veicolo = prefsevento.getString("Veicolo", "");
	    		String targa = prefsevento.getString("Targa","");
	    		String revisione = prefsevento.getString("Revisione","");
	    		String euro = prefsevento.getString("Euro","");
	    		String tipo = prefsevento.getString("Tipo","");
	    		String potenza = prefsevento.getString("Potenza","");

	    		listOfEventi.add(new StampaEventi(veicolo,targa,revisione,euro,potenza,tipo));

	    		
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
