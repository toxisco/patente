package com.mono.patente;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

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
	    		
	    		listOfEventi.add(new StampaEventi(desc,data,punti,dataevento,ente));

	    		
	    		prefsevento = getSharedPreferences("Evento"+i, Context.MODE_PRIVATE);
	    	}
	        
	    	StampaEventiAdapter adapter = new StampaEventiAdapter(this, listOfEventi);
	        
	        list.setAdapter(adapter);
	 }
}
