package com.priyanku.xml.stax;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.IntStream;
import java.util.ArrayList;

import com.google.common.primitives.Ints;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.namespace.QName;

public class StaxParserAWM {

	public static List<MonitoredFolderDirectory> parse(String path) {

		List<MonitoredFolderDirectory> monitoredFolderDirectoriesList = new ArrayList<>();
		//MonitoredFolderDirectory monitoredFolderDirectory = null;

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
	                    	
	                    	// make the pojo effectively final (all variables are by default in java 8 final
	                    	// if they are not changed after invocation, for eg: as shown below
	                    	// int variable = 123;
	                    	// variable = 456;
	                    	MonitoredFolderDirectory monitoredFolderDirectory = new MonitoredFolderDirectory();
	                    	//IntStream.range(0, attributeCount).forEachOrdered(n -> {
	                    	IntStream.range(0, attributeCount).parallel().forEach(n -> {
	                    		String attributeName = parser.getAttributeName(n).getLocalPart();
	                    		String attributeValue = parser.getAttributeValue(n);
	                    		if(attributeName.equalsIgnoreCase("host"))
	                    		{
	                    			monitoredFolderDirectory.setHost(attributeValue);
	                    		}
	                    		else if(attributeName.equalsIgnoreCase("key"))
	                    		{
	                    			monitoredFolderDirectory.setName(attributeValue);
	                    		}
	                    		else if(attributeName.equalsIgnoreCase("folderdirectory"))
	                    		{
	                    			monitoredFolderDirectory.setPath(attributeValue);
	                    			monitoredFolderDirectory.setFolderCount(0);
	                    			File folderDir = new File (attributeValue);
	                    			if(folderDir.exists() && folderDir.isDirectory())
	                    			{
	                    				monitoredFolderDirectory.setFolderCount(
				                    				folderDir.listFiles(new FileFilter() {
				                    				    @Override
				                    				    public boolean accept(File file) {
				                    				    	// isDirectory is not that accurate, isFile returns false if file does not exist or if it is a directory
				                    				        return ( !file.isFile() && 
				                    				        		!(file.getName().equalsIgnoreCase("error")
				                    				        				||
				                    				        				file.getName().equalsIgnoreCase("save")
				                    				        		)
				                    				        	);
				                    				    }
				                    				}).length
	                    				);
	                    			}
	                    			
	                    		}
	                    		else if(attributeName.equalsIgnoreCase("folderlimit"))
	                    		{
	                    			monitoredFolderDirectory.setFolderlimit(
	                    						Optional.ofNullable(attributeValue)
	                    								.map(Ints::tryParse)
	                    								.orElse(0)
	                    								);
	                    		}
	                    		
	                    	});
	                    	monitoredFolderDirectoriesList.add(monitoredFolderDirectory);
	                    	
	                    	
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
		
		return monitoredFolderDirectoriesList;
	}

	public static void main(String args[])
	{
		
		parse("sample_AWM.xml")
        .stream()
        .filter(e->e!=null)
        .filter(e->e.getFolderCount()>e.getFolderlimit())
        //print new line
        .peek(e->System.out.println())
		.forEach(System.out::println);
	}

}