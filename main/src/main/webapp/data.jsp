<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.main.DBFunctions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%!
    DBFunctions db = new DBFunctions();
%>
<body>
<h1>data page</h1>
<%
    Connection conn = (Connection) session.getAttribute("conn");

    if (conn != null) {
        out.print("<h4> DB Connection Established</h4>");
        ResultSet rs = db.fetch_data(conn, "maintest");

        out.print("<table border=1><tr>" + "<th>id</th>" + "<th>filename</th>" + "<th>creation time</th>" +  "<th>location</th>" + "<th>remote address</th>"+"</tr>");
        while (true) {
            try {
                if (!rs.next()) break;
                out.print("<tr><td>" + rs.getString("id") + "</td><td>"+rs.getString("name")+"</td><td>"+rs.getString("creation_time")+"</td><td>"+rs.getString("location")+"</td><td>"+rs.getString("request")+"</td></tr>");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        out.print("</table>");
    } else out.print("<h4> Connection Failed</h4>");
%>
</body>
</html>
