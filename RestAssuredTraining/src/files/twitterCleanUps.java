package files;

import static io.restassured.RestAssured.given;

public class twitterCleanUps {
	
	public static void deleteTweet(String tweetId, String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
	given().
		auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
	when().
		post(resources.deleteTweet(tweetId)).
	then().
		statusCode(200);
	}

}
