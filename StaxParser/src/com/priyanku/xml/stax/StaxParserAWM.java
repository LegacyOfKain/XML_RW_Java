package com.priyanku.xml.stax;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.namespace.QName;

public class StaxParserAWM {

	public static List<MonitoredFolderDirectory> parse(String path) {
		
		List<MonitoredFolderDirectory> monitoredFolderDirectories = new ArrayList<>();
		MonitoredFolderDirectory monitoredFolderDirectory = null;
		
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
			
			while (reader.hasNext()) {
				
				XMLEvent nextEvent = reader.nextEvent();
				//System.out.println("First Event Type = "+nextEvent.getEventType());
				
				if (nextEvent.isStartElement()) {
					// StartElement = <...>
					StartElement startElement = nextEvent.asStartElement();
					switch (startElement.getName()
							.getLocalPart()) {
							case "add":
								monitoredFolderDirectory = new MonitoredFolderDirectory();
								//System.out.println("website Start Event Type = "+nextEvent.getEventType());
								Attribute host = startElement.getAttributeByName(new QName("host"));
								if (host != null) {
									monitoredFolderDirectory.setHost(host.getValue());
								}
								break;
								/*
							case "name":
								nextEvent = reader.nextEvent();
								//System.out.println("name Start Event Type = "+nextEvent.getEventType());
								website.setName(nextEvent.asCharacters()
										.getData());
								break;
							case "category":
								nextEvent = reader.nextEvent();
								website.setCategory(nextEvent.asCharacters()
										.getData());
								break;
							case "status":
								nextEvent = reader.nextEvent();
								website.setStatus(nextEvent.asCharacters()
										.getData());
								break;
								*/
					}
				}
				// EndElement = </...>
				if (nextEvent.isEndElement()) {
					EndElement endElement = nextEvent.asEndElement();
					if (endElement.getName()
							.getLocalPart()
							.equals("website")) {
						monitoredFolderDirectories.add(monitoredFolderDirectory);
					}
				}
			}
		} catch (XMLStreamException xse) {
			System.out.println("XMLStreamException");
			xse.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			System.out.println("FileNotFoundException");
			fnfe.printStackTrace();
		}
		return monitoredFolderDirectories;
	}
	
	public static void main(String args[])
	{
		parse("sample_AWM.xml").stream()
							.forEach(System.out::println);
	}

}