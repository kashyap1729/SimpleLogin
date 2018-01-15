<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src='https://www.google.com/recaptcha/api.js'></script>
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="LoginCheck">
		<table>
			<tr>
				<td>User Name</td>
				<td><input type="text" name="uname" required="required"></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" required="required" ></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="login"></td>
			</tr>
		</table>
		<br> <br><br><br><br><br><br><br><br><br><br><br><br>
		<div class="g-recaptcha" data-sitekey="6Le7iDYUAAAAADKF4maA6Vuuix4Rk5HIVgnt0XT9"></div>
	</form>
</body>
</html>