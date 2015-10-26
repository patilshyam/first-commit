<%@ page import="org.springframework.web.bind.annotation.SessionAttributes"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" session="false" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<title>Flight Booking</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<script type="text/javascript">
	function checkForm(form) {
		if(form.password.value == ""){
			alert("Please Enter a password!");
			form.password.focus();
			return false;
		}
		if(form.confirmPassword.value == ""){
			alert("Please Enter a confirm password!");
			form.confirmPassword.focus();
			return false;
		}
		
		if(form.oldPassword.value == ""){
			alert("Please Enter a OLD password!");
			form.confirmPassword.focus();
			return false;
		}
		
		if(form.confirmPassword.value != form.password.value){
			alert("Please check password and Confirm password must be same!");
			form.confirmPassword.focus();
			return false;
		}
		
		if(form.oldPassword.value == form.password.value){
			alert("Please check new password and old password can not be same");
			form.confirmPassword.focus();
			return false;
		}
		
		if (form.password.value.length < 10) {
			alert("Password must contain at least 10 characters!");
			form.password.focus();
			return false;
		}
		re = /[0-9]/;
		if (!re.test(form.password.value)) {
			alert("Password must contain at least one number (0-9)!");
			form.password.focus();
			return false;
		}
		re = /[a-z]/;
		if (!re.test(form.password.value)) {
			alert("Password must contain at least one lowercase letter (a-z)!");
			form.password.focus();
			return false;
		}
		re = /[A-Z]/;
		if (!re.test(form.password.value)) {
			alert("Password must contain at least one uppercase letter (A-Z)!");
			form.password.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<!-- Begin #header -->
<div id="header">
  <div><img src="${pageContext.request.contextPath}/img/logo.png"></div>
  <div id="fav_actions">
    <ul id="quicklinks">
    </ul>
  </div>
</div>
<!-- End #header -->
<div id="topnav"></div>
<!-- Begin #body -->
<div id="body">
  <div id="content">
  <div class="module-inner">
  <h2 class="module-title">Password expires in 30 days. Please change your password</h2>
   <div class="article module-content">
    <form:form name="resetPasswordForm" action="${pageContext.request.contextPath}/portal/processchangepassword.html" method="post" modelAttribute="password" onsubmit="return checkForm(this);">
             <div id="frmdtdv">
             <div>
             			<%
							String errorString = (String) request.getAttribute("messages");
							if (errorString != null && errorString.trim().equals("passwordnotmatch")) {
								out.println("Old Password does not match");
							}
						%>
            	<form:label path="oldPassword">Old Password:</form:label>
                <form:password path="oldPassword" class="text" size="40"/>
                <form:label path="password">New Password:</form:label>
                <form:password path="password" class="text" size="40"/>
                <form:label path="confirmPassword">Confirm New Password:</form:label>
                <form:password path="confirmPassword" class="text" size="40"/>
                <input type="submit" value="Submit" class="button submit" />
            </div>
            <div>
             <ul>
             <li>New Password and Confirm New Password should be same.</li>
             <li>Password should contain at least 10 characters.</li>
             <li>Password should contain at least 1 Uppercase letter.</li>
             <li>Password should contain at least 1 Lowercase letter.</li>
             <li>Password should contain at least 1 Numeric character.</li>
             </ul>
             </div>
            </div>
            <div class="clear"></div>
      </form:form>
      </div>
      </div>
  </div>
  <div class="copyrt">Copyright 2010 &copy; IDEAS Republique Democratique du Congo.</div>
  <div class="clear"></div>
</div>
<!-- End #body -->
</body>
</html>