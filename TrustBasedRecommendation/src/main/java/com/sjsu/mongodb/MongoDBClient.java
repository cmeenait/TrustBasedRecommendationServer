package com.sjsu.mongodb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.sjsu.pojo.Bookmark;
import com.sjsu.pojo.TrustScore;
import com.sjsu.pojo.User;
import com.sjsu.pojo.UserRecommendation;
import com.sjsu.pojo.UserRecommendationAttributes;
import com.sjsu.utilities.DatabaseConstants;

public class MongoDBClient {

	MongoClient mongoClient = null;

	public MongoDBClient() throws UnknownHostException {
		mongoClient = new MongoClient(DatabaseConstants.MONGO_HOST_NAME,
				DatabaseConstants.MONGO_PORT_NUMBER);
		System.out.println("Connected To Mongo DB Successfully at Host "
				+ DatabaseConstants.MONGO_HOST_NAME + " Port Number "
				+ DatabaseConstants.MONGO_PORT_NUMBER);
	}

	public void listAllDatabases() {

		List<String> databaseNamesList = mongoClient.getDatabaseNames();

		for (String db : databaseNamesList) {
			System.out.println(db);
		}
	}

	public void addUser(User user) throws IOException {

		validateUser(user.getEmail());
		BasicDBObject document = new BasicDBObject();
		document.put("email", user.getEmail());
		document.put("city", user.getCity());
		document.put("name", user.getName());
		document.put("zip", user.getZip());

		DBCollection collection = getUserCollection();
		WriteResult result = collection.insert(document);
		String error = result.getError();
		if (error != null) {
			throw new IOException("Error adding the user with email id "
					+ user.getEmail() + error);
		}

	}

	public void updateUser(User user) throws IOException {

		String emailId = user.getEmail();
		validateUser(emailId);
		DBObject history = new BasicDBObject();
		history.put("city", user.getCity());
		history.put("name", user.getName());
		history.put("zip", user.getZip());

		DBObject update = new BasicDBObject("$set", history);

		DBCollection collection = getUserCollection();

		// search with email id and then udpate the object
		BasicDBObject searchQuery = new BasicDBObject()
				.append("email", emailId);
		WriteResult result = collection.update(searchQuery, update);
		String error = result.getError();
		if (error != null) {
			throw new IOException("Error updating the user with email id "
					+ emailId + error);
		}
		System.out.println("Number of Documents updated is " + result.getN());

	}

	public void deleteUser(User user) throws IOException {

		String emailId = user.getEmail();
		validateUser(emailId);
		BasicDBObject deleteDocument = new BasicDBObject();
		deleteDocument.put("email", emailId);

		DBCollection collection = getUserCollection();
		WriteResult result = collection.remove(deleteDocument);
		String error = result.getError();
		if (error != null) {
			throw new IOException("Error deleting the user with emailid "
					+ emailId + error);
		}
		System.out.println("Number of Documents deleted is " + result.getN());

	}

	private DBCollection getUserCollection() {
		DB db = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
		DBCollection collection = db
				.getCollection(DatabaseConstants.USER_TABLE_NAME);
		return collection;
	}

	private void validateUser(String emailId) throws IOException {
		if (emailId == null || emailId.isEmpty()) {
			throw new IOException("Please enter the email id of the user");
		}

	}

	// **********************************************************

	public void closeConnection() {

	}

