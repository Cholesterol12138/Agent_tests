package com.demo;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> userDatabase;

    public UserService() {
        this.userDatabase = new HashMap<>();
        addUser("1", "Alice", "alice@example.com");
        addUser("2", "Bob", "bob@example.com");
    }

    public void addUser(String id, String name, String email) {
        User user = new User(id, name, email);
        userDatabase.put(id, user);
    }

    // BUG: 当用户不存在时返回null，后续调用会NPE
    public String getUserById(String id) {
        User user = userDatabase.get(id);
        return user.getName().toUpperCase(); // 这里触发NPE
    }

    public static void main(String[] args) {
        UserService service = new UserService();
        try {
            String name = service.getUserById("999"); // 不存在的用户
            System.out.println("用户: " + name);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    static class User {
        private String id, name, email;
        public User(String id, String name, String email) {
            this.id = id; this.name = name; this.email = email;
        }
        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }
}
