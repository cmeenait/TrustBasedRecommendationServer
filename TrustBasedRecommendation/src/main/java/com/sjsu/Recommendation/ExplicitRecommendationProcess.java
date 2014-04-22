package com.sjsu.Recommendation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.sjsu.mongodb.MongoDBClient;
import com.sjsu.pojo.Bookmark;
import com.sjsu.pojo.TrustScore;
import com.sjsu.pojo.User;
import com.sjsu.pojo.UserRecommendation;

public class ExplicitRecommendationProcess {

	MongoDBClient mongoClient;

	ExplicitRecommendationProcess() {
		init();
	}

	private void init() {
		try {
			mongoClient = new MongoDBClient();

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}

	}

	public void   userExplictionRecommendationProcess() {
		List<User> usersList = mongoClient.getAllUserandFrndsofSystem();
		
		List<UserRecommendation>  userRecommendationList = new ArrayList<UserRecommendation>();

		for (int i = 0; i < usersList.size(); i++) {
			UserRecommendation userRecommendation  = calculateExplicitRecommendation(usersList.get(i));
			if(userRecommendation.getBookmarksList().size() != 0  )
			{
			userRecommendationList.add(userRecommendation);
			}
		}
		
		populateUserRecommedationinDB(userRecommendationList);
		

	}
	
	
	private void populateUserRecommedationinDB(
			List<UserRecommendation> userRecommendationList) {
		
		try {
			mongoClient.populateUserRecommendation(userRecommendationList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private UserRecommendation calculateExplicitRecommendation(User user) {

		UserRecommendation tempUserRecommendation = null;

		List<String> categoryList = mongoClient.getMostBookmarkedCategory(user);

		try {

			List<TrustScore> trustScoreList;
			trustScoreList = mongoClient.getTopTrustedFriendsinCategory(user,
					categoryList);

			List<Bookmark> bookmarksList = mongoClient
					.getBookmarksfromTopTrustedFriends(user, trustScoreList);

			tempUserRecommendation = new UserRecommendation();
			tempUserRecommendation.setEmail(user.getEmail());
			tempUserRecommendation.setBookmarksList(bookmarksList);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return tempUserRecommendation;

	}

}
