package com.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务 - 示例代码（包含一个 NullPointerException bug）
 */
public class UserService {

    private Map<String, User> userDatabase;

    public UserService() {
        this.userDatabase = new HashMap<>();
        // 初始化一些用户
        addUser("1", "Alice", "alice@example.com");
        addUser("2", "Bob", "bob@example.com");
    }

    public void addUser(String id, String name, String email) {
        User user = new User(id, name, email);
        userDatabase.put(id, user);
    }

    /**
     * 根据 ID 获取用户信息
     * BUG: 当用户不存在时，返回的 user 可能为 null
     */
    public String getUserById(String id) {
        User user = userDatabase.get(id);
        // 这里会触发 NullPointerException
        return user.getName().toUpperCase();
    }

    /**
     * 获取所有用户
     */
    public Map<String, User> getAllUsers() {
        return userDatabase;
    }

    /**
     * 用户类
     */
    static class User {
        private String id;
        private String name;
        private String email;

        public User(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    public static void main(String[] args) {
        UserService service = new UserService();

        System.out.println("=== 用户服务测试 ===");

        // 正常情况
        try {
            String name = service.getUserById("1");
            System.out.println("用户 1: " + name);
        } catch (Exception e) {
            System.err.println("ERROR: 获取用户 1 失败 - " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 触发 bug: 用户不存在
        try {
            String name = service.getUserById("999");  // 不存在的用户
            System.out.println("用户 999: " + name);
        } catch (Exception e) {
            System.err.println("ERROR: 获取用户 999 失败 - " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
