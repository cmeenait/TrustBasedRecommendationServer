package com.sjsu.Recommendation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.sjsu.mongodb.MongoDBClient;
import com.sjsu.pojo.Bookmark;
import com.sjsu.pojo.FriendSimilarityValue;
import com.sjsu.pojo.User;
import com.sjsu.pojo.UserRecommendation;
import com.sjsu.pojo.UserRecommendationAttributes;

public class ImplicitRecommendationProcess {

	static List<String> AVAIL_CATEGORIES_SYSTEM = new ArrayList<String>();
	CosineSimilarity cosinesimilarity;
	UserRecommendationAttributes recoAttr;
	HashMap<String, UserRecommendationAttributes> useRecommendationAttrMap;

	MongoDBClient mongoClient;

	ImplicitRecommendationProcess() throws UnknownHostException {
		init();
		cosinesimilarity = new CosineSimilarity();
		useRecommendationAttrMap = new HashMap<String, UserRecommendationAttributes>();

		mongoClient = new MongoDBClient();

	}

	/*public static void main(String[] args) throws UnknownHostException {

		ImplicitRecommendationProcess implicitRecoProcess = new ImplicitRecommendationProcess();

		List<User> usersList = implicitRecoProcess
				.getAllUserandFrndsofSystemfromDB();

		implicitRecoProcess.useRecommendationAttrMap = implicitRecoProcess
				.processDataPopulateMap(usersList);

		

		implicitRecoProcess.useRecommendationAttrMap = implicitRecoProcess
				.findCosineSimiliartyofFrnds(usersList);

		for (Map.Entry entry : implicitRecoProcess.useRecommendationAttrMap.entrySet()) {
		    System.out.print("key,val: ");
		    System.out.println(entry.getKey() + "," + entry.getValue());
		}
		
		
		
		List<UserRecommendation>  userRecommendationList=  implicitRecoProcess.getBookmarkRecommendationforUser(usersList);
		
		
		//Find out the explict recommendations already entered in the system . 
		
		List<String >  usersExplicitRecoList = implicitRecoProcess.FindExplictRecommendationUsers();
		
		HashMap <String , String > usersExplicitRecoListMap =  new HashMap<String ,String>();
		
		for(int i = 0  ;i < usersExplicitRecoList.size() ; i++ )
		{
			usersExplicitRecoListMap.put(usersExplicitRecoList.get(i), usersExplicitRecoList.get(i)); 
		}
		
		for(int i = 0 ;i <userRecommendationList.size() ; i++)
		{
			if(usersExplicitRecoListMap.containsKey(userRecommendationList.get(i).getEmail()))
			{
				userRecommendationList.remove(i);
				i--;
			}
		}
		
		
		
		
		implicitRecoProcess.populateUserRecommedationinDB(userRecommendationList);
		 

	}
*/
	public List<String > FindExplictRecommendationUsers() {
		List<String >  usersList  = mongoClient.findUserinRecommendation() ;
		return usersList;
		
	}

