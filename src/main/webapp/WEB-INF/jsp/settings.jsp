<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@include file="header.jsp"%>

<div class="contentArea">
	<h1>Settings</h1>
	
	<div class="contentAreaInnerContent">
	<form action="/settings" method="POST">
		<button class="btn btn-primary" type="submit">import files</button>
	</form>
	</div>
</div>

<%@include file="footer.jsp"%>
