package fr.badgers.meeteo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class Parser {
	static public Parser parser;
	
	private static URL url = null;
	//private DefaultHandler handler;
	
	public Parser(String urlString){
	}
	
	public static ArrayList<Condition> getData(String urlString) throws IOException{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = null;
		ArrayList<Condition> entries = null;
		try{
			parser = factory.newSAXParser();
			
		}
		catch(ParserConfigurationException e){
			e.printStackTrace();
		}
		catch(SAXException e){
			e.printStackTrace();
		}
		
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		DefaultHandler handler = new ParserXMLHandlerCC();
		try {
			// On parse le fichier XML
			InputStream input = url.openStream();
			if(input==null)
				Log.e("erreur android","null");
			else{
				parser.parse(input, handler);
				// On récupère directement la liste des feeds
				entries = ((ParserXMLHandlerCC) handler).getData();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return entries;
	}
}
