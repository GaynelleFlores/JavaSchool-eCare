package com.example.application.controllers;

import com.example.application.models.TestUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.Objects;

@Controller
public class LoginController {
    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("user", new TestUser());
        return "signin";
    }

    @PostMapping("/signin")
    public String greeting(TestUser user, Model model) {
        Connection conn;
        Statement st;
        try {
            conn = DriverManager.getConnection ("jdbc:postgresql://localhost:5432/postgres");
            st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS testTable\n" +
                    "(\n" +
                    "   phoneNumber    varchar(15),\n" +
                    "   password       varchar(15)\n" +
                    ");");
            st.executeUpdate("INSERT INTO testTable VALUES (" + user.phoneNumber + ", " + user.password + ")");
            st.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!Objects.equals(user.phoneNumber, "")) {
            model.addAttribute("phoneNumber", user.phoneNumber);
        } else {
            model.addAttribute("phoneNumber", "unknown");
        }
        return "greeting";
    }

}
