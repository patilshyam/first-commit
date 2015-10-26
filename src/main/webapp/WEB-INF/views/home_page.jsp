<%@ page language="java" contentType="text/html; charset=ISO-8859-1" session="false" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
<!-- Begin #header -->
<div id="header">
  <div><img src="${pageContext.request.contextPath}/img/logo.png"></div>
<!-- End #header -->
<div id="topnav"> <sec:authorize><a href="${pageContext.request.contextPath}/portal/flightBookingManagement.html">Flight Booking Management</a></sec:authorize><a href="${pageContext.request.contextPath}/portal/flightBookingManagement.jsp">Customer Management</a><sec:authorize access="hasAnyRole('ROLE_VADMIN','ROLE_ADMIN','ROLE_USER')"><a href="${pageContext.request.contextPath}/portal/transactionmanagement.html">Transaction Management</a></sec:authorize><sec:authorize access="hasAnyRole('ROLE_VADMIN')"><a href="${pageContext.request.contextPath}/portal/bankmanagement.html">Bank Management</a></sec:authorize> </div>
<!-- Begin #body -->
<div id="body">
  <div id="content">
   <div align="center"><h4>Welcome Home</h4>
   </div>
  </div>
<div class="clear"></div>
</div>
<!-- End #body -->
</body>
</html>