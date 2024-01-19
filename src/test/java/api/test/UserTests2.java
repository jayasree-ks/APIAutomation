package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPointsFromFile;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	Faker faker;
	User userPayload;
	
	public Logger logger;

	@BeforeClass

	public void setUpData() {

		faker = new Faker();
		userPayload = new User();
		
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setPassword(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());

	}

	@Test
	public void testPostUser() {
		logger.info("*****************Craeting User**************");
		
		Response response = UserEndPointsFromFile.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
			
		logger.info("*****************User is created**************");
	}

	@Test
	public void testGetUserByName() {
		logger.info("*****************Reading User**************");
		
		Response response = UserEndPointsFromFile.readUser(this.userPayload.getUsername());
		System.out.println(this.userPayload.getUsername());
		response.then().log().all();
		response.statusCode();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("*****************User is dsiplayed**************");

	}

	@Test
	public void testUpdateUserByName() {
		
		//update data using payload

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setPassword(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPointsFromFile.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);

		// checking after updating data
		Response responseAfterupdate = UserEndPointsFromFile.readUser(this.userPayload.getUsername());
		System.out.println(this.userPayload.getUsername());

		responseAfterupdate.then().log().all();
		responseAfterupdate.statusCode();
	}


	@Test
	
	public void testDeleteUserByName() {
		Response response=UserEndPointsFromFile.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		
	}


}