	public List<TrustScore> getTopTrustedFriendsinCategory(User user,
			List<String> categoryList) throws IOException {

		DBCollection collection = getTrustScoreCollection();

		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();

		obj.add(new BasicDBObject("user", user.getEmail()));
		obj.add(new BasicDBObject("category", new BasicDBObject("$in",
				categoryList)));

		andQuery.put("$and", obj);

		System.out.println(andQuery.toString());

		DBObject sort = new BasicDBObject("trustscore", -1);

		DBCursor cursor = collection.find(andQuery).sort(sort);

		List<TrustScore> trustScoreList = new ArrayList<TrustScore>();

		try {
			while (cursor.hasNext()) {

				DBObject dbobj = cursor.next();
				TrustScore trustscore = new TrustScore();
				trustscore.setUserEmailId(String.valueOf(dbobj.get("user")));
				trustscore
						.setFriendEmailId(String.valueOf(dbobj.get("friend")));
				trustscore.setCategoryName((String) dbobj.get("category"));
				trustscore.setTrustScore((Double) dbobj.get("trustscore"));
				trustscore.setExplicit((String) dbobj.get("explicit"));
				System.out.println(trustscore.toString());
				trustScoreList.add(trustscore);
			}
		} finally {
			cursor.close();
		}
		return trustScoreList;

	}

	private DBCollection getTrustScoreCollection() {

		DB db = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
		DBCollection collection = db
				.getCollection(DatabaseConstants.TRUSTSCORE_TABLE_NAME);
		return collection;
	}

