package api;

import base.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import utils.DbUtils;
import utils.TestDataFactory;

import java.sql.ResultSet;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StudentApiTest extends BaseTest {

    private void createStudent(String username) {

        given()
                .contentType(ContentType.JSON)
                .body("""
            {
                "studentname": "John",
                "address": "Delhi",
                "phone": "9999999999",
                "username": "%s",
                "password": "%s"
            }
            """.formatted(username, "pass123"))
                .when()
                .post("/student/save")
                .then()
                .log().all()
                .statusCode(200);
    }

    // 1. Basic API Test
    @Test
    void shouldCreateStudent() {

        String username = TestDataFactory.uniqueUsername();
        createStudent(username);
    }

    // 2. API + DB Validation
    @Test
    void shouldCreateStudentAndVerifyInDb() throws Exception {

        String username = TestDataFactory.uniqueUsername();

        given()
                .contentType("application/json")
                .body(TestDataFactory.createStudentRequest(username))
                .when()
                .post("/student/save");

        ResultSet rs = DbUtils.getStudentByUsername(username);
        assertTrue(rs.next());
    }

    // 3. Login Test
    @Test
    void shouldLoginSuccessfully() {

        String username = TestDataFactory.uniqueUsername();

        createStudent(username);

        String loginBody = """
        {
          "username": "%s",
          "pass": "pass123"
        }
        """.formatted(username);

        String token =
                given()
                        .contentType(ContentType.JSON)
                        .body(loginBody)
                        .when()
                        .post("/student/login")
                        .then()
                        .extract()
                        .asString();

        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/student/getAllStudents")
                .then()
                .statusCode(200);
    }

    // 4. Negative Login Test
    @Test
    void shouldFailLogin() {

        String loginBody = """
        {
          "username": "wrong_user",
          "pass": "wrong_pass"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(loginBody)
                .when()
                .post("/student/login")
                .then()
                .log().all()
                .statusCode(400);
    }
}