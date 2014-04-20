package com.sjsu.mongodb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.sjsu.pojo.FourSquareVenue;
import com.sjsu.pojo.TrustScore;
import com.sjsu.pojo.User;
import com.sjsu.restservices.FourSquareService;

public class MongoDBClientTest {

	@Test
	public void addUser() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			User user = new User();
			user.setCity("santaclara");
			user.setName("meena");
			user.setEmail("cmeenait@gmail.com");
			user.setZip(95051);

			mongoClient.addUser(user);

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}

	}

	@Test
	public void updateUser() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();

			User user = new User();
			user.setCity("mountainview");
			user.setName("meenalakshmanan");
			user.setEmail("cmeenait@gmail.com");
			user.setZip(92004);
			mongoClient.updateUser(user);

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			// assertTrue(false);
			e.printStackTrace();
		}
	}

	@Test
	public void deleteUser() {

		try {
			MongoDBClient mongoClient = new MongoDBClient();

			User user = new User();

			user.setEmail("cmeenait@gmail.com");
			mongoClient.deleteUser(user);

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			// assertTrue(false);
			e.printStackTrace();
		}

	}
	
	
	
	
	
	@Test
	public void getMostBookmarkedCategory() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			User user = new User();
			user.setCity("santaclara");
			user.setName("meena");
			user.setEmail("sowmistergmail.com");
			user.setZip(95051);

			mongoClient.getMostBookmarkedCategory(user);
			

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}

	}
	
	
	
	
	
	@Test
	public void getTopTrustedFriendsinCategory() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			User user = new User();
			user.setCity("santaclara");
			user.setName("meena");
			user.setEmail("sowmistergmail.com");
			user.setZip(95051);
			
			
			List<String> categoryList =new ArrayList<String>();
			categoryList.add("movies");
			categoryList.add("restaurants");

			mongoClient.getTopTrustedFriendsinCategory(user, categoryList);
			

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	@Test
	public void getBookmarksfromTopTrustedFriendsTest() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			User user = new User();
			user.setCity("santaclara");
			user.setName("meena");
			user.setEmail("sowmistergmail.com");
			user.setZip(95051);
			
			
			List<String> categoryList =new ArrayList<String>();
			categoryList.add("movies");
			categoryList.add("restaurants");
			
			
			
			List<TrustScore> trustScoreList  = new ArrayList<TrustScore>(); 
			
			
				TrustScore trustScore = new TrustScore();
				trustScore.setCategoryName("house");
				trustScore.setFriendEmailId("sowmistergmail.com");
				
				trustScoreList.add(trustScore);
				
				TrustScore trustScore1 = new TrustScore();
				trustScore1.setCategoryName("movies");
				trustScore1.setFriendEmailId("sowmistergmail.com");
				trustScoreList.add(trustScore1);
				
			

			mongoClient.getBookmarksfromTopTrustedFriends(user, trustScoreList);
			

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}

	}
	
	
	
	
	@Test
	public void getAllUserandFrndsofSystemTest() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			
				
			

			mongoClient.getAllUserandFrndsofSystem();
			

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}

	}
	
	
	
	
	@Test
	public void getAllBookmarkCategoryCountForUserTest() {
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			
				User user = new User();
				//user.setId("53389baec2e6e0681db8768e");
			user.setEmail("cmeena@gmail.com");

			mongoClient.getAllBookmarkCategoryCountForUser(user);
			

		} catch (UnknownHostException e) {
			assertTrue(false);
			e.printStackTrace();
		} catch (IOException e) {
			assertTrue(false);
			e.printStackTrace();
		}

	}
	
	



}
