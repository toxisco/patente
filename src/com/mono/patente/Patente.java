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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Patente extends Activity {
	
    private final static String MY_PREFERENCES = "Login";
    ProgressDialog myProgressDialog = null;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	final SharedPreferences prefs = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String textData = prefs.getString("user", "");
        if(textData!="")
        {        
        	Intent i = new Intent(getBaseContext(), Punti.class);
        	finish();
        	startActivity(i);
        }else{
            setContentView(R.layout.main);
            Button launch = (Button)findViewById(R.id.login);
            launch.setOnClickListener( new OnClickListener()
            {
					@Override
                    public void onClick(View viewParam)
                    {
                            // this gets the resources in the xml file and assigns it to a local variable of type EditText
                    EditText usernameEditText = (EditText) findViewById(R.id.user);
                    EditText passwordEditText = (EditText) findViewById(R.id.pswd);
                   
                    String sUserName = usernameEditText.getText().toString();
                    String sPassword = passwordEditText.getText().toString();   
                    
                    SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("user", sUserName);
                        editor.putString("pswd", sPassword);
                        editor.commit();
                        
                        myProgressDialog = ProgressDialog.show(Patente.this,
                                "Attendi...", "Sto scaricando i dati dal server...", true);
               
                new Thread() {
                        public void run() {
                                try{
                                        // Scarica i dati
                            		Refresh.getData(getBaseContext());
                            		finish();
                                	Intent i = new Intent(getBaseContext(), Punti.class);
                                	startActivity(i);
                                } catch (Exception e) { }
                                // Dismiss the Dialog
                                myProgressDialog.dismiss();
                        }
                }.start();
                    	}
					

            });
            
            Button register = (Button)findViewById(R.id.register);
            register.setOnClickListener( new OnClickListener()
            {
            	@Override
                public void onClick(View viewParam)
                {
            		Intent i = new Intent(Intent.ACTION_VIEW);
            		Uri u = Uri.parse("https://www2.ilportaledellautomobilista.it/reg/app");
            		i.setData(u);
            		startActivity(i);
                }

            });
            
        	View aboutButton = findViewById(R.id.About);
        	aboutButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {

                	Dialog dialog = new Dialog(Patente.this); 
                	dialog.setContentView(R.layout.aboutdialog);
                	dialog.setTitle("About"); 
                	dialog.show();
                } 
             });
           }
        } 
    public void onResume(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

}