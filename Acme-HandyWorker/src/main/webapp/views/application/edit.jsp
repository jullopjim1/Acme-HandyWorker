<%--
 * edit.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>


<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />


	<form:label path="price">
		<spring:message code="application.price" />
	</form:label>
	<form:input path="price" />
	<form:errors ccsClass="error" path="price" />
	<br />

	<form:label path="comments">
		<spring:message code="application.comments" />
	</form:label>
	<form:input path="comments" />
	<form:errors ccsClass="error" path="comments" />
	<br />

	<form:label path="status">
		<spring:message code="tutorial.pictures" />
	</form:label>
	<form:input path="pictures" />
	<form:errors ccsClass="error" path="pictures" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="tutorial.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="tutorial.cancel" />"
		onclick="javascript: relativeRedir('tutorial/handyworker/list.do');" />

	<br />


</form:form>