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
<title>flight booking</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
<script type="text/javascript">
function validateForm() {
    var x = document.forms["emailForm"]["userName"].value;
    if (x == null || x == "") {
        alert("Please enter the Username.");
        document.forms["emailForm"]["userName"].focus();
		return false;
    }
    var x = document.forms["emailForm"]["emailId"].value;
    if (x == null || x == "") {
        alert("Please enter the email address.");
        document.forms["emailForm"]["emailId"].focus();
		return false;
    }
    var email = document.forms["emailForm"]["emailId"].value;
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if(!re.test(email)){
    	alert("Please enter a valid email address.");
    	return false;	
    }
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
  <font color="red" size="4"><%
  String errorString = (String) request.getAttribute("messages");
  if (errorString != null && errorString.trim().equals("timeout")) {
		out.println("Your reset password link has been Expired.");
	}
  %></font>
  <h2 class="module-title">Please Enter Your User Name and Email to Reset the Password:</h2>
   <div class="article module-content">
    <form:form name="emailForm" action="${pageContext.request.contextPath}/portal/processforgotpassword.html" method="post" modelAttribute="password" onsubmit="return validateForm()">
            <div id="frmdtdv">
            	<p><font color="Black"></font></p>
            	<form:label path="userName">User Name</form:label>
                <form:input path="userName" class="text" size="40" autocomplete="off"/>
                <form:label path="emailId">Email Address</form:label>
                <form:input path="emailId" class="text" size="40" autocomplete="off"/>
                <input type="submit" value="Submit" class="button submit" />
                <input type="button" onclick="window.history.back()" value="Back" class="button submit">
            </div>
            
            <div class="clear"></div>
      </form:form>
      </div>
      </div>
      <div align="left"><font color="green" size="4">
						<%
							String message = (String) request.getAttribute("messages");
							if (message != null && message.trim().equals("forgotpassword")) {
								out.println("An Email has been sent to your email id to reset the password.");
							}
						%>
	</font>
							<font color="red" size="4"><%
							errorString = (String) request.getAttribute("messages");
							if (errorString != null && errorString.trim().equals("invalidEmailOrPass")) {
								out.println("Please Enter a Valid Email and User Name.");
							}
							if(errorString != null && errorString.trim().equals("accountLoced")){
								out.println("Your accout is locked and reset link already sent to your registered email id.");
							}
							%></font>
	</div>
  </div>
  <div class="copyrt">Copyright 2015 &copy; IDeas.</div>
  <div class="clear"></div>
</div>
<!-- End #body -->
</body>
</html>