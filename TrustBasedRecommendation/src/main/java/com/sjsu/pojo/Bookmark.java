package com.sjsu.pojo;

import java.io.Serializable;

public class Bookmark implements Serializable{
	
	public String name ;
	 public String  location ;
	 public String category ;
	 public String stats ;
	 public Boolean tried ;
	 public String status;
	 
	 public Bookmark() {

		}
	 
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	public Boolean getTried() {
		return tried;
	}
	public void setTried(Boolean tried) {
		this.tried = tried;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String toString(){
		return  " " +  name +
				" " +  location +
		 " " +category +
		" " +  stats +
		 " " + tried +
		" " +   status ;
	}

}
