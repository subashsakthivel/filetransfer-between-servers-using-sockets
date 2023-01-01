<%@ page import="com.examples.testserver1.DBFunctions" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="com.examples.testserver1.FileGenerator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
      Connection conn = (Connection) session.getAttribute("conn");
      FileGenerator fileGenerator = new FileGenerator("subtest1","sub-test", "postgres", "password","test",conn);
      Thread t1 = new Thread(fileGenerator);
      t1.start();
      System.out.println("file generating...");
    %>
</head>
<body>

</body>
</html>
