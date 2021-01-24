package com.priyanku.xml.stax;

public class MonitoredFolderDirectory {
	
	private String name;
	private String path;
	private String folderlimit;
	private String host;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFolderlimit() {
		return folderlimit;
	}
	public void setFolderlimit(String folderlimit) {
		this.folderlimit = folderlimit;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	@Override
	public String toString() {
		return "MonitoredFolderDirectory [name=" + name + ", path=" + path + ", folderlimit=" + folderlimit + ", host="
				+ host + "]";
	}

	
}
