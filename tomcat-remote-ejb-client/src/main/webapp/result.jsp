<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
<body>
<center>
<h1>
    <u>Result</u>
</h1>
<%
List result= (List) request.getAttribute("message");
Iterator it = result.iterator();

out.println("<br> <br><br>");
while(it.hasNext()){
out.println(it.next()+"<br>");
}
%>
</body>
</html>