package in.reqres.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

import in.reqres.core.BaseTest;
import in.reqres.model.UserModel;
import in.reqres.support.Util;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.json.JSONObject;

import io.restassured.http.ContentType;

/**
 * @author jussaragranja
 * Test Class
 */

public class ReqresTest extends BaseTest {

	JSONObject userJsonObject = new JSONObject();
	UserModel userModel = new UserModel();


	public JSONObject userJson(){

		userJsonObject.append("name", "TEST API "+ Util.valorRandomico(1000));
		userJsonObject.append("job", "TEST "+ Util.valorRandomico(1000));

		return userJsonObject;
	}

	@Test
	public void testGetSingleUser() {

		userModel.setId(2);
		userModel.setEmail("janet.weaver@reqres.in");
		userModel.setFirst_name("Janet");
		userModel.setLast_name("Weaver");
		userModel.setCompany("StatusCode Weekly");
		userModel.setCompany_url("http://statuscode.org/");


		given()
			.when()
				.get("users/"+userModel.getId())
			.then()
				.assertThat()
				.body("data.id", equalTo(userModel.getId()),
						"data.email", equalTo(userModel.getEmail()),
						"data.first_name", equalTo(userModel.getFirst_name()),
						"data.last_name", equalTo(userModel.getLast_name()),
						"ad.company", equalTo(userModel.getCompany()),
						"ad.url", equalTo(userModel.getCompany_url()))
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_OK)
				.log().all();
	}

	@Test
	public void testGetListUsers() {

		given()
			.when()
				.get	("users?page=2")
			.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_OK)
				.log()
				.all();
	}

	@Test
	public void testGetSingleResource() {

		given()
			.when()
				.get("unknown/2")
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_OK)
				.body("data.id", equalTo(2),
						"data.name", equalTo("fuchsia rose"),
						"data.year", equalTo(2001),
						"data.pantone_value", equalTo("17-2031"),
						"data.name", equalTo("fuchsia rose"),
						"data.name", equalTo("fuchsia rose"))
				.log().all();
	}
	
	@Test
	public void testGetDelayedResponse() {
		given()
			.when()
				.get("users?delay=3")
			.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_OK)
				.log().all();
	}
	
	@Test
	public void testPostCreateUser() {

		given()
			.when()
				.contentType(ContentType.JSON)
				.body(userJson().toString())
				.post("users")
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_CREATED)
				.log()
				.all();
	}
	
	@Test
	public void testPostRegisterSuccessful() {

		JSONObject registerJson = new JSONObject();
		registerJson.accumulate("email", "eve.holt@reqres.in");
		registerJson.accumulate("password", "pistol");

		given()
			.when()
				.contentType(ContentType.JSON)
				.body(registerJson.toString())
				.post("register")
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_OK)
				.log().all();
	}
	@Test
	public void testUpdateRegisterSucessful() {

		userModel.setId(2);
		userJson().accumulate("name", "apitest");

		given()
			.when()
				.contentType(ContentType.JSON)
				.body(userJson().toString())
				.put("users/"+userModel.getId())
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_OK)
				.log()
				.all();

	}

	@Test
	public void testPathUpdate() {

		userModel.setId(2);

		userJson().accumulate("name", "apitest2");
		userJson().accumulate("job", "testjob2");

		given()
			.when()
				.contentType(ContentType.JSON)
				.body(userJson().toString())
				.patch("users/"+userModel.getId())
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_OK)
				.log().all();
	}


	@Test
	public void TestDelete() {

		userModel.setId(11);

		given()
			.when()
				.delete("users/"+userModel.getId())
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_NO_CONTENT)
				.log().all();
	}


	@Test
	public void testPostLoginSuccessful() {

		JSONObject registerJson = new JSONObject();
		registerJson.accumulate("email", "eve.holt@reqres.in");
		registerJson.accumulate("password", "cityslicka");

		given()
				.when()
				.contentType(ContentType.JSON)
				.body(registerJson.toString())
				.post("login")
				.then()
				.assertThat()
				.statusCode(HttpStatus.SC_OK)
				.log()
				.all();
	}

	@Test
	public void testPostLoginUnsuccessful() {

		JSONObject registerJson = new JSONObject();
		registerJson.accumulate("email", "peter@klaven");

		JSONObject msg = new JSONObject();
		msg.accumulate("error", "Missing password");

		given()
			.when()
				.contentType(ContentType.JSON)
				.body(registerJson.toString())
				.post("login")
			.then()
				.assertThat()
				.statusCode(HttpStatus.SC_BAD_REQUEST)
				.body(containsString(msg.toString()))
				.log()
				.all();
	}

	@Test
	public void testGetSingleUserNotFound() {

		userModel.setId(80);

		given()
			.when()
				.get("users/"+userModel.getId())
			.then()
				.assertThat()
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_NOT_FOUND)
				.log().all();
	}
}
