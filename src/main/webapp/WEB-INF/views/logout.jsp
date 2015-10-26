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
</head>
<body>
<!-- Begin #header -->
<div id="header">
  <div><img src="${pageContext.request.contextPath}/img/logo.png"></div>
</div>
<!-- End #header --> 
<div id="topnav"></div>
<!-- Begin #body -->
<div align="center">
<div id="body">
<div id="content">
<div class="login">
	<div><font color="green">
						<%
							String messages = (String) request.getAttribute("messages");
							if (messages != null && messages.trim().equals("resetpassword")) {
								out.println("Your Password has been reset.");
							}
							if (messages != null && messages.trim().equals("resetpasswordsuccess")) {
								out.println("Your password has been reset. A password reset link has been sent to your registered email id.");
							}
						%>
	</font></div>
    Please <a href="${pageContext.request.contextPath}/portal/login"><b>CLICK HERE</b></a> to Flight Login Page
</div>
<div class="login-help">
</div>
</div>
</div>
</div>
<!-- End #body -->
</body>
</html>