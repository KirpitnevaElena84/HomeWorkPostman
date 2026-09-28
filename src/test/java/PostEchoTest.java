package ru.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;


public class PostEchoTest {

    static {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    void shouldSendPlainTextBodyAndVerifyResponse() {
        given()
                .contentType("text/plain; charset=UTF-8")
                .body("some data")
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", equalTo("some data"));
    }

    @Test
    void shouldFailOnWrongAssertion() {
        given()
                .contentType(ContentType.TEXT)
                .body("some data")
                .when()
                .post("https://postman-echo.com/post")
                .then()
                .body("data", not(equalTo("wrong data")));
        // проверяем, что ответ НЕ равен "wrong data"
    }


    @Test
    void shouldHandleCyrillicWithUTF8Encoding() {
        String cyrillicData = "тестовые данные 🚀";

        given()
                .contentType("text/plain; charset=UTF-8")
                .body(cyrillicData)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("data", equalTo(cyrillicData));
    }
}