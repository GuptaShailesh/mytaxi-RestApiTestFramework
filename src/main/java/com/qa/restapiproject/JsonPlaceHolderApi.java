package com.qa.restapiproject;

import static io.restassured.RestAssured.given;
import com.qa.restapiproject.utility.RawToJsonXmlConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonPlaceHolderApi {
	Response response;
	JsonPath jsonPath;

	public String getUserid(String userName) {

		response = given().queryParam("username", userName).when().get("/users").then().statusCode(200).extract()
				.response();
		jsonPath = RawToJsonXmlConverter.rawToJson(response);
		String userid = jsonPath.getString("id").replaceAll("\\[", "").replaceAll("\\]", "");

		return userid;
	}

	public List<Integer> getPostIds(String userId) {
		response = given().queryParam("userid", userId).when().get("/posts").then().statusCode(200).extract()
				.response();
		jsonPath = RawToJsonXmlConverter.rawToJson(response);
		List<Integer> postIds = jsonPath.getList("id");

		return postIds;
	}

	public ArrayList getCommentsForPostId(int postid) {
		response = given().queryParam("postId", postid).when().get("/comments").then().statusCode(200).extract()
				.response();
		jsonPath = RawToJsonXmlConverter.rawToJson(response);
		ArrayList<Map<String, ?>> allcommentsforPostId = jsonPath.get("");

		return allcommentsforPostId;
	}

	

}
