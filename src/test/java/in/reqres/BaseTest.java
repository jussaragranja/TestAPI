package in.reqres;

import static io.restassured.RestAssured.baseURI;

import org.junit.Before;

public class BaseTest {

	@Before
	public void testeUri() {
		baseURI = "https://reqres.in/";
	}
	
}
