package com.sjsu.pojo;

import java.util.ArrayList;
import java.util.List;

public class FourSquareVenue {

	private String findLatitude;
	private String findLongititude;
	private String findNearVenueCity;
	private List<String> venueNameList = new ArrayList<String>();
	private List<String> venueAddressList = new ArrayList<String>();
	private List<String> venueCityList = new ArrayList<String>();
	private List<String> venueCategoryList = new ArrayList<String>();
	public String getFindLatitude() {
		return findLatitude;
	}
	public void setFindLatitude(String findLatitude) {
		this.findLatitude = findLatitude;
	}
	public String getFindLongititude() {
		return findLongititude;
	}
	public void setFindLongititude(String findLongititude) {
		this.findLongititude = findLongititude;
	}
	public String getFindNearVenueCity() {
		return findNearVenueCity;
	}
	public void setFindNearVenueCity(String findNearVenueCity) {
		this.findNearVenueCity = findNearVenueCity;
	}
	public List<String> getVenueNameList() {
		return venueNameList;
	}
	public void setVenueNameList(List<String> venueNameList) {
		this.venueNameList = venueNameList;
	}
	public List<String> getVenueAddressList() {
		return venueAddressList;
	}
	public void setVenueAddressList(List<String> venueAddressList) {
		this.venueAddressList = venueAddressList;
	}
	public List<String> getVenueCityList() {
		return venueCityList;
	}
	public void setVenueCityList(List<String> venueCityList) {
		this.venueCityList = venueCityList;
	}
	public List<String> getVenueCategoryList() {
		return venueCategoryList;
	}
	public void setVenueCategoryList(List<String> venueCategoryList) {
		this.venueCategoryList = venueCategoryList;
	}
	

	
}
