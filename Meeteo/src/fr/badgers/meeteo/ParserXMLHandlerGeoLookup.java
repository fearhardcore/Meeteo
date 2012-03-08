package fr.badgers.meeteo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@SuppressWarnings("unused")
public class ParserXMLHandlerGeoLookup extends DefaultHandler {

	private final String ERROR = "error";
	private final String WMO = "wmo";
	private final String CITY = "city";
	private final String COUNTRY= "country_name";
	
	private ArrayList<Location> entries;

	private Location currentLocation;

	// Boolean to know if we're in an item
	private boolean inCurr;
	private boolean error;

	// Buffer for data in XML tag
	private StringBuffer buffer;

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		super.processingInstruction(target, data);
	}

	public ParserXMLHandlerGeoLookup() {
		super();
	}

	// * Cette m�thode est appel�e par le parser une et une seule
	// * fois au d�marrage de l'analyse de votre flux xml.
	// * Elle est appel�e avant toutes les autres m�thodes de l'interface,
	// * � l'exception unique, �videmment, de la m�thode setDocumentLocator.
	// * Cet �v�nement devrait vous permettre d'initialiser tout ce qui doit
	// * l'�tre avant led�but du parcours du document.

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		entries = new ArrayList<Location>();
	}

	/*
	 * Fonction �tant d�clench�e lorsque le parser trouve un tag XML C'est cette
	 * m�thode que nous allons utiliser pour instancier un nouveau feed
	 */

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		// Nous r�initialisons le buffer a chaque fois qu'il rencontre un item
		buffer = new StringBuffer();

		// Ci dessous, localName contient le nom du tag rencontr�

		// Nous avons rencontr� un tag ITEM, il faut donc instancier un nouveau
		// feed
		if (localName.equalsIgnoreCase(ERROR)) {
			error = true;
		}
	}

	// * Fonction �tant d�clench�e lorsque le parser � pars�
	// * l'int�rieur de la balise XML La m�thode characters
	// * a donc fait son ouvrage et tous les caract�re inclus
	// * dans la balise en cours sont copi�s dans le buffer
	// * On peut donc tranquillement les r�cup�rer pour compl�ter
	// * notre objet currentFeed

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		// if (localName.equalsIgnoreCase(TITLE)){
		// if(inItem){
		// // Les caract�res sont dans l'objet buffer
		// this.currentFeed.setTitle(buffer.toString());
		// buffer = null;
		// }
		// }
		if (localName.equalsIgnoreCase(CITY))
		{
			currentLocation.setName(buffer.toString());
		}
		
		if (localName.equalsIgnoreCase(COUNTRY))
		{
			currentLocation.setCountry(buffer.toString());
		}
	}

	// * Tout ce qui est dans l'arborescence mais n'est pas partie
	// * int�grante d'un tag, d�clenche la lev�e de cet �v�nement.
	// * En g�n�ral, cet �v�nement est donc lev� tout simplement
	// * par la pr�sence de texte entre la balise d'ouverture et
	// * la balise de fermeture

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String lecture = new String(ch, start, length);
		if (buffer != null)
			buffer.append(lecture);
	}

	// cette m�thode nous permettra de r�cup�rer les donn�es
	public ArrayList<Location> getData() {
		if (error)
			return null;
		return entries;
	}
}
