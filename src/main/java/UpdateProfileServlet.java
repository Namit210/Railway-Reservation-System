import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch the form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String name = request.getParameter("name");
        
        boolean updateSuccess = updateUserProfile(username, email, phone, dob, name);

        if (updateSuccess) {
            // Send updated values to the JSP to show the changes
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("dob", dob);
            request.setAttribute("name", name);

            // Forward the request back to the form page to show the updated information
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        } else {
            // If update fails, set error and forward to the update page
            request.setAttribute("errorMessage", "Failed to update profile!");
            request.getRequestDispatcher("updateProfile.jsp").forward(request, response);
        }
    }

    
    private boolean updateUserProfile(String username, String email, String phone, String dob, String name) throws SQLException {
    	Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet userRs = null;
        
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "amit", "password");
	        String sql1 = "INSERT INTO users (username, pass, name, dob, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        return true; // Simulating a successful update
    }
}
