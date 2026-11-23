package com.restAssured;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.ObjectUtils;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiPractice {
    public final static String BASE_URL = "https://reqres.in/api";
    public static String GET_USERS = "/users";
    public final static String GET_SINGLE_USER = "/users/7";
    public final static String GET_PAGE2_USERS = "/users?page=2";
    public static Response response;
    public static ValidatableResponse validatableResponse;
    public static RequestSpecification requestSpecification;
    private static final String GET_TOKEN = "/login";
    private static String token;

    @Test
    void getAllUsers() {
        response = given().queryParam("page", 1)
                .when()
                .get(BASE_URL + GET_USERS);

       validatableResponse= response.then()
                .statusCode(200);
                validatableResponse.log().body();
                System.out.println("THe Status Code is"+response.getStatusCode());
    }
@Test
    void getSingleUser() {
        response = given().pathParams("userId", 7)
                .when()
                .get(BASE_URL + GET_USERS+"/{userId}");

       validatableResponse= response.then()
                .statusCode(200)
                .log().body();
       validatableResponse.log().cookies();
       validatableResponse.statusCode(200);
       System.out.println("The Status Code is"+response.getStatusCode());

    }
@Test
    void getPage2Users() {
        response = given().queryParam("page", 2)
                .when()
                .get(BASE_URL + GET_USERS);

      response.then()
                .statusCode(200).
               log().body();
      System.out.println("The Status Code is"+response.getStatusCode());
    }
    @Test
    void createUser() {
        String requestBody= """
                {"name":"Mugesh","job": "QA Engineer"}
                """;
      requestSpecification= given()
                .header("Content-Type", "application/json")
                .body(requestBody);
                response =requestSpecification.when()
                .post(BASE_URL + GET_USERS);
      validatableResponse= response.then();
      validatableResponse.log().body();
      validatableResponse.statusCode(201);
      validatableResponse.body("name",equalTo("Mugesh"));
    }
    @Test
    void updateUser() {
        String requestBody= """
                {"name":"Mugesh","job": "QA Engineer"}
                """;
        requestSpecification= given()
                .header("Content-Type", "application/json")
                .body(requestBody).log().uri();
        response =requestSpecification.when()
                .put(BASE_URL + GET_USERS+"/7");
        validatableResponse= response.then();
        validatableResponse.log().body();
        validatableResponse.statusCode(200);
        validatableResponse.body("name",equalTo("Mugesh"));
    }
    @Test
    void deleteUser() {
        String requestBody= """
                {"name":"Mugesh","job": "QA Engineer"}
                """;
        requestSpecification= given()
                .header("Content-Type", "application/json")
                .body(requestBody).log().uri();
        response =requestSpecification.when()
                .delete(BASE_URL + GET_USERS+"/4");
        validatableResponse= response.then();
        validatableResponse.log().body();
        validatableResponse.statusCode(204);
    }
    @Test
    void getUserID() {
        String requestBody= """
                {"name":"Mugesh","job": "QA Engineer"}
                """;
        requestSpecification= given()
                .header("Content-Type", "application/json")
                .body(requestBody);
        response =requestSpecification.when()
                .post(BASE_URL + GET_USERS);
        validatableResponse= response.then();
        String extractID=validatableResponse.extract().path("id");
        validatableResponse.log().body();
        validatableResponse.statusCode(201);
        validatableResponse.body("name",equalTo("Mugesh"));
        System.out.println("The ID is :"+extractID);
    }
    @Test
    void getToken() {
        String requestBody= """
                {"email":"eve.holt@reqres.in","password": "cityslicka"}
                """;
        requestSpecification= given()
                .header("Content-Type", "application/json")
                .body(requestBody);
        response =requestSpecification.when()
                .post(BASE_URL+ GET_TOKEN);
        validatableResponse= response.then();
        token= validatableResponse.extract().path("token");
        validatableResponse.log().body();
        validatableResponse.statusCode(200);
        System.out.println("The token is :"+token);
    }
    @Test
void useToken(){
        getToken();
        requestSpecification= given().header("x-api-key", token)
                .pathParam("userId", 3);
        response =requestSpecification.when()
                .get(BASE_URL +"/app/"+"{userId}");
        validatableResponse= response.then();
        validatableResponse.log().body();
        validatableResponse.statusCode(200);
}

}
