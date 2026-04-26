package db;

import org.junit.jupiter.api.Test;
import utils.DbUtils;
import utils.TestDataFactory;

import java.sql.ResultSet;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentDbTest {

    @Test
    void shouldFetchStudentFromDb() throws Exception {

        String username = TestDataFactory.uniqueUsername();

        // call API instead of direct DB insert
        given()
                .contentType("application/json")
                .body(TestDataFactory.createStudentRequest(username))
                .when()
                .post("/student/save");

        ResultSet rs = DbUtils.getStudentByUsername(username);

        assertTrue(rs.next());
    }
}
