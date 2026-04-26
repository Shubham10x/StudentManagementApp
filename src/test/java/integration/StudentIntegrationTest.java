package integration;

import com.School.StudentManagement.StudentManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = StudentManagementApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class StudentIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnAllStudents() {

        String username = "testuser";

        // 1. Create user
        given()
                .port(port)
                .contentType("application/json")
                .body("""
                {
                    "studentname": "John",
                    "address": "Delhi",
                    "phone": "9999999999",
                    "username": "%s",
                    "password": "pass123"
                }
            """.formatted(username))
                .when()
                .post("/student/save");

        // 2. Login → get token
        String token =
                given()
                        .port(port)
                        .contentType("application/json")
                        .body("""
                        {
                          "username": "%s",
                          "pass": "pass123"
                        }
                    """.formatted(username))
                        .when()
                        .post("/student/login")
                        .then()
                        .extract()
                        .asString();

        // 3. Call secured API
        given()
                .port(port)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/student/getAllStudents")
                .then()
                .statusCode(200);
    }
}