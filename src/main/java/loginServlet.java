import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class loginServlet extends HttpServlet{
    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
        
        PrintWriter out = res.getWriter();
        String uname  = req.getParameter("username");
        String pass = req.getParameter("password");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway","amit","password");
            System.out.println("Connected! to "+ conn);
            
            String sql = "SELECT * FROM auth WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, uname);
            stmt.setString(2, pass);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                out.println("Login successful!");
                
                // Store user information in the session
                HttpSession session = req.getSession();
                session.setAttribute("uname", uname);  // Store username in the session
                session.setAttribute("pass", pass);    // Store password in the session (if necessary)
                
                // Redirect to the profile page
                RequestDispatcher rd = req.getRequestDispatcher("profile.jsp");
                rd.forward(req, res);
            } else {
                out.println("<script>");
                out.println("alert('Invalid login credentials!');");
                out.println("window.location.href = 'login.html';");
                out.println("</script>");
            }
            
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
