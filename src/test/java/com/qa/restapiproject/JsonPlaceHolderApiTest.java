package com.qa.restapiproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class JsonPlaceHolderApiTest {

	JsonPlaceHolderApi jsonPlaceHolderApi = new JsonPlaceHolderApi() ; 
	@Test
	@Parameters({"userName","baseUrl"})
	public void getCommentsAndValidateEmailAddress(String userName, String baseUrl)
	{
		RestAssured.baseURI=baseUrl;
		ArrayList <Map<String,?>>allCommentsforpostId ;

		String  userId= jsonPlaceHolderApi.getUserid(userName);
		List<Integer> postIds= jsonPlaceHolderApi.getPostIds(userId);


		for(int postId: postIds)
		{
			allCommentsforpostId = jsonPlaceHolderApi.getCommentsForPostId(postId); 

			for(Map<String,?>commentBlock :allCommentsforpostId)
			{
				String commentBody = (String) commentBlock.get("body");
				String emailId= (String) commentBlock.get("email");
				int commentid = (Integer) commentBlock.get("id");
				System.out.println("*****The Email ID and Comment for CommentID: " + commentid+ "****** is: \n"+ "Emaild :"+emailId +"\n"+ "Comment :" +commentBody);
				System.out.println("________________________________________________________________________");
				System.out.println("******check for emailAdress Validity started*****");
				Boolean isEmailAddressVaild= jsonPlaceHolderApi.isEmailIdValid(emailId);
				if(isEmailAddressVaild)
				{
					System.out.println(" ***** The "+emailId+" :********** is a valid email ID**************");
					System.out.println("---------------------------------------------");
				}
				else {
					System.out.println("The "+emailId+" is an Invalid email ID");
					System.out.println("---------------------------------------------");
				}


			}

		}

	}




}
