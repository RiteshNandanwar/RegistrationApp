<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<html>
<head>
	<title>Custom Login Page</title>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
	
</head>
<body>
	<div class="container mt-5">
		<h3>My Custom Login Page</h3>
		<form:form class="form" action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">
			<c:if test="${param.error != null }">
				<div class="alert alert-danger" style="width:50%">
					<h5> Sorry ! You entered invalid username / password </h5>
				</div>
			</c:if>
			<div class="form-group]">
				<p>	
					User Name: <input type="text" name="username"/>
				</p>
			</div>
			<div class="form-group">
				<p>	
					Password   : <input  type="password" name="password"/>
				</p>
			</div>
			<input type="submit" value="login"/>
		</form:form>
	</div>
</body>
</html>