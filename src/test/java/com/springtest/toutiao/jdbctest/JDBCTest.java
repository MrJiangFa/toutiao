package com.springtest.toutiao.jdbctest;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String URL = "jdbc:mysql://localhost:3306/jdbcdemo?useUnicode=true&characterEncoding=" +
                "utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String USER = "root";
        String PASSWORD = "951117fa";
        Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
        String sql = "select * from account where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,2);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            int id = res.getInt("id");
            String name = res.getString("name");
            System.out.println(id+name);
        }
    }
}
