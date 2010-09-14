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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.htmlcleaner.TagNode;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Refresh {
	
    private final static String MY_PREFERENCES = "Login";

    
	public static void getData(Context context)
	{
		HttpResponse r = null;
        String url = "https://www.ilportaledellautomobilista.it/http://vam.ilportaledellautomobilista.it:8080/amserver/UI/Login?goto=http://vps.ilportaledellautomobilista.it:8080/portal/gotohome.jsp&amp;scope=null";
        Map<String, String> accountValues = new HashMap<String, String>();
        
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        String user = prefs.getString("user", "");
        String pswd = prefs.getString("pswd", "");

        accountValues.put("IDToken1", user);
        accountValues.put("IDToken2", pswd);
    	try {
    			r = doPost(url, accountValues, context);
    		}
    		catch (Exception ex){
    			Log.e("!!!ERRORE!!!DoPost: ", ex.getMessage().toString());
    		}
    		            
            try {
            	
    			TagNode rootNode = HtmlParser.HtmlParser(r);

                /* passiamo il risultato alla TextView per visualizzarli. */
                Salva(rootNode, context);
            	
                 
            } catch (Exception e) {
                 /* In caso di errore, passiamo l'errore alla TextView. */
                 Log.e("ErrorHTMLParser", e.toString());
            }

	}
        
    	public static HttpResponse doPost(String url, Map<String, String> postData, Context context) throws ClientProtocolException, IOException {
    		HttpClient httpClient = getClient();
    		HttpPost httpPost = new HttpPost(url);
    		if (postData != null && postData.isEmpty() == false) {
    			List<NameValuePair> dataList = new ArrayList<NameValuePair>(postData.size());
    			Iterator<String> iKeys = postData.keySet().iterator();
    			while (iKeys.hasNext()) {
    				String k = iKeys.next();
    				String v = postData.get(k);
    				dataList.add(new BasicNameValuePair(k, v));
    			}
    			httpPost.setEntity(new UrlEncodedFormEntity(dataList));
    		}
    		httpClient.execute(httpPost);
    		
    		//Bisogna visitare prima questa pagina altrimenti da errore di sessione
    		String login2 = "https://www.ilportaledellautomobilista.it/http://voas.ilportaledellautomobilista.it:7777/cittadino/private/SaldoPuntiPatenteHome.page?invalidateSession=true";
    		try {
    			httpPost.setURI(new URI(login2));
    		} catch (URISyntaxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		//recupero il link all'estratto conto
    		HttpResponse conto = httpClient.execute(httpPost);
    		TagNode rootconto = HtmlParser.HtmlParser(conto);
    		TagNode[] elementi = rootconto.getElementsByAttValue("id", "linkEstrattoConto", true, true);
        	String link = elementi[0].getAttributeByName("href");
        	link = link.replaceAll("&amp;","&");
    		
    		//veicoli
    		String login3 = "https://www.ilportaledellautomobilista.it/http://voas.ilportaledellautomobilista.it:7777/cittadino/private/DatiSintesiVeicoliHome.page?invalidateSession=true";
    		try {
    			httpPost.setURI(new URI(login3));
    		} catch (URISyntaxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		Veicoli(httpClient.execute(httpPost), context);
    		
    		//Sommario e Eventi
    		String login4 = link;
    		try {
    			httpPost.setURI(new URI(login4));
    		} catch (URISyntaxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		return httpClient.execute(httpPost);
    	}
    	
    	
        private static void Veicoli(HttpResponse execute, Context context) {
        	try {
        		TagNode rootNode = HtmlParser.HtmlParser(execute);
	        	TagNode[] veicolo = rootNode.getElementsByAttValue("id", "tipoVeicoloTableData", true, false);
	        	TagNode[] targa = rootNode.getElementsByAttValue("id", "targaTableData", true, false);
	        	TagNode[] tipo = rootNode.getElementsByAttValue("id", "tipoCombustibileTableData", true, false);
	        	TagNode[] euro = rootNode.getElementsByAttValue("id", "compatibilitaTableData", true, false);
	        	TagNode[] potenza = rootNode.getElementsByAttValue("id", "potenzaTableData", true, false);
	        	TagNode[] revisione = rootNode.getElementsByAttValue("id", "dataScadenzaRevisioneTableData", true, false);
                
	        	for(int i=0;i<veicolo.length;i++)
                {
    	        	SharedPreferences veicoliprefs = context.getSharedPreferences("Veicoli"+i, Context.MODE_PRIVATE);
                	SharedPreferences.Editor editor = veicoliprefs.edit();
                	editor.putString("Veicolo", veicolo[0].getText().toString());
                	editor.putString("Targa", targa[0].getText().toString());
                	editor.putString("Tipo", tipo[0].getText().toString());
                	editor.putString("Euro", euro[0].getText().toString());
                	editor.putString("Potenza", potenza[0].getText().toString());
                	editor.putString("Revisione", revisione[0].getText().toString());
                	editor.commit();
                }
        	}
        	
		    catch(Exception e) {
                Log.e("ErrorV", e.toString());
		    }
		}

        
		/**
    	 * Creates a HttpClient with http and http parameters
    	 */
    	public static DefaultHttpClient getClient() {
            DefaultHttpClient ret = null;
            // Parameters
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, "utf-8");
            params.setBooleanParameter("http.protocol.expect-continue", false);
            // Schemes for http
            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            // Schemes for http and https
            final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            registry.register(new Scheme("https", sslSocketFactory, 443));
            // Client generation
            ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
            ret = new DefaultHttpClient(manager, params);
            return ret;
        }
    	
    	
    	public static void Salva(TagNode rootNode, Context context){//Salvo il SOmmario e gli Eventi

    		    try {   
    		        TagNode elementi = rootNode.findElementByAttValue("summary", "Estratto conto punti patente", true, true);
			        TagNode[] elementi1 = elementi.getElementsByName("td", true);
			    	SharedPreferences pref_sommario = context.getSharedPreferences("Sommario", Context.MODE_PRIVATE);
	                SharedPreferences.Editor editor_sommario = pref_sommario.edit();
	                editor_sommario.putString("Numero", elementi1[0].getText().toString());
	                editor_sommario.putString("DataNascita", elementi1[1].getText().toString());
	                editor_sommario.putString("Nome", elementi1[2].getText().toString());
	                editor_sommario.putString("Scadenza", elementi1[3].getText().toString());
	                editor_sommario.putString("Punti", elementi1[4].getText().toString());
	                GregorianCalendar gc = new GregorianCalendar();
	                //+1 al mese per questo lolloso "bug" :) http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4757002
	                int month = gc.get(gc.MONTH)+1;
	                editor_sommario.putString("Data", gc.get(gc.DATE)+"-"+month+"-"+gc.get(gc.YEAR));
	                editor_sommario.commit();
    		        
    		        TagNode node2 = rootNode.findElementByAttValue("summary", "Elenco movimenti punti patente", true, true);
    		        
    		        TagNode movimenti = node2.findElementByAttValue("id", "tableBodyMovimentoPunti", true, false);
    		        

    		        for(int i=0;movimenti!=null;i++)
    		        {
    			        TagNode[] tdMovimenti = movimenti.getElementsByName("td", true);
    			        
    			    	SharedPreferences prefs = context.getSharedPreferences("Evento"+i, Context.MODE_PRIVATE);
    	                SharedPreferences.Editor editor = prefs.edit();
    	                editor.putString("Data", tdMovimenti[0].getText().toString());
    	                editor.putString("Punti+", tdMovimenti[1].getText().toString());
    	                editor.putString("Punti-", tdMovimenti[2].getText().toString());
    	                editor.putString("Descrizione", tdMovimenti[3].getText().toString());
    	                editor.putString("DataEvento", tdMovimenti[4].getText().toString());
    	                editor.putString("Ente", tdMovimenti[5].getText().toString());
    	                editor.commit();
    			        
    	                movimenti = node2.findElementByAttValue("id", "tableBodyMovimentoPunti_"+i, true, false);
    		        }
    		    }
    		    catch(Exception e) {
    		        e.printStackTrace();
    		    }

    	}
}
