<%@ page language="java" contentType="text/html; charset=ISO-8859-1" session="false"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
<script type="text/javascript">
function validateForm() {
	var x = document.getElementById("airtimeName").value;
    var name=/^[a-zA-Z ]*$/;
    if(!name.test(x)){
    	alert("airlne Name must be alphabets.");
    	document.getElementById("airlineName").focus();
    	return false;
    }
    
    var mobile = document.getElementById("msisdn").value;
    if(mobile.length == 12 || mobile.length == 10){
    	if(mobile.length == 12){
    		if(mobile.charAt(0) != 2 || mobile.charAt(1) !=4 || mobile.charAt(2) !=3 ){
    			alert("Please Enter a Valid Contact Number.");
    	    	document.getElementById("msisdn").focus();
    	        return false;
    		}
    	}
    	if(mobile.length == 10){
    		if(mobile.charAt(0) != 0){
    			alert("Please Enter a Valid Contact Number.");
    	    	document.getElementById("msisdn").focus();
    	        return false;
    		}
    	}
    }
    else{
    	alert("Please Enter a Valid Contact Number.");
    	document.getElementById("msisdn").focus();
        return false;
    }
}
</script>
</head>
<body onload="return currencySelect()">
<!-- Begin #header -->
<div id="header">
  <div><img src="${pageContext.request.contextPath}/img/logo.png"></div>
  <div id="fav_actions">
    <ul id="quicklinks">
      <li id="logout_link" class="icon-link"><a href="${pageContext.request.contextPath}/j_spring_security_logout">Logout</a></li>
    </ul>

  </div>
</div>
<!-- End #header -->
<div id="topnav"><sec:authorize access="hasAnyRole('ROLE_VADMIN','ROLE_ADMIN')"><a href="${pageContext.request.contextPath}/portal/usermanagement.html">User Management</a></sec:authorize><a href="${pageContext.request.contextPath}/portal/customermanagement.html">Customer Management</a><sec:authorize access="hasAnyRole('ROLE_VADMIN','ROLE_ADMIN','ROLE_USER')"><a href="${pageContext.request.contextPath}/portal/transactionmanagement.html">Transaction Management</a></sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_VADMIN')"><a href="${pageContext.request.contextPath}/portal/airlinemanagement.html" class="active">airline Management</a></sec:authorize> </div>
<!-- Begin #body -->
<div id="body">
  <div id="content">
    <div class="module w3">
    	<div align="center"><font color="green" size="4">
						
		</font></div>
      <div class="module-inner">
        <h2 class="module-title">airline Registration</h2>
        <div class="article module-content">
          <form:form action="${pageContext.request.contextPath}/portal/processregisterairline.html" modelAttribute="airline"  method="post" name="registerForm" onsubmit="return validateForm()">
            <div id="frmdtdv">
              <div>
                <form:label path="airlineName">airline Name:</form:label>
                <form:input path="airlineName" id="airlineName" class="text" size="30" maxlength='30' required="required"/>
              </div>
              <div>
                <form:label path="airlineCode">airline Code:</form:label>
                <form:input path="airlineCode" id="airlineCode" class="text" size="30" maxlength='30' required="required"/>
              </div>
              <div>
                <form:label path="msisdn">airline MSISDN:</form:label>
                <form:input path="msisdn" id="msisdn" class="text" size="30" maxlength='12' onkeypress="return isNumberKey(event)" required="required"/>
              </div>
              <div>
                <form:label path="address">Address:</form:label>
                <form:input path="address" id="address" class="text" size="30" maxlength='30' required="required"/>
              </div>
              <div>
                <form:label path="contactNumber">Contact Number:</form:label>
                <form:input path="contactNumber" id="contactNumber" class="text" size="30" maxlength='12' onkeypress="return isNumberKey(event)" required="required"/>
              </div>
			  <br/>
              <div style="margin-top:4px;">
                <input type="submit" value="Register airline" class="button submit" />
				<input type="reset" value="Reset" class="button submit" />
              </div>
            </div>
            <div class="clear"></div>
          </form:form>
        </div>
      </div>
    </div>
  </div>
  <div class="copyrt">Copyright 2010 &copy; IDEAS Republique Democratique du Congo.</div>
  <div class="clear"></div>
</div>
<!-- End #body -->

</body>
</html>