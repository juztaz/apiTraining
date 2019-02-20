import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.methods;
import files.resources;
import files.twitterCleanUps;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class basics7_twitter {
	
	ArrayList<String> createdTweets = new ArrayList<String>();
	
	private String consumerKey = "L3htSJhwEMEmnVa5I9WVz9cs8";
	private String consumerSecret = "UqEDhIEVL0dAlDT2aBzcIgewsiaAbspTjwmC58tLbcvf0KDK75";
	private String accessToken = "1095676520260096000-lb9p1Gqj1XksdvAQAXVAfaJ1qbidkZ";
	private String tokenSecret = "eXH2s2wd46kATvhf2Nt2NyHhpThS9jr5HYwgEJR5F4oDo";
	private String newTweetText = "This was created from RestAssured";
	
	@AfterTest
	public void cleanUp() {
		if(!createdTweets.isEmpty()) {
			createdTweets.forEach((n) -> {
				twitterCleanUps.deleteTweet(n, consumerKey, consumerSecret, accessToken, tokenSecret);
				System.out.println("Deleted from clean up");
			});
		}
	}
	
	@BeforeTest
	public void getSession() throws IOException {		
	//Enable logging if failed:
	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
				
	//Set URI
	RestAssured.baseURI = resources.loadBaseURI("Twitter");
	}
	
	@Test
	public void getLattestTweet() {
		Response res =
		given().
			auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
			queryParam("count", "1").
		when().
			get(resources.timelineTweets()).
		then().
			statusCode(200).
		extract().
			response();
		
		//Take the body
		JsonPath js = methods.rawToJson(res);
		
		System.out.println(js.getString("text[0]"));
	}
	
	@Test
	public void createTweet() {
		Response res =
		given().
			auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
			queryParam("status", newTweetText).
		when().
			post(resources.newTweet()).
		then().
			statusCode(200).
		extract().
			response();
		
		JsonPath js = methods.rawToJson(res);
		String tweetId = js.getString("id");
		
		//Add to list for cleanup
		createdTweets.add(tweetId);
	}
	
	@Test
	public void deleteTweet() {
		//Create testing tweet
		String tweetId = methods.createDummyTweet("To be deleted", consumerKey, consumerSecret, accessToken, tokenSecret);
		
		given().
			auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
		when().
			post(resources.deleteTweet(tweetId)).
		then().
			statusCode(200);
	}

}
