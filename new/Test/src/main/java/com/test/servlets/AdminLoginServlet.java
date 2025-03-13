package com.test.servlets;

import com.test.services.AdminAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    private final AdminAuthService authService = new AdminAuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Simply forward to the login page
        request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ServletContext servletContext = getServletContext(); // Get the ServletContext

        // Updated to include ServletContext parameter
        if (authService.authenticateAdmin(username, password, servletContext)) {
            // Success - create session and redirect to admin dashboard
            HttpSession session = request.getSession();
            session.setAttribute("adminUsername", username);
            session.setAttribute("isAdmin", true);

            response.sendRedirect("adminDashboard.jsp");
        } else {
            // Failed login
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        }
    }
}