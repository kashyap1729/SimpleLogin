<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src='https://www.google.com/recaptcha/api.js'></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="JavaScript/JavaScript.js"></script>
<script> 


</script>
<title>Garanti Bank Login Page</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body onload="sendUrlParameters()">
	<form method="post" action="LoginCheck">
	<header>Garanti Bank</header>
  <label>Username <span>*</span></label>
  <input type="text" name="uname" required="required" />
  <label>Password <span>*</span></label>
  <input type="password" name="password" required="required" />
  <input type="hidden" name="url" value=""  /> 
  <div class="g-recaptcha" data-sitekey="6LfFhjYUAAAAAFEHDsXcxU77qf_S4PP8PIWdEN-L"> </div> 
  <div style="color:red">&nbsp&nbsp&nbsp&nbsp&nbsp${errorMessage}</div>
  <button>Login</button>
	</form>
</body>
</html>