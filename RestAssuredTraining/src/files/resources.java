package files;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.DatatypeConverter;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class resources {
	
	public static String postEndPoint() {
		String res = "/Library/Addbook.php";
		return res;
	}
	
	public static String getEndPoint(String id) {
		String res = "/Library/GetBook.php?ID="+ id + "";
		return res;
	}
	
	public static String deleteEndPoint() {
		String res = "/Library/DeleteBook.php";
		return res;
	}
	
	public static String xmlAddEndPoint() {
		String res = "/maps/api/place/add/xml";
		return res;
	}
	
	public static String jiraIssueEndPoint() {
		String res = "/rest/api/2/issue";
		return res;
	}
	
	public static String jiraAddCommentEndPoint(String issueID) {
		String res =  "/rest/api/2/issue/" + issueID + "/comment";
		return res;
	}
	
	public static String jiraUpdateCommentEndPoint(String issueID, String commentId) {
		String res =  "/rest/api/2/issue/" + issueID + "/comment/" + commentId;
		return res;
	}
	
	public static String loadBaseURI(String baseUrl) throws IOException {
		//Load props
		Properties props = new Properties();
		FileInputStream fis = new FileInputStream("/Users/justinasz/eclipse-workspace/RestAssuredTraining/src/files/env.properties");
		props.load(fis);
		
		if(baseUrl == "Jira") {
			RestAssured.baseURI = props.getProperty("JIRA");
		} else if(baseUrl == "Twitter") {
			RestAssured.baseURI = props.getProperty("TWITTER");
		} else {
			RestAssured.baseURI = props.getProperty("HOST");
		}
		
		return RestAssured.baseURI;
	}
	
	public static List<String> getSession(String username, String password) throws IOException {

		List<String> sessionInfo = new ArrayList<String>();
		
		//Load props
		RestAssured.baseURI = loadBaseURI("Jira");
		
		//Auth
		Response res = given().
			header("Content-Type", "application/json").
			body("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }").
		when().
			post("/rest/auth/1/session").
		then().
			statusCode(200).
		extract().response();
		
		//Get values
		JsonPath js = methods.rawToJson(res);
		String sessionName = js.get("session.name");
		String sessionValue = js.getString("session.value");
		
		//Add them to list
		sessionInfo.add(sessionName);
		sessionInfo.add(sessionValue);
		
		//Return info
		return sessionInfo;
	}
	
	public static String encodedAuth(String username, String password) {
		//Make modifications
		String usernameValue = username + ":" + password;
		String encoded = DatatypeConverter.printBase64Binary(usernameValue.getBytes());
		
		return encoded;
	}
	
	//Twitter stuff
	public static String timelineTweets() {
		String res = "/home_timeline.json";
		return res;
	}
	
	public static String newTweet() {
		String res = "/update.json";
		return res;
	}
	
	public static String deleteTweet(String tweetId) {
		String res = "/destroy/" + tweetId + ".json";
		return res;
	}

}
