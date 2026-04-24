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
                .body(TestDataFactory.createStudentRequest(username))
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

        createStudent(username);

        ResultSet rs = DbUtils.getStudentByUsername(username);

        assertTrue(rs.next(), "Student not found in DB");

        String studentName = rs.getString("student_name");
        assertEquals("TestUser", studentName);
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
                        .log().all()
                        .statusCode(200)
                        .extract()
                        .asString();

        assertNotNull(token);
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