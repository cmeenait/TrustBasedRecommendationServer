package com.sjsu.restservices;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sjsu.mongodb.MongoDBClient;
import com.sjsu.pojo.User;

@Path("/restservice")
public class RestService {

	@POST
	@Path("/adduser")
	@Consumes("application/json")
	public Response addUser(User user)
	{
		String failedMessage = "failed to add user";
		String successMessage = "user successfully added";
		
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			mongoClient.addUser(user);
		} catch (UnknownHostException e) {
			return Response.status(400).entity(failedMessage).build();
		} catch (IOException e) {
			return Response.status(400).entity(failedMessage).build();
		}
		
		//200 denotes it is success
		return Response.status(200).entity(successMessage).build();
		//String output = user.toString();
		 //return Response.status(200).entity(output).build();

	}//end of addUser()
	
	
	@POST
	@Path("/edituser")
	@Consumes("application/json")
	public Response editUser(User user)
	{
		String failedMessage = "failed to edit user";
		String successMessage = "user successfully edited";
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			mongoClient.updateUser(user);
		} catch (UnknownHostException e) {
			return Response.status(400).entity(failedMessage).build();
		} catch (IOException e) {
			return Response.status(400).entity(failedMessage).build();
		}
		
		//200 denotes it is success
		return Response.status(200).entity(successMessage).build();

	}//end of editUser()
	
	
	@POST
	@Path("/deleteuser")
	@Consumes("application/json")
	public Response deleteUser(User user)
	{
		String failedMessage = "failed to delete user";
		String successMessage = "user successfully deleted";
		try {
			MongoDBClient mongoClient = new MongoDBClient();
			mongoClient.deleteUser(user);
		} catch (UnknownHostException e) {
			return Response.status(400).entity(failedMessage).build();
		} catch (IOException e) {
			return Response.status(400).entity(failedMessage).build();
		}
		
		//200 denotes it is success
		return Response.status(200).entity(successMessage).build();
		

	}//end of deleteUser()
	
	
	
	
	@GET
	@Path("/test")
	public Response test()
	{
		String result = "<h1>RESTful Demo Application</h1> !!";
		return Response.status(200).entity(result).build();
	}
	
	
	
	
	
	//sample function for production json
	@GET
	@Path("/print")
	@Produces("application/json")
	public User produceJSON()
	{
		User user = new User();
		user.setName("jsonName");
		user.setZip(95051);
		
		return user;
	}
	
	
	
	
	
	
	
	
	
	
}
