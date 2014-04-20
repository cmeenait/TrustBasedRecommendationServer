package com.sjsu.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserBookmark {
	
	private  String userEmailId ;
	private HashMap<String , Integer>  categoryCountMap ;
	private ArrayList<Integer>  categoriesCountList ; 
	
	private HashMap <String , Double >  frndSimilarityValue ; 
	
 
	
	
	
	
	public HashMap<String, Integer> getCategoryCount() {
		return categoryCountMap;
	}
	public void setCategoryCount(HashMap<String, Integer> categoryCount) {
		this.categoryCountMap = categoryCount;
	}
	public ArrayList<Integer> getCategories() {
		return categoriesCountList;
	}
	public void setCategories(ArrayList<Integer> categories) {
		this.categoriesCountList = categories;
	}
	public HashMap<String, Double> getFrndSimilarityValue() {
		return frndSimilarityValue;
	}
	public void setFrndSimilarityValue(HashMap<String, Double> frndSimilarityValue) {
		this.frndSimilarityValue = frndSimilarityValue;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	
	public String toString()
	{
		return  userEmailId + " " + categoriesCountList.get(0) + " " + categoriesCountList.get(1) + " " ;
	}
	

}
