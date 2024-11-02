package com.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/register")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String DB_URL = "jdbc:mysql://localhost:3306/conference";
        final String USER = "root";
        final String PASSWORD = "Admin123*";

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (name.isEmpty() && email.isEmpty() && password.isEmpty()) {
            response.sendRedirect("index.html");
            return;
        }

        // Establishing a Database Connection
        try {
        // Load the MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establishing a Database Connection
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO participants (name, email, password) VALUES (?, ?, ?)")) {
            statement.setString(1, name); 
            statement.setString(2, email);
            statement.setString(3, password); 
            statement.executeUpdate();
            request.getRequestDispatcher("confirm.jsp").forward(request, response);
        }

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        response.getWriter().println("MySQL JDBC Driver not found.");
    } catch (SQLException e) {
        e.printStackTrace();
        response.getWriter().println("Registration failed: " + e.getMessage());
    }
    }
}