	public void populateUserRecommedationinDB(
			List<UserRecommendation> userRecommendationList) {
		
		try {
			mongoClient.populateUserRecommendation(userRecommendationList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public List<UserRecommendation> getBookmarkRecommendationforUser(List<User> usersList) {
		// TODO Auto-generated method stub
		List<UserRecommendation>  userRecommendationList =  new ArrayList<UserRecommendation>();
		for (int i =0 ;i < usersList.size() ; i++)
		{
			User user = usersList.get(i);
			UserRecommendationAttributes userRecoAttr = useRecommendationAttrMap.get(user.getEmail());
			
			 Map <String , Double > FrndsSimilarityValueMap = userRecoAttr.getFrndSimilarityValue();
			 
			 List<String>  frndsEmailList =  new ArrayList<String>();
			 for (Map.Entry entry : FrndsSimilarityValueMap.entrySet()) {
				
				     String Frndemail = (String) entry.getKey()  ;
				     
				     //getbookamrk for that frnd 
				     
				     frndsEmailList.add(Frndemail);
				     
				     
				}
			// @SuppressWarnings("unchecked")
			//List<String>  frndsEmailList = (List<String>) FrndsSimilarityValueMap.keySet();
			 
			 List<Bookmark>  bookmarksList = getBookmarksforUserfromDB(frndsEmailList);
			 
			 UserRecommendation tempUserRecommendation = new UserRecommendation();
			 tempUserRecommendation.setEmail(user.getEmail());
			 tempUserRecommendation.setBookmarksList(bookmarksList);
			 userRecommendationList.add(tempUserRecommendation);
		}
		
		return userRecommendationList;
		
	}

	private static void init() {
		// TODO Auto-generated method stub

		AVAIL_CATEGORIES_SYSTEM.add("doctor");
		AVAIL_CATEGORIES_SYSTEM.add("house");

		AVAIL_CATEGORIES_SYSTEM.add("movies");
		AVAIL_CATEGORIES_SYSTEM.add("restaurant");
		AVAIL_CATEGORIES_SYSTEM.add("Universites");

	}

	public List<User> getAllUserandFrndsofSystemfromDB() {
		List<User> usersList = mongoClient.getAllUserandFrndsofSystem();
		return usersList;
	}

	public HashMap<String, UserRecommendationAttributes> processDataPopulateMap(
			List<User> usersList) {
		ArrayList<Integer> categoriesList = null;

		for (int i = 0; i < usersList.size(); i++) {

			recoAttr = getCategoryCountForUserfromDB(usersList.get(i));

			// Populate thh empty categories to 0

			HashMap<String, Integer> categoryCountMap = recoAttr
					.getCategoryCountMap();
			categoriesList = new ArrayList<Integer>();

			for (int k = 0; k < AVAIL_CATEGORIES_SYSTEM.size(); k++) {
				if (categoryCountMap
						.containsKey(AVAIL_CATEGORIES_SYSTEM.get(k))) {
					categoriesList.add(categoryCountMap
							.get(AVAIL_CATEGORIES_SYSTEM.get(k)));
				} else {
					categoriesList.add(0);
				}

			}
			recoAttr.setCategories(categoriesList);

			useRecommendationAttrMap.put(recoAttr.getUserEmailId(), recoAttr);
		}
		return useRecommendationAttrMap;
	}

	public HashMap<String, UserRecommendationAttributes> findCosineSimiliartyofFrnds(
			List<User> usersList) {
		for (int k = 0; k < usersList.size(); k++) {
			User user = usersList.get(k);

			ArrayList<Integer> userCategoryArray = useRecommendationAttrMap
					.get(user.getEmail()).getCategories();

			List<String> FriendsList = user.getFriendsList();

			HashMap<String, Double> frndSimilarityValueMap = new HashMap<String, Double>();

			for (int j = 0; j < FriendsList.size(); j++) {
				String tempFriendEmail = FriendsList.get(j);

				ArrayList<Integer> friendCategoryArray = useRecommendationAttrMap
						.get(tempFriendEmail).getCategories();
				double similarityValue = cosinesimilarity.cosineSimilarity(
						userCategoryArray, friendCategoryArray);

				frndSimilarityValueMap.put(tempFriendEmail, similarityValue);
			}

			// sort the frnd similarity value based on values

			Map<String, Double> sortedMap = sortTheMap(frndSimilarityValueMap);

			UserRecommendationAttributes userbookmark = useRecommendationAttrMap
					.get(user.getEmail());

			userbookmark.setFrndSimilarityValue(sortedMap);

		}
		return useRecommendationAttrMap;

	}

	public UserRecommendationAttributes getCategoryCountForUserfromDB(User user) {
		UserRecommendationAttributes recoAttr = mongoClient
				.getAllBookmarkCategoryCountForUser(user);
		return recoAttr;
	}
	
	
	public List<Bookmark>  getBookmarksforUserfromDB(List<String> frndemailList) {
		
		List<Bookmark>  bookmarkList =mongoClient.getBookmarksfromUser(frndemailList);
		
		return bookmarkList ;
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sortTheMap(HashMap<String, Double> frndSimilarityValueMap) {
		List list = new LinkedList(frndSimilarityValueMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

}
