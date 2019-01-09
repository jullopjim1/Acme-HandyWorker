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

<display:table name="educationRecords" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('HANDY')">
		<display:column>
			<a
				href="educationRecord/handyworker/edit.do?educationRecordId=${row.id}"><spring:message
					code="educationRecord.edit" /></a>
		</display:column>
	</security:authorize>

	<display:column property="title" titleKey="educationRecord.title" />
	<display:column property="startMoment"
		titleKey="educationRecord.startMoment" />
	<display:column property="endMoment"
		titleKey="educationRecord.endMoment" />
	<display:column property="institution"
		titleKey="educationRecord.institution" />
	<display:column titleKey="educationRecord.link">
		<a href="${row.link}">${row.link}</a>
	</display:column>
	<display:column property="comments" titleKey="educationRecord.comments" />
	<security:authorize access="hasRole('HANDY')">
		<display:column titleKey="educationRecord.delete">
			<a
				href="educationRecord/handyworker/delete.do?educationRecordId=${row.id}"><spring:message
					code="educationRecord.delete" /></a>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('HANDY')">
	<div>
		<a href="educationRecord/handyworker/create.do"> <spring:message
				code="educationRecord.create" />
		</a>
	</div>
</security:authorize>

<br />
<input type=button name="back"
	value="<spring:message code="educationRecord.back" />"
	onclick="javascript: relativeRedir('curriculum/handyworker/list.do');" />


