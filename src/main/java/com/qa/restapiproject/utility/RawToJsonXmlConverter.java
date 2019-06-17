package com.qa.restapiproject.utility;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class RawToJsonXmlConverter {

	public static JsonPath rawToJson(Response res) {
		String response = res.asString();
		return new JsonPath(response);

	}

	public static XmlPath rawToXml(Response res) {
		String response = res.asString();
		return new XmlPath(response);

	}
}
