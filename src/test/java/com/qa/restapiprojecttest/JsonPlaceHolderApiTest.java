package com.qa.restapiprojecttest;
import static org.testng.Assert.fail;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.restapiproject.JsonPlaceHolderApi;
import com.qa.restapiproject.utility.EmailAddressValidator;

import io.restassured.RestAssured;


public class JsonPlaceHolderApiTest {

	JsonPlaceHolderApi jsonPlaceHolderApi = new JsonPlaceHolderApi() ; 
	EmailAddressValidator emailValidator= new EmailAddressValidator();
	private static final Logger logger = LogManager.getLogger(JsonPlaceHolderApiTest.class);
	String  userId;
	List<Integer> postIds;
	List <Map<String,?>>allCommentsforpostId ;
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

		try {


			//This method call will return the Id of the  given user
			logger.info("\n--------Getting the UserID of user *"+userName+"*---------------------- \n");


			userId= jsonPlaceHolderApi.getUserid(userName);
			Assert.assertTrue(!(userId.isEmpty()),"The userId not found for user: "+userName);

			//This method call will return the list of Ids of all the posts posted by given user
			logger.info("--------Getting all the postsIDs for the posts written by user *"+userName+"* having UserId *"+userId+"*-------------- \n");
			postIds= jsonPlaceHolderApi.getPostIds(userId);
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
			logger.error(e.getStackTrace());
			fail("\n ####### The "+context.getName()+" Test: Failed with Exception : ############" +e.getMessage());

		}



	}


	/**
	 * This test method is written to validate the response from Users Api. 
	 * We can add more validation in this method by varying the parameters and validating on other fields
	 * We can also use data provider or some file to get the data
	 * @param userName
	 * @param context
	 */
	@Test 
	@Parameters ({"userName", "expectedUserId","baseUrl"})
	public void validateUsersApiResponse(String userName,String expectedUserId,String baseUrl,ITestContext context)
	{
		logger.info("\n######### " + context.getName()+ " Started: ##########\n");
		RestAssured.baseURI=baseUrl;

		try {
			userId= jsonPlaceHolderApi.getUserid(userName);
			Assert.assertEquals(userId,expectedUserId, "Invalid response from users Api ");
		}

		catch (Exception e)
		{
			logger.error(e.getStackTrace());
			fail("\n ####### The "+context.getName()+" Test: Failed with Exception : ############" +e.getMessage());
		}


	}

	/**
	 * This test method is written to validate the response from posts Api. 
	 * We can add more validation in this method by varying the parameters and validating on other fields
	 * We can also use data provider or some file to get the data
	 * @param userId
	 * @param expectedPostIds
	 * @param context
	 */
	@Test 
	@Parameters ({"userId", "expectedPostIdCount","baseUrl"})
	public void validatePostsApiResponse(String userId, int expectedPostIdCount,String baseUrl, ITestContext context )
	{
		logger.info("\n######### " + context.getName()+ " Started: ##########\n");
		RestAssured.baseURI=baseUrl;

		try {
			postIds=  jsonPlaceHolderApi.getPostIds(userId);
			Assert.assertEquals(postIds.size(),expectedPostIdCount, "Invalid response from posts Api ");
		}

		catch (Exception e)
		{
			logger.error(e.getStackTrace());
			fail("\n ####### The "+context.getName()+" Test: Failed with Exception : ############" +e.getMessage());
		}

	}

	/**
	 * This test method is written to validate the response from Comments Api. 
	 * We can add more validation in this method by varying the parameters and validating on other fields
	 * We can also use data provider or some file to get the data
	 * @param postId
	 * @param expectedTotalComments
	 * @param context
	 */
	@Test 
	@Parameters ({"postId", "expectedTotalComments","baseUrl"})
	public void validateCommentsApiResponse(int postId, int expectedTotalComments,String baseUrl, ITestContext context )
	{
		logger.info("\n######### " + context.getName()+ " Started: ##########\n");
		RestAssured.baseURI=baseUrl;

		try {
			allCommentsforpostId=  jsonPlaceHolderApi.getCommentsForPostId(postId);
			Assert.assertEquals(allCommentsforpostId.size(),expectedTotalComments, "Invalid response from comments  Api ");
		}

		catch (Exception e)
		{
			logger.error(e.getStackTrace());
			fail("\n ####### The "+context.getName()+" Test: Failed with Exception : ############" +e.getMessage());
		}

	}




}
