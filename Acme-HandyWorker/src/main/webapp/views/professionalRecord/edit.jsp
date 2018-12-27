<%--
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="professionalRecord/handyworker/edit.do"
	modelAttribute="professionalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculum" />

	<form:label path="name">
		<spring:message code="professionalRecord.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="startMoment">
		<spring:message code="professionalRecord.startMoment" />:
	</form:label>
	<form:input path="startMoment" />
	<form:errors cssClass="error" path="startMoment" />
	<br />

	<form:label path="endMoment">
		<spring:message code="professionalRecord.endMoment" />:
	</form:label>
	<form:input path="endMoment" />
	<form:errors cssClass="error" path="endMoment" />
	dd/mm/yyyy
	<br />

	<form:label path="role">
		<spring:message code="professionalRecord.role" />:
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role" />
	<br />

	<form:label path="link">
		<spring:message code="professionalRecord.link" />:
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link" />
	<br />

	<form:label path="comments">
		<spring:message code="professionalRecord.comments" />:
	</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel" />"
		onclick="javascript: relativeRedir('professionalRecord/handyworker/list.do');" />
	<br />



</form:form>

