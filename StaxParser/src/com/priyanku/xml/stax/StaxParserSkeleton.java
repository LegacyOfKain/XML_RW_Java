package com.priyanku.xml.stax;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class StaxParserSkeleton {

	public static List<MonitoredFolderDirectory> parse(String path) {


		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream(path));


			Stack<QName> sk=new Stack<QName>();
	        QName monitoredFolderDirectories=new QName("monitoredFolderDirectories");    //if this is the parent looking for
	        QName add=new QName("add");    //this is the element targetting for
			
			for (int event = parser.next();event != XMLStreamConstants.END_DOCUMENT;event = parser.next()) 
			{
				switch (event) {
				case XMLStreamConstants.START_ELEMENT:
					System.out.print(parser.getLocalName());
					//System.out.print(parser.getAttributeCount());
					//disable below line for proper formatting of the xml
					//System.out.println(sk);
					
					if (add.equals(parser.getName())) {
	                    if (monitoredFolderDirectories.equals(sk.peek())) {
	                        //do thing you want to do on name element with parent advisory
	                    	int attributeCount = parser.getAttributeCount();
	                    	
	                    	IntStream.range(0, attributeCount).forEachOrdered(n -> {
	                    		System.out.print("--" + parser.getAttributeName(n).getLocalPart()+"--"   );
	                    	});
	                    } else {
	                        //do different thing if any on name element with parent other than advisory
	                    }
	                }
					
					sk.push(parser.getName());
					break;
				case XMLStreamConstants.END_ELEMENT:
					System.out.print(parser.getLocalName());
					
					sk.pop();
					break;
				case XMLStreamConstants.CHARACTERS:
					//This line prints the indentations for the xml also
					System.out.print(parser.getText());
					break;
				case XMLStreamConstants.CDATA:
					System.out.print(parser.getText());
					break;
				} // end switch
			} // end while
				parser.close();
		}
		catch (XMLStreamException ex) {
			ex.printStackTrace(); 
		}
		catch (IOException ex) {
			ex.printStackTrace(); 
		}
		
		return null;
	}

	public static void main(String args[])
	{
		parse("sample_AWM.xml");
        //.stream()
		//.forEach(System.out::println);
	}

}