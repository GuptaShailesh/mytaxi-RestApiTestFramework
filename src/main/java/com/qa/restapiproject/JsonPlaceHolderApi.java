package com.qa.restapiproject;

import static io.restassured.RestAssured.given;
import com.qa.restapiproject.utility.RawToJsonXmlConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class JsonPlaceHolderApi {
	Response response;
	JsonPath jsonPath;
	private static final Logger logger = LogManager.getLogger(JsonPlaceHolderApi.class);
	/**
	 * @param userName
	 * @return userId of the given user 
	 */
	public String getUserid(String userName) {
		
		response = given().queryParam("username", userName).when().get("/users").then().statusCode(200).extract()
				.response();
		jsonPath = RawToJsonXmlConverter.rawToJson(response);
		String userid = jsonPath.getString("id").replaceAll("\\[", "").replaceAll("\\]", "");
		logger.info("-------Returning the userid of user *"+userName+"* : ---- ");
		return userid;

	}

	/**
	 * @param userId
	 * @return list of posts id for given userId
	 */
	public List<Integer> getPostIds(String userId) {
		response = given().queryParam("userId", userId).when().get("/posts").then().statusCode(200).extract()
				.response();

		jsonPath = RawToJsonXmlConverter.rawToJson(response);
		List<Integer> postIds = jsonPath.getList("id");
		logger.info("-------Returning the post IDs written by user with Id *"+userId+"* ------- ");
		return postIds;
	}

	/**
	 * @param postid
	 * @return list of all the comments for given postId
	 * 
	 */
	public List<Map<String,?>> getCommentsForPostId(int postid) 
	{
		ArrayList<Map<String, ?>> allcommentsforPostId = null;
		response = given().queryParam("postId", postid).when().get("/comments").then().statusCode(200).extract()
				.response();
		jsonPath = RawToJsonXmlConverter.rawToJson(response);
		allcommentsforPostId = jsonPath.get("");

		logger.info("\n-------Returning the comments written for the post having PostId : **"+postid+"** ---------\n");

		return allcommentsforPostId;

	}
}
