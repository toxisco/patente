package com.mono.patente;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.util.Log;

public class HtmlParser {
	
	// oggetto TagNode radice del file html
	private static TagNode rootNode;

	
	public static TagNode HtmlParser(HttpResponse url_str)
	{
		
		// inizializzazione dell'oggetto HtmlCleaner utile a generare un html pulito
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setAllowHtmlInsideAttributes(true);
		props.setAllowMultiWordAttributes(true);
		props.setRecognizeUnicodeChars(true);
		props.setOmitComments(true);
		 
		try {

			rootNode = cleaner.clean(new InputStreamReader(url_str.getEntity().getContent()));
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Log.e("Error", e.getMessage());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("Error", e.getMessage());
		}
		return rootNode;
	}
	

	
}