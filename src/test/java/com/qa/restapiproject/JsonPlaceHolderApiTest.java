package com.qa.restapiproject;

import static org.testng.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
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

		logger.info("\n################### The "+context.getName()+ " Test: Started ###############\n");

		RestAssured.baseURI=baseUrl;
		List <Map<String,?>>allCommentsforpostId ;
		try {


			//This method call will return the Id of the  given user
			logger.info("\n--------Getting the UserID of user *"+userName+"*---------------------- \n");

			
			String  userId= jsonPlaceHolderApi.getUserid(userName);
			Assert.assertTrue(!(userId.isEmpty()),"The userId not found for user: "+userName);

			//This method call will return the list of Ids of all the posts posted by given user
			logger.info("--------Getting all the postsIDs for the posts written by user *"+userName+"* having UserId *"+userId+"*-------------- \n");
			List<Integer> postIds= jsonPlaceHolderApi.getPostIds(userId);
			Assert.assertTrue(!(postIds.isEmpty()), "No posts found for user with userID: "+ userId);

			/*	This for each loop will iterate over each post Ids and will fetch comments Id and EmailId
				Related to each post and will validate the each email ID*/
			
			logger.info("---------Fetching the comments for each PostIds and Validating the emailId Format------------");
			for(int postId: postIds)
			{
				allCommentsforpostId = jsonPlaceHolderApi.getCommentsForPostId(postId); 

				if(allCommentsforpostId.isEmpty())
				{
					logger.warn("No comments found for Post ID: " +postId);
				}

				else {
					for(Map<String,?>commentBlock :allCommentsforpostId)
					{
						String commentBody = (String) commentBlock.get("body");
						String emailId= (String) commentBlock.get("email");
						int commentid = (Integer) commentBlock.get("id");

						logger.info("##############################_________________________________________________________________________________________________________________\n");
						logger.info("\n--------------Printing the Email ID and Comments, for CommentID: *" + commentid+ "*------------------\n"+ "EMAILID :**"+emailId +"**\n"+ "COMMENTS :\\**" +commentBody +"**/\n");
						logger.info("-----------check for emailAdress Validity started--------------------------------------");
						Boolean isEmailAddressFormatVaild= emailValidator.isValidEmaiIdFormat(emailId);
						if(isEmailAddressFormatVaild)
						{
							logger.info("--------------The Format of EmailID : ******"+emailId+" ********----- is correct-----------\n");
							logger.info("\n________________________________________________________________________________________________________________##################\n");
						}
						else {
							logger.info("--------------The Format of EmailID:  ******"+emailId+" :********-----is incorrect-----------\n");
							logger.info("\n_________________________________________________________________________________________________________________####################");
						}


					}
				}
			}

			logger.info("\n ############ The "+context.getName()+ " Test: Completed #########################\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("\n ####### The "+context.getName()+" Test: Failed with Exception : ############" +e.getMessage());
			
		}
		


	}
}
