package files;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class methods {
	
	public static XmlPath rawToXml(Response res) {
		String response = res.asString();
		XmlPath x = new XmlPath(response);
		return x;
	}
	
	public static JsonPath rawToJson(Response res) {
		String responseString = res.asString();
		JsonPath js = new JsonPath(responseString);
		return js;
	}
	
	public static String generateStringFromResource(String path) throws IOException {
		return new String (Files.readAllBytes(Paths.get(path)));
	}
	
	public static String createDummyComment(String issueID) {
		//Create comment:
		Response res_create = 
			given().
				header("Content-Type", "application/json").
				header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
				body(payLoad.newComment("Dummy comment for update")).
			when().
				post(resources.jiraAddCommentEndPoint(issueID)).
			then().
				extract().response();
				
		//Take the ID
		JsonPath js = methods.rawToJson(res_create);
		String commentId = js.get("id");
		return commentId;
	}
	
	public static String createDummyTweet(String text, String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
		Response res =
				given().
					auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).
					queryParam("status", text).
				when().
					post(resources.newTweet()).
				then().
					statusCode(200).
				extract().
					response();
				
				JsonPath js = methods.rawToJson(res);
				String tweetId = js.getString("id");
				
				return tweetId;
	}
}