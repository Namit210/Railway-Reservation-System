<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Profile</title>
</head>
<body>
    <h2>Update Profile Details</h2>

    <form action="update" method="POST">
        <label for="name">Name:</label><br>
        <input type="text" id="name" name="name" value="<%= request.getAttribute("name") != null ? request.getAttribute("name") : "" %>"><br><br>
		
		<label for="username">Username:</label><br>
        <input type="text" id="username" name="username" value="<%= request.getAttribute("username") != null ? request.getAttribute("username") : "" %>" ><br><br>
		
		<label for="pass">Username:</label><br>
        <input type="password" id="pass" name="pass" value="<%= request.getAttribute("pass") != null ? request.getAttribute("username") : "" %>" ><br><br>
		
		<label for="dob">Date Of Birth:</label><br>
        <input type="date" id="dob" name="dob" value="<%= request.getAttribute("dob") != null ? request.getAttribute("dob") : "" %>" ><br><br>
		
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>"><br><br>

        <label for="phone">Phone Number:</label><br>
        <input type="text" id="phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>" ><br><br>

     
        <input type="submit" value="Update">
    </form>
</body>
</html>
