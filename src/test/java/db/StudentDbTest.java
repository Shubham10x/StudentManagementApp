package db;

import org.junit.jupiter.api.Test;
import utils.DbUtils;
import utils.TestDataFactory;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentDbTest {

    @Test
    void shouldFetchStudentFromDb() throws Exception {

        // ⚠️ make sure this user exists in DB
        ResultSet rs = DbUtils.getStudentByUsername("existing_user");

        assertTrue(rs.next());
    }
}
