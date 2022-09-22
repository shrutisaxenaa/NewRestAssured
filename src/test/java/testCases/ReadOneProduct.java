package testCases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ReadOneProduct {
	
	SoftAssert softAssert= new SoftAssert();
	
	String baseUri="https://techfios.com/api-prod/api/product";
	
	@Test
	public void readOneProduct() {
		
		
		/*
			given: all input details(baseURI,Headers,PayLoad/Body,QueryParamaeters,Authorization)
		    when: submit API requests(HTTP method, Endpoint/resource)
		     then: validate response(status code,Headers, responseTime, payLoad/Body)
			 
		 */
		
		Response response =
		
	given()
		.baseUri(baseUri)
		.header("Content-Type","application/json")
		.header("Authorization","Bearer gbfbvvxcxcxzcxzcxz")
		.queryParam("id", "5267").
					
    when()
         
         .get("/read_one.php?id=5108").
         
    then()
    
          .extract().response();
	
	int statusCode=	response.getStatusCode();
	System.out.println("Status Code :"+statusCode);
//	Assert.assertEquals(statusCode, 200);
	softAssert.assertEquals(statusCode, 200,"Status codes are not matching");
	
	

	long responseTime=response.getTimeIn(TimeUnit.MILLISECONDS);
	System.out.println("Response Time is :"+responseTime);
	
	if(responseTime<=2000) {
		System.out.println("Response time is within the Range");
	}
	       
	else {
		System.out.println("Response Time is not in the Range");
	}
		
	
	String responseHeader=response.getHeader("Content-Type");
	
	System.out.println("Response Header is:"+responseHeader);
	Assert.assertEquals(responseHeader, "application/json");
	
	String responseBody=response.getBody().asString();
	
	
		
	JsonPath jsonPath= new JsonPath(responseBody);
	
	String ProductID=jsonPath.get("id");
	System.out.println("Product ID :"+ProductID);
	
	String productName=jsonPath.getString("name");
	System.out.println("Product name: "+productName);
	Assert.assertEquals(productName, "Nice Pillow UPDATED BY Shrutika");
	
	String productDescription= jsonPath.getString("description"); 
	System.out.println("Product Descrition: "+productDescription);
	Assert.assertEquals(productDescription, "The best pillow for amazing programmers.");
	
	String productPrice=jsonPath.getString("price");
	System.out.println("product price :"+productPrice);
	Assert.assertEquals(productPrice, "955");
	
	softAssert.assertAll();
	}

}
