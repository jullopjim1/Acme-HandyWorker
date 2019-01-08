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

<form:form action="educationRecord/handyworker/edit.do"
	modelAttribute="educationRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculum" />

	<form:label path="title">
		<spring:message code="educationRecord.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="startMoment">
		<spring:message code="educationRecord.startMoment" />:
	</form:label>
	<form:input path="startMoment" />
	<form:errors cssClass="error" path="startMoment" />
	dd/mm/yyyy
	<br />

	<form:label path="endMoment">
		<spring:message code="educationRecord.endMoment" />:
	</form:label>
	<form:input path="endMoment" />
	<form:errors cssClass="error" path="endMoment" />
	dd/mm/yyyy
	<br />

	<form:label path="institution">
		<spring:message code="educationRecord.institution" />:
	</form:label>
	<form:input path="institution" />
	<form:errors cssClass="error" path="institution" />
	<br />
	
	<form:label path="link">
		<spring:message code="educationRecord.link" />:
	</form:label>
	<form:input path="link" />
	<form:errors cssClass="error" path="link" />
	<br />

	<form:label path="comments">
		<spring:message code="educationRecord.comments" />:
	</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="educationRecord.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="educationRecord.cancel" />"
		onclick="javascript: relativeRedir('educationRecord/handyworker/list.do?curriculumId=${curriculumId}');" />
	<br />



</form:form>

