package com.stylight.prettyurl;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrettyUrlApplicationTests {

	private final String PRETTY_URL = "/pretty";
	private final String CANONICAL_URL = "/canonical";
	@LocalServerPort
	public int port;
	TestRestTemplate testRestTemplate = new TestRestTemplate();
	@Value("${app.base.domain}")
	private String basePath;

	@Test
	public void testValidGetByKey() throws JSONException {
		//arrange
		List<String> canonicalUrls = Arrays.asList("/products", "/products?gender=female");
		String expected = "{" +
				"\"" + basePath + "/products\":\"" + basePath + "/Fashion/\"," +
				"\"" + basePath + "/products?gender=female\":\"" + basePath + "/Women/\"" +
				"}";
		//act
		ResponseEntity<String> response = testRestTemplate.exchange(
				createURLWithPort(PRETTY_URL), HttpMethod.POST, getRequestEntity(canonicalUrls), String.class
		);


		//assert
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}

	@Test
	public void testValidGetByKeyBestMatch() throws JSONException {
		//arrange
		List<String> canonicalUrls = Arrays.asList("/products?gender=female&tag=123&tag=1234&tag=5678", "/products?tag=5678&tag=1234");
		String expected = "{" +
				"\"" + basePath + "/products?gender=female&tag=123&tag=1234&tag=5678\":\"" + basePath + "/Women/Shoes/\"," +
				"\"" + basePath + "/products?tag=5678&tag=1234\":\"" + basePath + "/Boat--Shoes/\"" +
				"}";
		//act
		ResponseEntity<String> response = testRestTemplate.exchange(
				createURLWithPort(PRETTY_URL), HttpMethod.POST, getRequestEntity(canonicalUrls), String.class
		);


		//assert
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}

	@Test
	public void testValidGetByKeyNoMatch() throws JSONException {
		//arrange
		List<String> canonicalUrls = Arrays.asList("/product?gender=male&tag=12345&tag=12345&tag=5678", "/product?boats=true&tag=56788&tag=12345");
		String expected = "{" +
				"\"" + basePath + "/product?gender=male&tag=12345&tag=12345&tag=5678\":\"" + basePath + "/product?gender=male&tag=12345&tag=12345&tag=5678\"," +
				"\"" + basePath + "/product?boats=true&tag=56788&tag=12345\":\"" + basePath + "/product?boats=true&tag=56788&tag=12345\"" +
				"}";
		//act
		ResponseEntity<String> response = testRestTemplate.exchange(
				createURLWithPort(PRETTY_URL), HttpMethod.POST, getRequestEntity(canonicalUrls), String.class
		);


		//assert
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}

	@Test
	public void testValidGetByValue() throws JSONException {
		//arrange
		List<String> canonicalUrls = Arrays.asList("/Fashion/", "/Women/");
		String expected = "{" +
				"\"" + basePath + "/Fashion/\":\"" + basePath + "/products\"," +
				"\"" + basePath + "/Women/\":\"" + basePath + "/products?gender=female\"" +
				"}";
		//act
		ResponseEntity<String> response = testRestTemplate.exchange(
				createURLWithPort(CANONICAL_URL), HttpMethod.POST, getRequestEntity(canonicalUrls), String.class
		);


		//assert
		JSONAssert.assertEquals(expected, response.getBody(), true);
	}

	/*private String createURLWithPort(String uri) {
		return "http://localhost:" + port + "/pretty-url/" + uri;
	}*/
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + "/" + uri;
	}

	private HttpEntity<List<String>> getRequestEntity(List<String> urls) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(urls, headers);
	}

}

