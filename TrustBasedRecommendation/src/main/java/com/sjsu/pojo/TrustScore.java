package com.sjsu.pojo;

public class TrustScore {
	
	
	private String userId ;
	private String friendId ;
	private String categoryName ;
	private double  trustScore ;
	private String explicit ;
	public String getUserEmailId() {
		return userId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userId = userEmailId;
	}
	public String getFriendEmailId() {
		return friendId;
	}
	public void setFriendEmailId(String friendEmailId) {
		this.friendId = friendEmailId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	

	public double getTrustScore() {
		return trustScore;
	}
	public void setTrustScore(double trustScore) {
		this.trustScore = trustScore;
	}
	public String getExplicit() {
		return explicit;
	}
	public void setExplicit(String explicit) {
		this.explicit = explicit;
	}
	public String toString()
	{
		return categoryName + " " + trustScore + " " +  userId + " " + explicit + friendId;
		
	}

}
