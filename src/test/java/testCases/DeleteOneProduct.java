package testCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteOneProduct {
	
	SoftAssert softAssert= new SoftAssert();
	
	String baseUri="https://techfios.com/api-prod/api/product";
	String deleteProductResponseHeader;
	String deleteProductRequestHeader="application/json; charset=UTF-8";
	String bearerToken="Bearer gbfbvvxcxcxzcxzcxz";
	String deleteProductExpectedMessage="Product was deleted.";
	HashMap<String,String> deletePayloadMap;
	
	
	
	public Map<String,String> getDeletePayloadMap(){
	
		deletePayloadMap= new HashMap<String,String>();
		
		deletePayloadMap.put("id", "5387");
		
		return deletePayloadMap;
	}
	

	
	@Test(priority=1)
public void deleteOneProduct() {
				
		Response response =
				
				given()
					.baseUri(baseUri)
					.header("Content-Type",deleteProductRequestHeader)
					.header("Authorization",bearerToken)
//					.body(new File("Data\\createPayload.json")).
					.body(getDeletePayloadMap()).
		
    when()
         
         .delete("/delete.php").
         
    then()
    
          .extract().response();
		
		int statusCode=	response.getStatusCode();
		System.out.println("Status Code :"+statusCode);
		softAssert.assertEquals(statusCode, 200);
		
		
			
		deleteProductResponseHeader=response.getHeader("Content-Type");
		
		System.out.println("Response Header is:"+deleteProductResponseHeader);
		softAssert.assertEquals(deleteProductResponseHeader, deleteProductRequestHeader);
		
		String responseBody=response.getBody().asString();
		
		JsonPath jsonPath= new JsonPath(responseBody);
		
		String deleteProductActualMessage=jsonPath.getString("message");
		System.out.println("Product Message: "+deleteProductActualMessage);
		softAssert.assertEquals(deleteProductActualMessage, deleteProductExpectedMessage);
	
		
		softAssert.assertAll();
}
	
	

		@Test(priority=2)
		public void readOneProduct() {
			
			
			Response response =
			
		given()
			.baseUri(baseUri)
			.header("Content-Type","application/json")
			.header("Authorization","Bearer gbfbvvxcxcxzcxzcxz")
			.queryParam("id", getDeletePayloadMap().get("id")).
						
	    when()
	         
	         .get("/read_one.php").
	         
	    then()
	    
	          .extract().response();
			
			int statusCode=	response.getStatusCode();
			System.out.println("Status Code :"+statusCode);
//			Assert.assertEquals(statusCode, 200);
			softAssert.assertEquals(statusCode, 404,"Status codes are not matching");
			
			
		String responseBody=response.getBody().asString();
						
		JsonPath jsonPath= new JsonPath(responseBody);
		
		String actualProductMessage=jsonPath.getString("message");
		softAssert.assertNotEquals(actualProductMessage, deleteProductExpectedMessage);
		
		softAssert.assertAll();
		}
		
		
		
	}
	
