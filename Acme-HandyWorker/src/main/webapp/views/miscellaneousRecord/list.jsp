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

<display:table name="miscellaneousRecords" id="row"
	requestURI="${requestURI}" pagesize="5" class="displaytag">

	<security:authorize access="hasRole('HANDY')">
		<display:column>
			<a
				href="miscellaneousRecord/handyworker/edit.do?miscellaneousRecordId=${row.id}"><spring:message
					code="miscellaneousRecord.edit" /></a>
		</display:column>
	</security:authorize>

	<display:column property="title" titleKey="miscellaneousRecord.title" />
	<display:column titleKey="miscellaneousRecord.link">
		<a href="${row.link}">${row.link}</a>
	</display:column>
	<display:column property="comments"
		titleKey="miscellaneousRecord.comments" />
	<security:authorize access="hasRole('HANDY')">
		<display:column titleKey="miscellaneousRecord.delete">
			<a
				href="miscellaneousRecord/handyworker/delete.do?miscellaneousRecordId=${row.id}"><spring:message
					code="miscellaneousRecord.delete" /></a>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('HANDY')">
	<div>
		<a href="miscellaneousRecord/handyworker/create.do"> <spring:message
				code="miscellaneousRecord.create" />
		</a>
	</div>
</security:authorize>

<br />
<input type=button name="back"
	value="<spring:message code="miscellaneousRecord.back" />"
	onclick="javascript: relativeRedir('curriculum/handyworker/list.do');" />


