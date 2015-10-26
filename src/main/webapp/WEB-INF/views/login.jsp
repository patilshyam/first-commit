<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet">
<title>Login Page</title>
<script type="text/javascript">
function validateForm() {
    var x = document.forms["loginForm"]["j_username"].value;
    if (x == null || x == "") {
        alert("Please fill the User Name.");
        document.forms["loginForm"]["j_username"].focus();
        return false;
    }

    var x = document.forms["loginForm"]["j_password"].value;
    if (x == null || x == "") {
        alert("Please fill the Password.");
        document.forms["loginForm"]["j_password"].focus();
        return false;
    }
}
</script>
</head>
<body onload='document.loginForm.j_username.focus();'>
<!-- Begin #header -->
<div id="header">
  <div><img src="${pageContext.request.contextPath}/img/logo.png"></div>
</div>
<!-- End #header --> 
<div id="topnav"></div>
<!-- Begin #body -->
<div align="center">
<div id="body">
<div class="login">
    <h1>Login to Customer Enrollment Portal</h1>
    <form name='loginForm' method="post" action="${pageContext.request.contextPath}/j_spring_security_check" onsubmit="return validateForm()">
      <p><input type='text' name='j_username' placeholder="Username" autocomplete="off"></p>
      <p><input type='password' name='j_password' placeholder="Password" autocomplete="off"></p>
					<div align="center"><font color="red">
						<%
							String errorString = (String) request.getAttribute("error");
							if (errorString != null && errorString.trim().equals("credentialsExpired")) {
								out.println("Password Expired. Please reset your password");
							}
							if (errorString != null && errorString.trim().equals("true")) {
								out.println("Invalid username or password. Please try again.");
							}
							if (errorString != null && errorString.trim().equals("lock_error")) {
								out.println("Your account is locked.Plese check registered email to reset password.");
							}
						%>
					</font></div>
      <p class="submit"><input type="submit" name="submit" value="Login"></p>
    </form>
  </div>

  <div class="login-help">
    <p>Forgot your password? <a href="forgotpassword.html">Click here to reset it</a>.</p>
  </div>
</div>
</div>
<!-- End #body -->
</body>
</html>