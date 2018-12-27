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

<form:form action="professionalRecord/ranger/edit.do" modelAttribute="professionalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="companyName">
		<spring:message code="professionalRecord.companyName" />:
	</form:label>
	<form:input path="companyName" />
	<form:errors cssClass="error" path="companyName" />
	<br />
	
	<form:label path="startYear">
		<spring:message code="professionalRecord.startYear" />:
	</form:label>
	<form:input path="startYear" />
	<form:errors cssClass="error" path="startYear" />
	dd/mm/yyyy
	<br />
	
	<form:label path="finishYear">
		<spring:message code="professionalRecord.finishYear" />:
	</form:label>
	<form:input path="finishYear" />
	<form:errors cssClass="error" path="finishYear" />
	dd/mm/yyyy
	<br />

	<form:label path="role">
		<spring:message code="professionalRecord.role" />:
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role" />
	<br />
	
	<form:label path="attachment">
		<spring:message code="professionalRecord.attachment" />:
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />
	
	<form:label path="comment">
		<spring:message code="professionalRecord.comment" />:
	</form:label>
	<form:input path="comment" />
	<form:errors cssClass="error" path="comment" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />

	<input type="button" name="cancel"
		value="<spring:message code="professionalRecord.cancel" />"
		onclick="javascript: relativeRedir('professionalRecord/ranger/list.do');" />
	<br />



</form:form>

