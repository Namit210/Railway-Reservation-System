<%@ page import="java.sql.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.Period" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    response.setHeader("Cache-Control", "no-store");  // HTTP 1.1
    response.setHeader("Pragma", "no-cache");         // HTTP 1.0
    response.setDateHeader("Expires", 0);             // Prevent caching
%>

<%

    
    if (session == null || session.getAttribute("uname") == null) {
        response.sendRedirect("login.html"); // If not logged in, redirect to login
        return;
    }

    String uname = (String) session.getAttribute("uname");
    String pass = (String) session.getAttribute("pass");

    // Database connection and query execution
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "amit", "password");
    String checkUserSql = "SELECT * FROM users WHERE pass = ? AND username = ?";
    PreparedStatement checkUserStmt = conn.prepareStatement(checkUserSql);
    checkUserStmt.setString(1, pass);
    checkUserStmt.setString(2, uname);
    ResultSet userRs = checkUserStmt.executeQuery();
    
    if (userRs.next()) {
        // Get user's date of birth from the result set
        java.sql.Date dob = userRs.getDate("dob");

        // Convert java.sql.Date to LocalDate
        LocalDate dateOfBirth = dob.toLocalDate();

        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Calculate age using Period
        Period period = Period.between(dateOfBirth, currentDate);
        int age = period.getYears();

        // If the birthday hasn't passed this year, subtract 1 from the age
        if (currentDate.isBefore(dateOfBirth.withYear(currentDate.getYear()))) {
            age--;
        }

        // Fetch train booking history for the logged-in user
        String bookingHistorySql = "SELECT train_name, journey_date, status, seat_type FROM bookings WHERE username = ?";
        PreparedStatement bookingStmt = conn.prepareStatement(bookingHistorySql);
        bookingStmt.setString(1, uname);
        ResultSet bookingRs = bookingStmt.executeQuery();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Profile Page</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
    }

    .container {
      width: 100%;
      max-width: 1200px;
      margin: 0 auto;
    }

    .header {
      background-color: #333;
      color: white;
      padding: 15px 0;
      text-align: center;
    }

    .nav-bar a {
      color: white;
      margin: 0 15px;
      text-decoration: none;
      font-size: 16px;
    }

    .nav-bar a:hover, .nav-bar a.active {
      text-decoration: underline;
    }

    .profile-container {
      display: flex;
      margin-top: 20px;
    }

    .profile-sidebar {
      width: 25%;
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }

    .profile-pic {
      width: 100%;
      height: auto;
      border-radius: 50%;
      margin-bottom: 20px;
    }

    .user-name {
      font-size: 24px;
      margin-bottom: 10px;
    }

    .edit-btn {
      background-color: #007bff;
      color: white;
      border: none;
      padding: 10px;
      border-radius: 5px;
      cursor: pointer;
      font-size: 14px;
    }

    .edit-btn:hover {
      background-color: #0056b3;
    }

    .profile-info {
      width: 75%;
      margin-left: 20px;
      background-color: white;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    }

    h3 {
      font-size: 20px;
      margin-bottom: 15px;
    }

    ul {
      list-style: none;
    }

    ul li {
      font-size: 16px;
      margin: 10px 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 15px;
    }

    table, th, td {
      border: 1px solid #ddd;
    }

    th, td {
      padding: 10px;
      text-align: left;
    }

    th {
      background-color: #f4f4f4;
    }

    .history-container {
      max-height: 300px;
      overflow-y: auto;
      border: 1px solid #ddd;
      border-radius: 5px;
      padding: 0px;
    }

    .booking-history table thead {
      font-size: 20px;
      margin-bottom: 15px;
      position: sticky;
      top: 0;
      background-color: #fff;
      padding: 10px;
      z-index: 1;
    }

    footer {
      text-align: center;
      padding: 10px;
      background-color: #333;
      color: white;
      margin-top: 30px;
    }
  </style>
</head>
<body>
  <div class="container">
    <header class="header">
      <h1>Welcome to Railway Booking</h1>
      <nav class="nav-bar">
        <a href="book.html">Book Tickets</a>
        <a href="#">Profile</a>
        <form action="logout" method="post" style="display:inline;">
        <button type="submit">Logout</button>
    </form>
      </nav>
    </header>

    <div class="profile-container">
      <div class="profile-sidebar">
        <img src="profile.jpeg" alt="Profile Picture" class="profile-pic">
        <h2 class="user-name"><%= uname %></h2>
        <button class="edit-btn" onclick="window.location.href='updateProfile.jsp';">Edit Profile</button>

      </div>

      <div class="profile-info">
        <section class="personal-info">
          <h3>Personal Information</h3>
          <ul>
            <li><strong>Name:</strong> <%= userRs.getString("name") %></li>
            <li><strong>Email:</strong> <%= userRs.getString("email") %></li>
            <li><strong>Phone:</strong> <%= userRs.getString("phone") %></li>
            <li><strong>Age:</strong> <%= age %></li>
          </ul>
        </section>

        <!-- Booking History Section -->
        <section class="booking-history">
          <h3>Booking History</h3>
          <div class="history-container">
            <table>
              <thead>
                <tr>
                  <th>Train Name</th>
                  <th>Journey Date</th>
                  <th>Seat Type</th>
                 
                </tr>
              </thead>
              <tbody>
                <%
                    while (bookingRs.next()) {
                        String trainName = bookingRs.getString("train_name");
                        Date journeyDate = bookingRs.getDate("journey_date");
                        String seat = bookingRs.getString("seat_type");
                %>
                <tr>
                  <td><%= trainName %></td>
                  <td><%= journeyDate %></td>
                  <td><%= seat %></td>
                  
                </tr>
                <%
                    }
                    // Close resources after use
                    if (bookingRs != null) bookingRs.close();
                    if (bookingStmt != null) bookingStmt.close();
                %>
              </tbody>
            </table>
          </div>
        </section>

        <!-- Preferences Section -->
        <section class="preferences">
          <h3>Travel Preferences</h3>
          <ul>
            <li><strong>Preferred Seat:</strong> Window Seat</li>
            <li><strong>Notifications:</strong> Enabled</li>
          </ul>
         
        </section>
      </div>
    </div>
  </div>

  <footer>
    <p>&copy; 2025 Railway Booking</p>
  </footer>
</body>
</html>

<%
    } else {
        out.println("User not found!");
    }
    // Close resources after use
    if (userRs != null) userRs.close();
    if (checkUserStmt != null) checkUserStmt.close();
    if (conn != null) conn.close();
%>
