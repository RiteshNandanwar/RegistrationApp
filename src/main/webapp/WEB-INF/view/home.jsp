<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	<title>Home Page</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>

<body>
	<div class="container" style="bg-color:#333">
	<h2 class="header">Spring Developer Pvt. Ltd.</h2>
	<hr>
	Welcome to Spring Developer Community
	
	<hr>
		<p> User : <security:authentication property="principal.username" />  </p>
		<p> Role(s) : <security:authentication property="principal.authorities" /> </p>
  	<hr>
  		<!-- Add a link to point to leaders... for manager -->
  	<security:authorize access="hasRole('MANAGER')">
		<p>
			<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>(Only for Manager Peoples)
		</p>
		<hr>
	</security:authorize>
		<!-- Add a link to point to System.. for Admins   -->
	
	<security:authorize access="hasRole('ADMIN')">
		<p>
			<a href="${pageContext.request.contextPath}/systems">Only Admin Meeting</a>(Only for Administer Peoples)
		</p>
		<hr>
	</security:authorize>
	<br>
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
		<input type="submit" value="Logout">
	</form:form>
	</div>
</body>
</html>