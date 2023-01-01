<%@ page import="com.example.main.MultiJabberServer" %>
<%@ page import="java.sql.Connection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Connection conn = (Connection) session.getAttribute("conn");
    if(conn!=null) {
        new MultiJabberServer(conn);
    }
%>
</body>
</html>
