package com.survey.restsurvey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RestSurveyApplicationTests {
	
	String str = """
			
			{
	    "id": "Question1",
	    "description": "Most popular cloud platform",
	    "correctAnswer": "AWS",
	    "option": [
	        "AWS",
	        "Azure",
	        "Google Cloud",
	        "Oracle Cloud"
	    ]
	}
	""";
	//http://localhost:8080/surveys/Survey1/questions/Question1
	@Autowired
	private TestRestTemplate template;
	
	private static String SPECIFIC_QUESTION = "/surveys/Survey1/questions/Question1";
	private static String GENERIC_QUESTION_URL = "/surveys/Survey1/questions";

	@Test
	void contextLoads() {
	ResponseEntity<String> responseEntity=	template.getForEntity(SPECIFIC_QUESTION, String.class);
	String expectedBody =  			
			"""
		{"id":"Question1","description":"Most popular cloud platform","correctAnswer":"AWS","option":["AWS","Azure","Google Cloud","Oracle Cloud"]}
	""";
	assertEquals(expectedBody.trim(), responseEntity.getBody());
	}
	
	@Test
	void testJSONAssert() throws JSONException {
	//	Body: {"id":"Question1","description":"Most popular cloud platform","correctAnswer":"AWS","option":["AWS","Azure","Google Cloud","Oracle Cloud"]}

		//Header: 
	//	[Content-Type:"application/json", Transfer-Encoding:"chunked", Date:"Wed, 20 Mar 2024 16:36:20 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
		ResponseEntity<String> responseEntity=	template.getForEntity(SPECIFIC_QUESTION, String.class);
		String expectedBody =  			
				"""
			{"id":"Question1","description":"Most popular cloud platform","correctAnswer":"AWS","option":["AWS","Azure","Google Cloud","Oracle Cloud"]}
		""";
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
		//last boolean value, when false means => not strictly check and ignore space related issue, and all parameter
		JSONAssert.assertEquals(expectedBody, responseEntity.getBody(), false);
		
	}
	
	//testing POST Request
	@Test
	void testPostRequest() {
		String postBody= """
			{
				    "description": "Most popular Language for Enterprise Application1",
				    "correctAnswer": "Java",
				    "option": [
				        "Java",
				        "python",
				        "typescript",
				        "dot net"
				    ]
				}
				
				""";
		//for sending post request
		//set headers
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<String> httpEntity = new HttpEntity<String>(postBody,headers);
		ResponseEntity<String> responseEntity = template.exchange(GENERIC_QUESTION_URL, HttpMethod.POST, httpEntity, String.class);
		System.out.println("responseEntity ===> "+ responseEntity);
		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		String locationHeader= responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/question"));
		
		template.delete(locationHeader);
		
		
	}
	
				

}
