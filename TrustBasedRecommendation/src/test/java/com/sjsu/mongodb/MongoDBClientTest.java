package com.sjsu.mongodb;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Ignore;
import org.junit.Test;

import com.sjsu.pojo.User;

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

}
