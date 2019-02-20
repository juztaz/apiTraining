import java.io.IOException;
import java.util.ArrayList;
import static io.restassured.RestAssured.*;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.jiraCleanUps;
import files.methods;
import files.payLoad;
import files.resources;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basics6_jira {
	
	ArrayList<String> createdIssues = new ArrayList<String>();
	ArrayList<String> createdComments = new ArrayList<String>();
	String testingIssueId = "10007";

	@BeforeTest
	public void getSession() throws IOException {		
	//Enable logging if failed:
	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
				
	//Set URI
	RestAssured.baseURI = resources.loadBaseURI("Jira");
	}
	
	@AfterTest
	public void cleanUp() {
		
		//Delete created issues if present
		if(!createdIssues.isEmpty()) {
			createdIssues.forEach((n) -> {
				try {
					jiraCleanUps.deleteIssue(n);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		
		//Delete created comments if present
		if(!createdComments.isEmpty()) {
			createdComments.forEach((n) -> {
				try {
					jiraCleanUps.deleteComment(n, testingIssueId);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	@Test
	public void jiraAPI_createBug() {
		Response res = 
		given().
			header("Content-Type", "application/json").
			header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
		body(payLoad.newBug("RES", "RestAssured", "Created from framework")).
		when().
			post(resources.jiraIssueEndPoint()).
		then().
			statusCode(201).
		extract().response();
		
		//Take the ID
		JsonPath js = methods.rawToJson(res);
		String issueId = js.get("id");
		
		//Debug
		createdIssues.add(issueId);
		System.out.println("New issue ID: " + issueId);
	}
	
	@Test
	public void jiraAPI_createComment() {
		//Create comment:
		Response res_create = 
				given().
					header("Content-Type", "application/json").
					header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
				body(payLoad.newComment("Added from test")).
				when().
					post(resources.jiraAddCommentEndPoint(testingIssueId)).
				then().
					statusCode(201).
				extract().response();
		
		//Take the ID
		JsonPath js = methods.rawToJson(res_create);
		String commentId = js.get("id");
		
		//Debug
		createdComments.add(commentId);
		System.out.println("New comment ID: " + commentId);
	}

	@Test
	public void jiraAPI_updateComment() {
		//Create testingComment
		String commentId = methods.createDummyComment(testingIssueId);
		
		//Update comment:
		given().
			header("Content-Type", "application/json").
			header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
			body(payLoad.newComment("Updated from test")).
		when().
			put(resources.jiraUpdateCommentEndPoint(testingIssueId, commentId)).
		then().
			statusCode(200);
				
		//Debug
		createdComments.add(commentId);
		System.out.println("Comment was updated");
	}
	
	@Test
	public void jiraAPI_deleteComment() {
		//Create testingComment
		String commentId = methods.createDummyComment(testingIssueId);
		
		//Delete comment:
		given().
			header("Content-Type", "application/json").
			header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
		when().
			delete(resources.jiraUpdateCommentEndPoint(testingIssueId, commentId)).
		then().
			statusCode(204);
				
		//Debug
		System.out.println("Comment was deleted");
	}
}
