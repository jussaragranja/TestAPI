package in.reqres.core;

import static io.restassured.RestAssured.baseURI;

import org.junit.Before;

/**
 * @author jussaragranja
 * Base Class for Test Execution
 */

public class BaseTest {

	@Before
	public void testeUri() {
		baseURI = "https://reqres.in/api/";
	}
	
}
