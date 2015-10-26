<%@ page language="java" contentType="text/html; charset=ISO-8859-1" session="false" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>Flight Booking </title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<!-- Begin #header -->
<div id="header">
  <div><img src="${pageContext.request.contextPath}/img/logo.png"></div>
  <div id="fav_actions">
    <ul id="quicklinks">
      <li id="logout_link" class="icon-link"><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
    </ul>>
  </div>
</div>
<!-- End #header -->
<!-- End #body -->

</body>
</html>