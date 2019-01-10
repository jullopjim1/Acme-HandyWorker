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

<form:form action="${requestURI}" modelAttribute="phase">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="fixUpTask"/>

	<form:label path="title">
		<spring:message code="phase.title" />
	</form:label>
	<form:input path="title" readonly="${isRead }" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="description">
		<spring:message code="phase.description" />
	</form:label>
	<form:input path="description" readonly="${isRead }" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="startMoment">
		<spring:message code="phase.start" />
	</form:label>
	<form:input path="startMoment" placeholder="yyyy/mm/dd 00:00"
		readonly="${isRead }" />
	<form:errors cssClass="error" path="startMoment" />
	<br />

	<form:label path="endMoment">
		<spring:message code="phase.end" />
	</form:label>
	<form:input path="endMoment" placeholder="yyyy/mm/dd 00:00"
		readonly="${isRead }" />
	<form:errors cssClass="error" path="endMoment" />
	<br />
	<br/>
	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="phase.save" />" />
	
		<jstl:if test="${phase.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="phase.delete" />"
				onclick="javascript: return confirm('<spring:message code="phase.confirmDelete" />')" />
		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="phase.cancel" />"
			onclick="javascript: relativeRedir('phase/handyworker/list.do?fixUpTaskId=${phase.fixUpTask.id}');" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" name="cancel"
			value="<spring:message code="phase.back" />"
			onclick="javascript: relativeRedir('phase/handyworker/list.do?fixUpTaskId=${phase.fixUpTask.id}');" />
	</jstl:if>
</form:form>
