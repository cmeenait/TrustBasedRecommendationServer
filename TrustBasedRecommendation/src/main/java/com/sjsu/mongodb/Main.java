package com.sjsu.mongodb;
  
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
 
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
  
/**
 * Java + MongoDB Simple Example
 *
 */
public class Main {
     
 
    static String array_names[] = {"John","Tim","Brit","Robin","Smith","Lora","Jennifer","Lyla","Victor","Adam"};
     
    static String array_address[][] ={
        {"US", "FL", " Miami"},
        {"US", "FL", " Orlando"},
        {"US", "CA", "San Diego"},
        {"US", "FL", " Orlando"},
        {"US", "FL", " Orlando"},
        {"US", "NY", "New York"},
        {"US", "NY", "Buffalo"},
        {"US", "TX", " Houston"},
        {"US", "CA", "San Diego"},
        {"US", "TX", " Houston"}
    };
     
  public static void main(String[] args) {
  
    try {
   
    // Connect to mongodb
    MongoClient mongo = new MongoClient("localhost", 27017);
  
    // get database
    // if database doesn't exists, mongodb will create it for you
    DB db = mongo.getDB("bookmark");
  
    // get collection
    // if collection doesn't exists, mongodb will create it for you
    DBCollection collection = db.getCollection("UserCollection");
  
    /**** Insert ****/
    // create a document to store key and value
     
    BasicDBObject document ;
    BasicDBObject friends ;
    BasicDBObject itemobject ;
    String bookmarks[];
    for(int i = 0 ; i < array_names.length ; i++){
        document = new BasicDBObject();
        //value -> String
        document.append("name", array_names[i]);
        // value -> int
        document.append("city", "San Jose");
        //value -> int
        document.append("zip", 95112);
     // value -> string
        document.append("email", "sowmistergmail.com");
        // value -> date
        document.append("join", new Date());
        /*// value -> array
        document.append("friends", pickFriends());*/
        
        itemobject = new BasicDBObject();
        itemobject.append("location","san jose");
        itemobject.append("category", "restraurant");
        itemobject.append("stats", "203 likes");
        
        //bookmarks = pickbookmarks();
        document.append("bookmarks", new BasicDBObject("item",itemobject)
        .append("tried", true)
        .append("status", "Bad"));
        
        friends = new BasicDBObject();
        //value -> String
        friends.append("uid", "53276076d404d5ee3d67f755");
        // value -> int
        friends.append("trustscore", 2);
        // value -> date
        friends.append("join", new Date());
       document.append("friends", friends);
        
        collection.insert(document);
 
    }
     
     
    // get count
    System.out.println("All Persons: "+collection.getCount());
    //------------------------------------
    // get all document
    DBCursor cursor = collection.find();
    try {
       while(cursor.hasNext()) {
           System.out.println(cursor.next());
       }
    } finally {
       cursor.close();
    }
    //------------------------------------
 
    // get documents by query
    BasicDBObject query = new BasicDBObject("age", new BasicDBObject("$gt", 40));
 
    cursor = collection.find(query);
    System.out.println("Person with age > 40 --> "+cursor.count());
     
  
    /**** Update ****/
    //update documents found by query "age > 30" with udpateObj "age = 20"
    BasicDBObject newDocument = new BasicDBObject();
    newDocument.put("age", 20);
  
    BasicDBObject updateObj = new BasicDBObject();
    updateObj.put("$set", newDocument);
  
    collection.update(query, updateObj,false,true);
  
    /**** Find and display ****/
    cursor = collection.find(query);
    System.out.println("Person with age > 40 after update --> "+cursor.count());
     
     
    //get all again
    cursor = collection.find();
    try {
       while(cursor.hasNext()) {
           System.out.println(cursor.next());
       }
    } finally {
       cursor.close();
    }
  
    /**** Done ****/
    System.out.println("Done");
  
    } catch (UnknownHostException e) {
    e.printStackTrace();
    } catch (MongoException e) {
    e.printStackTrace();
    }
  
  }
  private static Object pickitemDetails() {
	// TODO Auto-generated method stub
	return null;
}
private static String[] pickbookmarks() {
	  int random = (int) (Math.random()*10);
	  return array_address[random];
}
//----------------------------------------------------
  //These methods are just jused to build random data
  private static String[] pickFriends(){
      int numberOfFriends = (int) (Math.random()* 10);
      LinkedList<String> friends = new LinkedList<String>();
      int random = 0;
      while(friends.size() < numberOfFriends){
          random = (int) (Math.random()*10);
          if(!friends.contains(array_names[random]))
              friends.add(array_names[random]);
               
      }
      String a[] = {};
      return  friends.toArray(a);
  }
  private static String[] pickAddress(){
      int random = (int) (Math.random()*10);
      return array_address[random];
  }
}