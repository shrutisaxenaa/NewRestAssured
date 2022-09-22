package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAllProducts {
	
	@Test
	public void readAllProducts() {
		
	/*
		given: all input details(baseURI,Headers,PayLoad/Body,QueryParamaeters,Authorization)
	    when: submit API requests(HTTP method, Endpoint/resource)
	     then: validate response(status code,Headers, responseTime, payLoad/Body)
		 
	 */
		Response response =
		
		given()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type","application/json; charset=UTF-8")
				.auth().preemptive().basic("demo@techfios.com", "abc123").
		when()
		        .get("/read.php").
		        
		 
    //for validating we use "response" reference object from Response Interface
					        
	    
		then()
		       .extract().response();
		
		
		
		//validating Response StatusCode
		
		int statusCode=	response.getStatusCode();
		System.out.println("Status Code :"+statusCode);
		Assert.assertEquals(statusCode, 200);
		
		//Validating Response time
		
		long responseTime=response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time is :"+responseTime);
		
		if(responseTime<=2000) {
			System.out.println("Response time is within the Range");
		}
		       
		else {
			System.out.println("Response Time is not in the Range");
		}
		
		//validating Response Header
		
		String responseHeader=response.getHeader("Content-Type");
		
		System.out.println("Response Header is:"+responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
		
		//validating Response Body
		//we are using asString() method to change Json body to String
		
		String responseBody=response.getBody().asString();
		
		
		//for printing same way as printed in response window//
		response.getBody().prettyPrint(); 
		
		
		//this way it will print in single line//
		//System.out.println("Response Body :"+responseBody);
		
		//Validating if Response Body is not NULL//
		
		//BY calling this JsonPath class we converting responseBody from String to Jason.
		//so that we can ID
		
		JsonPath jsonPath= new JsonPath(responseBody);
		
		String firstProductID=jsonPath.get("records[0].id");
		System.out.println("First Product ID :"+firstProductID);
		
		if(firstProductID!=null) {
			
			System.out.println("records are not null");
		}
		else {
			System.out.println("records are null");
		}
		
		
				
				
				
	
		
						
						
	}

}
