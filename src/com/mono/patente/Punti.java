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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Punti extends Activity {
	
    ProgressDialog myProgressDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Handler handler = new Handler();
        handler.post(Dati);                 

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
	    case R.id.Refresh://Aggiorno i dati
	        final Handler handler = new Handler();
            myProgressDialog = ProgressDialog.show(Punti.this,
                    "Attendi...", "Sto scaricando i dati dal server...", true);
            new Thread() {
            	public void run() {
    				// Scarica i dati
            		Refresh.getData(getBaseContext());
                        handler.post(Dati);//aggiorno la view              
                    myProgressDialog.dismiss();
            	}
            }.start();
	    	break;
	    }
	    return false;
	}
	
	Runnable Dati = new Runnable() {
        
        public void run() {
            setContentView(R.layout.punti);
            
    		SharedPreferences prefsevento = getSharedPreferences("Evento0", Context.MODE_PRIVATE);
    		SharedPreferences prefsveicolo = getSharedPreferences("Veicoli0", Context.MODE_PRIVATE);
    		int i=0;
    		int z=0;
        	for(;prefsevento.getString("Data","")!="";i++)
        	{
        		prefsevento = getSharedPreferences("Evento"+i, Context.MODE_PRIVATE);
        	}
        	for(;prefsveicolo.getString("Veicolo","")!="";z++)
        	{
        		prefsveicolo = getSharedPreferences("Veicoli"+z, Context.MODE_PRIVATE);
        	}
        	if(z==0)	z=1;

    		SharedPreferences prefs = getSharedPreferences("Sommario", Context.MODE_PRIVATE);
    		String numero = prefs.getString("Numero", "");
    		String nascita = prefs.getString("DataNascita", "");
    		String nome = prefs.getString("Nome", "");
    		String scadenza = prefs.getString("Scadenza", "");
    		String punti = prefs.getString("Punti", "");
    		String data = prefs.getString("Data", "");
    		
            TextView nometext = (TextView) findViewById(R.id.nome);
            TextView puntitext = (TextView) findViewById(R.id.punti);
            TextView nascitatext = (TextView) findViewById(R.id.nascita);
            TextView numerotext = (TextView) findViewById(R.id.numero);
            TextView scadenzatext = (TextView) findViewById(R.id.scadenza);
            
            TextView nomevar = (TextView) findViewById(R.id.nomevar);
            TextView puntivar = (TextView) findViewById(R.id.puntivar);
            TextView nascitavar = (TextView) findViewById(R.id.nascitavar);
            TextView numerovar = (TextView) findViewById(R.id.numerovar);
            TextView scadenzavar = (TextView) findViewById(R.id.scadenzavar);
            TextView variazionevar = (TextView) findViewById(R.id.variazionevar);
            TextView veicolivar = (TextView) findViewById(R.id.veicolivar);
            TextView datatext = (TextView) findViewById(R.id.data);

    		nometext.setText("Nome");
    		puntitext.setText("Punti");
    		nascitatext.setText("Data di Nascita");
    		numerotext.setText("Numero Patente");
    		scadenzatext.setText("Scadenza");
    		
    		nomevar.setText(nome);
    		puntivar.setText(punti);
    		nascitavar.setText(nascita);
    		numerovar.setText(numero);
    		scadenzavar.setText(scadenza);
    		variazionevar.setText(i-1+" Variazioni");
    		veicolivar.setText(z-1+" Veicoli");
    		datatext.setText(" "+data);
    		
    		Button eventiDettagli = (Button) findViewById(R.id.eventidettagli);
    		Button veicoliDettagli = (Button) findViewById(R.id.veicolidettagli);
    		eventiDettagli.setOnClickListener( new OnClickListener()
            {
    				@Override
                    public void onClick(View viewParam)
                    {
                    	Intent e = new Intent(getBaseContext(), Eventi.class);
                    	startActivity(e);
                    }
            });
    		if(z!=1)
    		{
	    		veicoliDettagli.setOnClickListener( new OnClickListener()
	            {
	    				@Override
	                    public void onClick(View viewParam)
	                    {
	                    	Intent v = new Intent(getBaseContext(), Veicoli.class);
	                    	startActivity(v);
	                    }
	            });
    		}else
    		{
    			veicoliDettagli.setClickable(false);
    		}
        }
	};
}
