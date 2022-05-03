package com.rirc.OSGI01;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class KDXMLRec01 {

	public enum ElEvent {
		START,
		END,
		DATA
	}
	
	private int recNo;
	public XMLStreamReader xmlr;
	final private List<String> aElem;
	public ElEvent elEvent;
	public  String name;
	public  String text;
	public  String endElement;

	private KDXMLRec01(XMLStreamReader xmlr, List<String> aElem) {
		super();
		this.xmlr = xmlr;
		this.aElem = aElem;
	}

	public int getAInt(String name) {
		String val= getAString(name); 
		return (val==null || val.isEmpty())? 0:Integer.parseInt(val);
	}

	public UUID getAUUID(String name) {
		String val= getAString(name); 
		return (val==null || val.isEmpty())? null:UUID.fromString(val);
	}
	
	public String getAString(String name) {
		return xmlr.getAttributeValue(null, name);
	}

	public boolean is(String ... names) {
		if (elEvent!=ElEvent.END) {
			int l= aElem.size();
			if (l!=names.length) return false;
			for (int i= 0; i< l; i++) {
				if (!aElem.get(i).equals(names[i])) return false;
			}
			return true;
		} else {
			int l= aElem.size();
			if ((l+1)!=names.length) return false;
			for (int i= 0; i< l; i++) {
				if (!aElem.get(i).equals(names[i])) return false;
			}
			return endElement.equals(names[l]);
		}
	}

	@Override
	public String toString() {
		
		StringBuilder sb= null;
		if (elEvent==ElEvent.START) { 
			for (int i= 0; i< xmlr.getAttributeCount(); i++) {
				if (i==0) sb= new StringBuilder();
				else sb.append(',');
				
				sb.append(xmlr.getAttributeLocalName(i) );
				sb.append(':');
				sb.append(xmlr.getAttributeValue(i) );
			}
		}
		
		return "XMLRec [recNo=" + recNo + ", aElem=" + aElem + ", elEvent=" + elEvent + ", name=" + name + ", text="
				+ text + ", endElement=" + endElement +( (sb==null)? "": ", Attribute="+sb )+ "]";
	}

	public static Iterable<KDXMLRec01> getRecs(Reader fxml) throws XMLStreamException, FactoryConfigurationError {
		XMLStreamReader xmlr= XMLInputFactory.newInstance().createXMLStreamReader(fxml);
		List<String> aElem= new ArrayList<String>();
		
		KDXMLRec01 _xmlRec= new KDXMLRec01(xmlr, aElem);

		return ()-> new Iterator<KDXMLRec01>() {
			int recNo;
			String lastElemName= null;

			@Override
			public boolean hasNext() {
				try {
					return xmlr.hasNext();
				} catch (XMLStreamException ex) {
					throw new RuntimeException(ex);
				}
			}
			
			@Override
			public KDXMLRec01 next() {
				try {
					while (xmlr.hasNext()) {
						int typeElem= xmlr.next();
						
						switch (typeElem) {
						case XMLStreamConstants.START_ELEMENT: {
							String elem= xmlr.getLocalName();
							aElem.add(elem);

							lastElemName= elem;
							
							_xmlRec.recNo= recNo++;
							_xmlRec.elEvent= ElEvent.START;
							_xmlRec.name= elem;
							_xmlRec.text= null;
							_xmlRec.endElement= elem;
							return _xmlRec;
						}
						case XMLStreamConstants.END_ELEMENT: {
							String elem= xmlr.getLocalName();
							int li= aElem.size()-1;
							if (aElem.get(li).equals(elem)) aElem.remove(li);
							else throw new XMLStreamException("Error end elem: "+elem);

							lastElemName= null;

							_xmlRec.recNo= recNo++;
							_xmlRec.elEvent= ElEvent.END;
							_xmlRec.name= elem;
							_xmlRec.text= null;
							_xmlRec.endElement= elem;
							return _xmlRec;
						}
						case XMLStreamConstants.CHARACTERS: {
							String text= xmlr.getText();
							if (lastElemName!=null && !KDStr.isNullOrEmpty(text)) {
								_xmlRec.recNo= recNo++;
								_xmlRec.elEvent= ElEvent.DATA;
								_xmlRec.name= lastElemName;
								_xmlRec.text= text;
								_xmlRec.endElement= null;
								return _xmlRec;
							}
						}
						break;
						}						
					}
					_xmlRec.recNo= recNo++;
					_xmlRec.elEvent= ElEvent.END;
					_xmlRec.endElement= "";
					return _xmlRec;
				} catch (XMLStreamException ex) {
					throw new RuntimeException(ex);
				}
			}
		};
	}
}
