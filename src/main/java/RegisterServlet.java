import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        
        PrintWriter out = res.getWriter();
        String uname = req.getParameter("username");
        String name = req.getParameter("full-name");
        String pass = req.getParameter("password");
        String cpass = req.getParameter("confirm-password");
        String dob = req.getParameter("dob");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        
        // Check if password and confirm password match
        if(!cpass.equals(pass)) {
            out.println("<p>Password and Confirm Password should match.</p>");
            RequestDispatcher rd = req.getRequestDispatcher("register.html"); // redirect back to the registration page
            rd.include(req, res);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet userRs = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "amit", "password");
            System.out.println("Connected to: " + conn);
            
            // SQL query to check if the username already exists
            String checkUserSql = "SELECT * FROM auth WHERE username = ?";
            stmt = conn.prepareStatement(checkUserSql);
            stmt.setString(1, uname);
            userRs = stmt.executeQuery();
            
            if (userRs.next()) {
                out.println("<p>Username already taken. Please choose another.</p>");
                RequestDispatcher rd = req.getRequestDispatcher("register.html"); // redirect to registration page
                rd.include(req, res);
            } else {
                // SQL queries to insert a new user into 'auth' and 'users' tables
                String sql = "INSERT INTO auth (username, password) VALUES (?, ?)";
                String sql1 = "INSERT INTO users (username, pass, name, dob, email, phone) VALUES (?, ?, ?, ?, ?, ?)";

                // Prepare statements
                stmt = conn.prepareStatement(sql);
                stmt1 = conn.prepareStatement(sql1);
                
                stmt.setString(1, uname);
                stmt.setString(2, pass);
                stmt1.setString(1, uname);
                stmt1.setString(2, pass);
                stmt1.setString(3, name);
                stmt1.setString(4, dob);
                stmt1.setString(5, email);
                stmt1.setString(6, phone);
                
                // Execute the update queries
                int rowsAffected = stmt.executeUpdate();
                int row2 = stmt1.executeUpdate();
                
                // Check if insertion was successful
                if (rowsAffected > 0 && row2 > 0) {
                    out.println("<p>Registration successful!</p>");
                    RequestDispatcher rd = req.getRequestDispatcher("profile.jsp");
                    req.setAttribute("uname", uname);
                    req.setAttribute("pass", pass);
                    rd.forward(req, res);
                } else {
                    out.println("<p>Registration failed. Please try again.</p>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error occurred during registration. Please try again later.</p>");
        } finally {
            try {
                // Close resources
                if (userRs != null) userRs.close();
                if (stmt != null) stmt.close();
                if (stmt1 != null) stmt1.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