	public ArrayList<String> getMostBookmarkedCategory(User user) {

		// db.TrustScoreCollection.aggregate( { $match : {
		// user : 100 } }, {$group : { _id : "$category" , total :{$sum : 1}}},
		// { $sort :{total : -1}} );
		ArrayList<String> categoryList = null;
		try {
			validateUser(user.getEmail());
			DBCollection collection = getBookmarkCollection();

			DBObject match = new BasicDBObject("$match", new BasicDBObject(
					"email", user.getEmail()));

			DBObject fields = new BasicDBObject("category", 1);
			fields.put("total", 1);
			fields.put("_id", 0);
			DBObject project = new BasicDBObject("$project", fields);

			DBObject groupFields = new BasicDBObject("_id", "$category");
			groupFields.put("total", new BasicDBObject("$sum", 1));
			DBObject group = new BasicDBObject("$group", groupFields);

			DBObject sort = new BasicDBObject("$sort", new BasicDBObject(
					"total", -1));

			DBObject limit = new BasicDBObject("$limit", 3);

			AggregationOutput output = collection.aggregate(match, project,
					group, sort, limit);

			categoryList = new ArrayList<String>();

			for (DBObject obj : output.results()) {
				// DBObject obj = list.iterator().next();
				String id = (String) obj.get("_id");
				int total = Integer.parseInt(obj.get("total").toString());
				categoryList.add(id);
				System.out.println("ID IS " + id + " total: " + total);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return categoryList;

	}

	private DBCollection getBookmarkCollection() {
		DB db = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
		DBCollection collection = db
				.getCollection(DatabaseConstants.BOOKMARK_TABLE_NAME);
		return collection;

	}

	public void getCategoryListforUser(User user) {
		DBCollection collection = getBookmarkCollection();
		// BasicDBObject andQuery = new BasicDBObject();

		DBObject query = new BasicDBObject("email", user.getEmail());

		collection.find(query);
		
		


	}

	public List<Bookmark> getBookmarksfromTopTrustedFriends(User user,
			List<TrustScore> trustScoreList) {
//TODO get status only liked 
		List<String> categoryList = new ArrayList<String>();
		List<String> FriendsList = new ArrayList<String>();
		for (int i = 0; i < trustScoreList.size(); i++) {
			TrustScore trustScore = trustScoreList.get(i);
			categoryList.add(trustScore.getCategoryName());
			FriendsList.add(trustScore.getFriendEmailId());
		}

		DBCollection collection = getBookmarkCollection();

		BasicDBObject andQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();

		obj.add(new BasicDBObject("email",
				new BasicDBObject("$in", FriendsList)));
		obj.add(new BasicDBObject("category", new BasicDBObject("$in",
				categoryList)));

		andQuery.put("$and", obj);

		System.out.println(andQuery.toString());

		DBCursor cursor = collection.find(andQuery);
		List<Bookmark> bookmarksList = new ArrayList<Bookmark>();

		while (cursor.hasNext()) {
			// System.out.println(cursor.next()) ;

			DBObject dbobj = cursor.next();
			Bookmark bookmark = new Bookmark();

			bookmark.setName(String.valueOf(dbobj.get("name")));
			bookmark.setLocation((String) dbobj.get("location"));
			bookmark.setCategory((String) dbobj.get("category"));
			bookmark.setStats((String) dbobj.get("status"));
			System.out.println(bookmark.toString());
			bookmarksList.add(bookmark);
		}
		
		return bookmarksList ; 

	}
	
	
	
	
	
public List<Bookmark>  getBookmarksfromUser(List<String> frndEmailList)
{
	//Todo get only status = liked 
	DBCollection collection = getBookmarkCollection();
	
	List<Bookmark> bookmarksList = new ArrayList<Bookmark>();
	BasicDBObject inQuery = new BasicDBObject();

	inQuery.put("email", new BasicDBObject("$in", frndEmailList));
	DBCursor cursor = collection.find(inQuery);
	while(cursor.hasNext()) {
		DBObject dbobj = cursor.next();
		Bookmark bookmark = new Bookmark();

		bookmark.setName(String.valueOf(dbobj.get("name")));
		bookmark.setLocation((String) dbobj.get("location"));
		bookmark.setCategory((String) dbobj.get("category"));
		bookmark.setStats((String) dbobj.get("status"));
		System.out.println(bookmark.toString());
		bookmarksList.add(bookmark);
	}
	
	return bookmarksList ; 
}
	
	

	public UserRecommendationAttributes getAllBookmarkCategoryCountForUser(User user) {
		
		
		
		System.out.println(user.getEmail());
		DBCollection collection = getBookmarkCollection();

		ArrayList<String> categoryList = null;
		
		DBObject match = new BasicDBObject("$match", new BasicDBObject(
				"email",user.getEmail()));
		
		
		DBObject fields = new BasicDBObject("category", 1);
		fields.put("total", 1);
		fields.put("_id", 0);
		DBObject project = new BasicDBObject("$project", fields);
		
		
		DBObject groupFields = new BasicDBObject("_id", "$category");
		groupFields.put("total", new BasicDBObject("$sum", 1));
		DBObject group = new BasicDBObject("$group", groupFields);
		
		
		

		AggregationOutput output = collection.aggregate(match, project,
				group);
		
		

		categoryList = new ArrayList<String>();
		
		UserRecommendationAttributes  userBookmark = new  UserRecommendationAttributes(); 
		userBookmark.setUserEmailId(user.getEmail());
		HashMap<String, Integer> categoryCount = new HashMap<String, Integer>();
		for (DBObject obj : output.results()) {
			// DBObject obj = list.iterator().next();
			String id = (String) obj.get("_id");
			int total = Integer.parseInt(obj.get("total").toString());
			
			categoryCount.put(id, total);
		}
		
		userBookmark.setCategoryCountMap(categoryCount);
		return userBookmark;

	}

	public List<User> getAllUserandFrndsofSystem() {

		DBCollection collection = getUserCollection();
		
		List<User>  users = new ArrayList<User>();

		BasicDBObject query = new BasicDBObject();

		BasicDBObject field = new BasicDBObject();
		
		field.put("email", 1);
		field.put("friends" , 1) ;

		DBCursor cursor = collection.find(query, field);

		while (cursor.hasNext()) {
			//System.out.println(cursor.next());
			User user = new User();
			DBObject dbobj = cursor.next();
			
			user.setEmail((String) dbobj.get("email"));
			user.setFriendsList((List<String>) dbobj.get("friends"));
			
			users.add(user);
		}

		// TODO Auto-generated method stub
		return users;
	}

	
	
	
	public void populateUserRecommendation(
			List<UserRecommendation> userRecommendationList) throws IOException {
		DBCollection collection = getRecommendationCollection();
		
		for(int i = 0 ; i <userRecommendationList.size() ; i++  )
		{
		BasicDBObject document = new BasicDBObject();
		document.append("email", userRecommendationList.get(i).getEmail());
		
		List<Bookmark>  bookmarksList = userRecommendationList.get(i).getBookmarksList() ;
		
		List<BasicDBObject> bookmarkdocList = new ArrayList<BasicDBObject>();
		
		for(int k =0 ; k <bookmarksList.size() ;k++ )
		{
		Bookmark tempBookmark = bookmarksList.get(k);
		
		BasicDBObject bookmarkdoc = new BasicDBObject();
		
		bookmarkdoc.append("name " , tempBookmark.getName() ) ;
		bookmarkdoc.append("location " ,tempBookmark.getLocation());
		bookmarkdoc.append("stats " ,tempBookmark.getStats());
		bookmarkdoc.append("category " ,tempBookmark.getCategory());
		bookmarkdocList.add(bookmarkdoc);
		
		}
		document.append("bookmarks",   bookmarkdocList ) ;

		
		WriteResult result = collection.insert(document);
		String error = result.getError();
		if (error != null) {
			throw new IOException("Error adding the user with email id "
					+ userRecommendationList.get(i).getEmail()+ error);
		}
		
		}
		
	}

	private DBCollection getRecommendationCollection() {
		DB db = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
		DBCollection collection = db
				.getCollection(DatabaseConstants.RECOMMENDATION_TABLE_NAME);
		return collection;
	}

	
	

	
	
public List<Bookmark>  getRecommendationsforUser(User user)
{
	//Todo get only status = liked 
	DBCollection collection = getRecommendationCollection();
	
	List<Bookmark> bookmarksList = new ArrayList<Bookmark>();
	BasicDBObject inQuery = new BasicDBObject();

	inQuery.put("email",user.getEmail());
	
	
	DBObject fields = new BasicDBObject("bookmarks", 1);
	
	DBCursor cursor = collection.find(inQuery,  fields );
	while(cursor.hasNext()) {
		DBObject dbobj = cursor.next();
		bookmarksList  = (List<Bookmark>) dbobj.get("bookmarks"); 
		/*Bookmark bookmark = new Bookmark();

		bookmark.setName(String.valueOf(dbobj.get("name")));
		bookmark.setLocation((String) dbobj.get("location"));
		bookmark.setCategory((String) dbobj.get("category"));
		bookmark.setStats((String) dbobj.get("status"));
		System.out.println(bookmark.toString());
		bookmarksList.add(bookmark); */
	}
	
	for (int i = 0 ; i <bookmarksList.size() ; i++ )
	{
		Bookmark bookmark = (Bookmark)bookmarksList.get(i);
		System.out.println(bookmark.toString()) ; 
	}
	
	return bookmarksList ; 
}

public List<Bookmark> getPopularRecommendationinCategory(
		List<String> categoryList) {
	DBCollection collection = getBookmarkCollection();
	
	List<Bookmark> bookmarksList = new ArrayList<Bookmark>();
	BasicDBObject inQuery = new BasicDBObject();

	inQuery.put("category", new BasicDBObject("$in", categoryList));
	
	
	
	
	

	BasicDBObject orderBy = new BasicDBObject() ;
	orderBy.put("stats", -1);
	
	DBCursor cursor = collection.find(inQuery).sort(orderBy);

	
	while(cursor.hasNext()) {
		DBObject dbobj = cursor.next();
		Bookmark bookmark = new Bookmark();

		bookmark.setName(String.valueOf(dbobj.get("name")));
		bookmark.setLocation((String) dbobj.get("location"));
		bookmark.setCategory((String) dbobj.get("category"));
		bookmark.setStats((String) dbobj.get("stats"));
		bookmark.setStatus((String) dbobj.get("status"));
		bookmark.setTried((Boolean) dbobj.get("tried"));
		System.out.println(bookmark.toString());
		bookmarksList.add(bookmark);
	}
	
	return bookmarksList ; 
}
}
