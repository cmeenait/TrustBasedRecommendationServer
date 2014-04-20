package com.sjsu.ImplicitRecommendation;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sjsu.mongodb.MongoDBClient;
import com.sjsu.pojo.User;
import com.sjsu.pojo.UserBookmark;

public class ImplicitRecommendationProcess {

	public static void main(String[] args) {
		
		List<String > AVAIL_CATEGORIES_SYSTEM = new ArrayList<String>();
		
		CosineSimilarity  cosinesimilarity = new CosineSimilarity();
		
		UserBookmark  userBookmark  ;
		HashMap<String , UserBookmark>  useBookmarkMap = new HashMap <String , UserBookmark > ();
		
		AVAIL_CATEGORIES_SYSTEM.add("doctor");
		AVAIL_CATEGORIES_SYSTEM.add("house");
		
		AVAIL_CATEGORIES_SYSTEM.add("movies");
		AVAIL_CATEGORIES_SYSTEM.add("restraurant");
		AVAIL_CATEGORIES_SYSTEM.add("Universites"); 
		
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			
			
			List<User> usersList =mongoClient.getAllUserandFrndsofSystem();
			
			ArrayList<Integer > categoriesList = null;
			
			for(int i = 0 ;i < usersList.size() ; i++)
			{
			
				  userBookmark =	mongoClient.getAllBookmarkCategoryCountForUser(usersList.get(i));
				if(userBookmark.getCategoryCount() != null )
				{
				HashMap<String , Integer>  categoryCountMap =	userBookmark.getCategoryCount() ;
				 categoriesList = new ArrayList<Integer>();
				
				for( int k = 0 ; k < AVAIL_CATEGORIES_SYSTEM.size() ; k++ )
				{
					if(categoryCountMap.containsKey(AVAIL_CATEGORIES_SYSTEM.get(k)))
					{
						categoriesList.add(categoryCountMap.get(AVAIL_CATEGORIES_SYSTEM.get(k)));
					}
					else
					{
						categoriesList.add(0);
					}
				}
				}
				userBookmark.setCategories(categoriesList);
				
				
				useBookmarkMap.put(userBookmark.getUserEmailId() , userBookmark);
			}
			
			for(int k = 0 ;  k < usersList.size() ; k++)
			{
				User user = usersList.get(k);
				
				ArrayList<Integer> UserArray  = useBookmarkMap.get(user.getEmail()).getCategories();
				
				List<String>  FriendsList = user.getFriendsList() ;
				
				HashMap <String , Double >  frndSimilarityValueMap = new HashMap <String , Double>();   
				for (int j = 0; j < FriendsList.size(); j++)
				{
					String tempFriendEmail = FriendsList.get(j) ;
					
					
					ArrayList<Integer> FriendArray  = useBookmarkMap.get(tempFriendEmail).getCategories();
					double similarityValue = cosinesimilarity.cosineSimilarity(UserArray, FriendArray);
					
					frndSimilarityValueMap.put(tempFriendEmail, similarityValue);
				}
				
				
				for (Map.Entry entry : frndSimilarityValueMap.entrySet()) {
					  
				    System.out.println(entry.getKey() + "," + entry.getValue());
				}
				UserBookmark userbookmark = useBookmarkMap.get(user.getEmail());
				
				userbookmark.setFrndSimilarityValue(frndSimilarityValueMap);
				
			}
		
			
			
			
		
		
			for (Map.Entry entry : useBookmarkMap.entrySet()) {
			  
			    System.out.println(entry.getKey() + "," + entry.getValue());
			}

			
			
			
		} catch (UnknownHostException e) {
						e.printStackTrace();
		}
		
	}

}
