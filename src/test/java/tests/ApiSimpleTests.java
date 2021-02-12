package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static utils.FileUtils.readStringFromFile;

public class ApiSimpleTests {
    @BeforeEach
    void beforeEach() {
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void listUsersTest() {
        given()
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .log().body()
                .body("total", is(12));
    }

    @Test
    void SingleUserTest() {
        given()
                .when()
                .get("/users/7")
                .then()
                .statusCode(200)
                .log().body()
                .body("data.first_name", is("Michael"));
    }

    @Test
    void SingleUserNotFound() {
        given()
                .when()
                .get("/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    void createNewUserTest() {
        String data = "{\n" +
                "\"name\": \"Demon\",\n" +
                "\"job\": \"On vam ne Demon\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("name", is("Demon"));
    }

    @Test
    void createNewUserTest2() {
        String data = readStringFromFile("src/test/resources/Login_Job.json");

        given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .body("name", is("Demon"));
    }
}
