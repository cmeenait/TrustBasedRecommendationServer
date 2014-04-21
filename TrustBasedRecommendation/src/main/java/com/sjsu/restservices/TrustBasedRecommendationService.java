package com.sjsu.restservices;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sjsu.mongodb.MongoDBClient;
import com.sjsu.pojo.Bookmark;
import com.sjsu.pojo.TrustScore;
import com.sjsu.pojo.User;

@Path("/trustbasedtecommendationservice")
public class TrustBasedRecommendationService {

	@GET
	@Path("/getexplicittrustrecommendation")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getExplicitRecommendation(User user) {
		String failedMessage = "failed to get explicit recommendation";
		String successMessage = "got explicit recommentation  ";
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			
			List<String> categoryList = mongoClient.getMostBookmarkedCategory( user);
			
			
			
			
		List<TrustScore> trustScoreList = 	 mongoClient.getTopTrustedFriendsinCategory(user ,categoryList);
		
	
		
		List<Bookmark> bookmarksList = mongoClient.getBookmarksfromTopTrustedFriends(user , trustScoreList);
		
		} catch (UnknownHostException e) {
			return Response.status(400).entity(failedMessage).build();
		} catch (IOException e) {
			return Response.status(400).entity(failedMessage).build();
		}

		// 200 denotes it is success
		return Response.status(200).entity(successMessage).build();

	}
	
	
	
	
	
	
	
	

	@GET
	@Path("/getimplicittrustrecommendation")
	@Consumes("application/json")
	@Produces("application/json")
	public Response getimplicitRecommendation(User user) {
		String failedMessage = "failed to get implicit recommendation";
		String successMessage = "got implicit recommentation  ";
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			
			List<Bookmark> bookmarksList = mongoClient.getRecommendationsforUser(user);
			
			
			for (int i = 0 ; i <bookmarksList.size() ; i++ )
			{
				Bookmark bookmark = (Bookmark)bookmarksList.get(i);
				System.out.println(bookmark.toString()) ; 
			}
		
		} catch (UnknownHostException e) {
			return Response.status(400).entity(failedMessage).build();
		} catch (IOException e) {
			return Response.status(400).entity(failedMessage).build();
		}

		// 200 denotes it is success
		return Response.status(200).entity(successMessage).build();

	}

}
