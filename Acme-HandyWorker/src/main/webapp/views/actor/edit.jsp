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




<form:form action="${requestURI}" modelAttribute="actor">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSuspicious" />
	<form:hidden path="userAccount" />

	<form:label path="name">
		<spring:message code="actor.name" />
	</form:label>
	<form:input path="name" readonly="${isRead}" />
	<form:errors ccsClass="error" path="name" />
	<br />

	<form:label path="middleName">
		<spring:message code="actor.middleName" />
	</form:label>
	<form:input path="middleName" readonly="${isRead}" />
	<form:errors ccsClass="error" path="middleName" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />
	</form:label>
	<form:input path="surname" readonly="${isRead}" />
	<form:errors ccsClass="error" path="surname" />
	<br />

	<form:label path="photo">
		<spring:message code="actor.photo" />
	</form:label>
	<form:input path="photo" readonly="${isRead}" />
	<form:errors ccsClass="error" path="photo" />
	<br />

	<form:label path="email">
		<spring:message code="actor.email" />
	</form:label>
	<form:input path="email" readonly="${isRead}" />
	<form:errors ccsClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>
	<form:input path="phone" readonly="${isRead}" />
	<form:errors ccsClass="error" path="phone" />
	<br />

	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" readonly="${isRead}" />
	<form:errors ccsClass="error" path="address" />
	<br />


	<h3>${score}</h3>

	<a href="tutorial/view.do?handyWorkerId=${handyWorkerId}"> <spring:message
			code="profile.viewTutorials" />
	</a>

	<br />
	<br />


	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="actor.save" />" />

		<input type="button" name="cancel"
			value="<spring:message code="fixuptask.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
		<br />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" name="back"
			value="<spring:message code="actor.back" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
		<br />

	</jstl:if>

</form:form>



