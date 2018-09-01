package in.reqres;

import static io.restassured.RestAssured.given;
import org.junit.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ServiceGetTest extends BaseTest {

	@Test
	public void testGetSingleUser() {
		given()
		.when()
		.get("/api/users/2")
		.then()
		.contentType(ContentType.JSON)
		.statusCode(200);
	}

	@Test
	public void testGetSingleUser2() {
		Response response = given()
				.when()
				.get("/api/users/2");
		given()
		.when()
		.get("/api/users/2")
		.then()
		.statusCode(200);
		
		//exibindo um log no console
		System.out.println(response.body().asString());
	}
	
	@Test
	public void testGetListUsers() {
		given()
		.when()
		.get("/api/users?page=2")
		.then()
		.contentType(ContentType.JSON)
		.statusCode(200)
		.log()
		.all();
	}
	
	@Test
	public void testGetSingleResource() {
		given()
		.when()
		.get("/api/unknown/2")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void testGetDelayedResponse() {
		given()
		.when()
		.get("/api/users?delay=3")
		.then()
		.statusCode(200);
	}
	
	@Test
	public void testPostCreate() {
		String body = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
	given()
	.contentType(ContentType.JSON)
	.body(body)
	.when()
	.post("/api/users")
	.then()
	.statusCode(201)
	.log()
	.all();
	}
	
	@Test
	public void testPostRegisterSuccessful() {
		String b = "{\"email\": \"sydney@fife\",\"password\": \"pistol\"}";
		given()
		.contentType(ContentType.JSON)
		.body(b)
		.when()
		.post("/api/register")
		.then()
		.statusCode(201);
	}
	@Test
	public void testUpdateRegisterSucessful() {
		String p = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
		given()
		.contentType(ContentType.JSON)
		.body(p)
		.when()
		.put("/api/users/2")
		.then()
		.statusCode(200)
		.log()
		.all();

	}
	
	@Test
	public void TestDelete() {
		given()
		.when()
		.delete("/api/users/2")
		.then()
		.statusCode(204);
	}
	
	@Test
	public void testPathUpdate() {
		String pa = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
		given()
		.contentType(ContentType.JSON)
		.body(pa)
		.when()
		.patch("/api/users/2")
		.then()
		.statusCode(200)
		.log()
		.all();
	}
	
	@Test
	public void testPostLoginUnsuccessful() {
		String post = "{\"email\": \"peter@klaven\"}";
		given()
		.contentType(ContentType.JSON)
		.body(post)
		.when()
		.post("/api/login")
		.then()
		.statusCode(400)
		.log()
		.all();
	}
	
	@Test
	public void testPostLoginSuccessful() {
		String post2 = "{\"email\": \"peter@klaven\",\"password\": \"cityslicka\"}";
		given()
		.contentType(ContentType.JSON)
		.body(post2)
		.when()
		.post("/api/login")
		.then()
		.statusCode(200)
		.log()
		.all();
	}
	@Test
	public void testGetSingleUserNotFound() {
		given()
		.when()
		.get("/api/users/23")
		.then()
		.contentType(ContentType.JSON)
		.statusCode(404);
	}
}
