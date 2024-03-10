<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>remote-ejb-test</title>


<h1>Enter host name to test remote EJB connectivity ... </h1>

<form action="RemoteEjbServlet" method="post">
host: <input type="text" name="host"><br/>
<input type="submit"> <br/>

</form>
<br/>
<br/>
<c:if test="${not empty message}">
    <h1>${message}</h1>
</c:if>
<p>&copy Vimlesh 2024</p>
</body>
</html>
