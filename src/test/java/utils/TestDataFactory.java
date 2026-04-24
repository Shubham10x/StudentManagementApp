package utils;

import java.util.UUID;


public class TestDataFactory {

    public static String uniqueUsername() {
        return "user_" + UUID.randomUUID().toString().substring(0, 5);
    }

    public static String createStudentRequest(String username) {
        return """
    {
      "studentname": "TestUser",
      "address": "Delhi",
      "phone": "9999999999",
      "username": "%s",
      "password": "pass123"
    }
    """.formatted(username);
    }
}