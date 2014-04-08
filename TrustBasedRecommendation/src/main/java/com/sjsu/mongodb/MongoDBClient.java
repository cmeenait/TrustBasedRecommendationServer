package com.sjsu.mongodb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.sjsu.pojo.User;
import com.sjsu.utilities.DatabaseConstants;

public class MongoDBClient {

	MongoClient mongoClient = null;

	public MongoDBClient() throws UnknownHostException {
		mongoClient = new MongoClient(DatabaseConstants.MONGO_HOST_NAME, DatabaseConstants.MONGO_PORT_NUMBER);
		System.out.println("Connected To Mongo DB Successfully at Host " + DatabaseConstants.MONGO_HOST_NAME + " Port Number " + DatabaseConstants.MONGO_PORT_NUMBER);
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
			throw new IOException("Error adding the user with email id " + user.getEmail() + error);
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
		BasicDBObject searchQuery = new BasicDBObject().append("email", emailId);
		WriteResult result = collection.update(searchQuery, update);
		String error = result.getError();
		if (error != null) {
			throw new IOException("Error updating the user with email id " + emailId + error);
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
			throw new IOException("Error deleting the user with emailid " + emailId + error);
		}
		System.out.println("Number of Documents deleted is " + result.getN());

	}

	private DBCollection getUserCollection() {
		DB db = mongoClient.getDB(DatabaseConstants.DATABASE_NAME);
		DBCollection collection = db.getCollection(DatabaseConstants.USER_TABLE_NAME);
		return collection;
	}

	private void validateUser(String emailId) throws IOException {
		if (emailId == null || emailId.isEmpty()) {
			throw new IOException("Please enter the email id of the user");
		}

	}

}
