package com.sjsu.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRecommendationAttributes {
	
	private  String userEmailId ;
	private HashMap<String , Integer>  categoryCountMap ;
	private ArrayList<Integer>  categoriesCountList ; 
	
	private Map <String , Double >  frndSimilarityValueMap ; 
	
	
	
	
 
	
	
	
	

	
	public Map<String, Double> getFrndSimilarityValue() {
		return frndSimilarityValueMap;
	}
	public void setFrndSimilarityValue(Map<String, Double> frndSimilarityValue) {
		this.frndSimilarityValueMap = frndSimilarityValue;
	}
	public HashMap<String, Integer> getCategoryCountMap() {
		return categoryCountMap;
	}
	public void setCategoryCountMap(HashMap<String, Integer> categoryCount) {
		this.categoryCountMap = categoryCount;
	}
	public ArrayList<Integer> getCategories() {
		return categoriesCountList;
	}
	public void setCategories(ArrayList<Integer> categories) {
		this.categoriesCountList = categories;
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
