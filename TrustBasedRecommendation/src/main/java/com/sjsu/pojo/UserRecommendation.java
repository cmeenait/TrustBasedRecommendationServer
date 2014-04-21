package com.sjsu.pojo;

import java.util.List;

public class UserRecommendation {
	
	
	
	private String email ;
	
	private List<Bookmark> bookmarksList ;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Bookmark> getBookmarksList() {
		return bookmarksList;
	}

	public void setBookmarksList(List<Bookmark> bookmarksList) {
		this.bookmarksList = bookmarksList;
	}
	

}
