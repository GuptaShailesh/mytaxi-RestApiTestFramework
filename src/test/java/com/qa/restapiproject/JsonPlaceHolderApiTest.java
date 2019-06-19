package com.qa.restapiproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import com.qa.restapiproject.utility.EmailAddressValidator;

import io.restassured.RestAssured;


public class JsonPlaceHolderApiTest {

	JsonPlaceHolderApi jsonPlaceHolderApi = new JsonPlaceHolderApi() ; 
	EmailAddressValidator emailValidator= new EmailAddressValidator();
	private static final Logger logger = LogManager.getLogger(JsonPlaceHolderApiTest.class);

	/**
	 * @param userName
	 * @param baseUrl
	 * This method will fetch the comments for each post written by given user 
	 * and will validate the email address for each comment 
	 */
	@Test
	@Parameters({"userName","baseUrl"})
	public void getCommentsAndValidateEmailAddressFormat(String userName, String baseUrl, ITestContext context)
	{

		logger.info("-----------The "+context.getName()+ " Test: Started------ \n");

		RestAssured.baseURI=baseUrl;
		ArrayList <Map<String,?>>allCommentsforpostId ;
		try {


			//This method call will return the Id of the  given user
			logger.info("--------Getting the UserID of user *"+userName+"*---------------------- \n");
			String  userId= jsonPlaceHolderApi.getUserid(userName);
			
			
			//This method call will return the list of Ids of all the posts posted by given user
			logger.info("--------Getting all the postsIDs for the posts written by user *"+userName+"* having UserId *"+userId+"*-------------- \n");
			List<Integer> postIds= jsonPlaceHolderApi.getPostIds(userId);
			
			
		/*	This foreach loop will iterate over each post Ids and will fetch comments Id and EmailId
		Related to each post and will validate the each email ID*/
			logger.info("---------Fetching the comments for each PostIds and Validating the emailId Format------------");
			for(int postId: postIds)
			{
				allCommentsforpostId = jsonPlaceHolderApi.getCommentsForPostId(postId); 

				for(Map<String,?>commentBlock :allCommentsforpostId)
				{
					String commentBody = (String) commentBlock.get("body");
					String emailId= (String) commentBlock.get("email");
					int commentid = (Integer) commentBlock.get("id");
					
					logger.info("_________________________________________________________________________________________________________________");
					logger.info("--------------The Email ID and Comments for CommentID: *" + commentid+ "* is: \n"+ "Emaild :"+emailId +"\n"+ "Comment :" +commentBody);
					logger.info("-----------check for emailAdress Validity started--------------------------------------");
					Boolean isEmailAddressFormatVaild= emailValidator.isValidEmaiIdFormat(emailId);
					if(isEmailAddressFormatVaild)
					{
						logger.info("--------------The ******"+emailId+" :********-----Format is correct-----------\n");
						logger.info("________________________________________________________________________________________________________________\n");
					}
					else {
						logger.info("--------------The ******"+emailId+" :********-----Format is incorrect-----------");
						logger.info("_________________________________________________________________________________________________________________\n");
					}


				}

			}
			logger.info("-----------The "+context.getName()+ " Test: Completed-------------------------------");
		}
		catch (Exception e)
		{
			logger.error("-----------The "+context.getName()+" Test: Failed with Exception : -------" +e.getMessage());
		}
	}




}
