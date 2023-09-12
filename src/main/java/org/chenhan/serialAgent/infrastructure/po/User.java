package org.chenhan.serialAgent.infrastructure.po;

/**
 * @Author: chenhan
 * @Description: 测试数据库
 * @ProjectName: SerialNumberAgent
 * @Date: 2023/9/12 11:12
 **/
public class User {
    private int id;
    private String username;
    private String email;

    // 构造方法
    public User() {
    }

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString 方法，用于打印对象信息
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
