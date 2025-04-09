import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class BookTicketServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        
        PrintWriter out = res.getWriter();
        
        // Retrieve data from the form
        String train = req.getParameter("train");
        String journeyDate = req.getParameter("journey-date");
        String seat = req.getParameter("seat");
        String username = (String) req.getParameter("username"); // Assuming user is logged in and username is in session
        
        // Check if the user is logged in
//        if (username == null || username.isEmpty()) {
//            res.sendRedirect("login.html");
//            return;
//        }

        // Connect to the database and insert booking record
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "amit", "password");
            System.out.println("Connected! to " + conn);
            
            // Prepare SQL query to insert the booking
            String sql = "INSERT INTO bookings (username, train_name, journey_date, seat_type) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, username);
            stmt.setString(2, train);
            stmt.setDate(3, Date.valueOf(journeyDate)); // Convert string to Date
            stmt.setString(4, seat);
            
            // Execute the update query (use executeUpdate for insert)
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                out.println("Ticket booked successfully!");
                RequestDispatcher rd = req.getRequestDispatcher("profile.jsp"); // Redirect to profile page after booking
                rd.forward(req, res);
            } else {
                out.println("Failed to book ticket. Please try again.");
            }

            // Clean up resources
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error occurred during booking. Please try again later.");
        }
    }
}
