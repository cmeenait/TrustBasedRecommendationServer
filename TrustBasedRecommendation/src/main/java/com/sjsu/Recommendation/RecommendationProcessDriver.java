package com.sjsu.Recommendation;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sjsu.pojo.User;
import com.sjsu.pojo.UserRecommendation;

public class RecommendationProcessDriver {

	public static void main(String[] args) throws UnknownHostException {
		
		
		ExplicitRecommendationProcess explictrecommendationProcess = new ExplicitRecommendationProcess();
		
		explictrecommendationProcess.userExplictionRecommendationProcess(); 
		
		
		
		//implicit reco 
		
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

}
