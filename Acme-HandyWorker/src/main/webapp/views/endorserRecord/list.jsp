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

<display:table name="endorserRecords" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('HANDY')">
		<display:column>
			<a
				href="endorserRecord/handyworker/edit.do?endorserRecordId=${row.id}"><spring:message
					code="endorserRecord.edit" /></a>
		</display:column>
	</security:authorize>

	<display:column property="fullName" titleKey="endorserRecord.fullName" />
	<display:column property="email" titleKey="endorserRecord.email" />
	<display:column property="phone" titleKey="endorserRecord.phone" />
	<display:column titleKey="endorserRecord.link">
		<a href="${row.link}">${row.link}</a>
	</display:column>
	<display:column property="comments" titleKey="endorserRecord.comment" />
	<security:authorize access="hasRole('HANDY')">
		<display:column titleKey="endorserRecord.delete">
			<a
				href="endorserRecord/handyworker/delete.do?endorserRecordId=${row.id}"><spring:message
					code="endorserRecord.delete" /></a>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('HANDY')">
	<div>
		<a
			href="endorserRecord/handyworker/create.do?curriculumId=${row.curriculum.id}">
			<spring:message code="endorserRecord.create" />
		</a>
	</div>
</security:authorize>

<br />
<input type=button name="back"
	value="<spring:message code="endorserRecord.back" />"
	onclick="javascript: relativeRedir('curriculum/handyworker/list.do');" />


