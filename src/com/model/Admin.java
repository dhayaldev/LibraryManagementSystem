package com.model;

import java.sql.*;
import com.util.DBConnection;

public class Admin {
    public static boolean login(String username, String password) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM admin WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            return false;
        }
    }
}
