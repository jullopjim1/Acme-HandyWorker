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

<form:form action="report/referee/edit.do" modelAttribute="report"
	readonly="${isRead }">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />

	<form:label path="description">
		<spring:message code="report.description" />
	</form:label>
	<form:input path="description" />
	<form:errors ccsClass="error" path="description" />
	<br />

	<form:label path="attachments">
		<spring:message code="report.attachments" />
	</form:label>
	<form:textarea path="attachments" />
	<form:errors ccsClass="error" path="attachments" />
	<br />

	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="report.save" />" />; 
	
		<jstl:if test="${report.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="report.delete" />"
				onclick="javascript: return confirm('<spring:message code="report.confirmDelete" />')" />
		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="report.cancel" />"
			onclick="javascript: relativeRedir('report/referee/list.do');" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" name="cancel"
			value="<spring:message code="report.back" />"
			onclick="javascript: relativeRedir('report/referee/list.do');" />
	</jstl:if>
</form:form>