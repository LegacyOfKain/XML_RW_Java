package com.priyanku.xml.stax;

public class MonitoredFolderDirectory {
	
	private String name;
	private String path;
	private int folderlimit;
	private int folderCount;
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
	public int getFolderlimit() {
		return folderlimit;
	}
	public void setFolderlimit(int folderlimit) {
		this.folderlimit = folderlimit;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getFolderCount() {
		return folderCount;
	}
	public void setFolderCount(int folderCount) {
		this.folderCount = folderCount;
	}
	
	@Override
	public String toString() {
		return "MonitoredFolderDirectory [name=" + name + ", path=" + path + ", folderlimit=" + folderlimit
				+ ", folderCount=" + folderCount + ", host=" + host + "]";
	}

	
}
