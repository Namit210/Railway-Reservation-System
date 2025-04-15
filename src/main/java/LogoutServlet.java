

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Invalidate session to log out the user
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }

        // Set headers to prevent the browser from caching
        response.setHeader("Cache-Control", "no-store");  // HTTP 1.1
        response.setHeader("Pragma", "no-cache");         // HTTP 1.0
        response.setDateHeader("Expires", 0);              // Prevent caching

        // Redirect to the login page
        response.sendRedirect("login.html");
    }
}

