package files;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import io.restassured.RestAssured;

public class jiraCleanUps {

	public static void deleteIssue(String issueId) throws IOException {
		RestAssured.baseURI = resources.loadBaseURI("Jira");
		
		given().
			header("Content-Type", "application/json").
			header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
		when().
			delete(resources.jiraIssueEndPoint() + "/" + issueId).
		then().
			statusCode(204);
		System.out.println("From clean up: auto deleted issue");
	}
	
	public static void deleteComment(String commentId, String issueId) throws IOException {
		RestAssured.baseURI = resources.loadBaseURI("Jira");
		
		given().
			header("Content-Type", "application/json").
			header("Authorization", "Basic " + resources.encodedAuth("justinasz", "qwerty")).
		when().
			delete(resources.jiraUpdateCommentEndPoint(issueId, commentId));
		
		System.out.println("From clean up: auto deleted comment");
	}	
}
