<%--
 * action-2.jsp
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



<display:table name="curriculum" id="row" requestURI="${requestURI}"
	pagesize="4" class="displaytag">


	<display:column property="ticker" titleKey="curriculum.ticker" />


	<!-- DETALLES -->
	<display:column titleKey="curriculum.personalRecord">
		<spring:url value="/personalRecord/handyworker/show.do" var="editURL">
			<spring:param name="curriculumId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="curriculum.show" /> </a>
	</display:column>

	<!-- Realizar vistas de los records -->

	<display:column titleKey="curriculum.educationRecords">
		<spring:url value="/educationRecord/handyworker/list.do" var="editURL">
			<spring:param name="curriculumId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="curriculum.show" /> </a>
		<br />
	</display:column>

	<display:column titleKey="curriculum.endorserRecords">
		<spring:url value="/endorserRecord/handyworker/list.do" var="editURL">
			<spring:param name="curriculumId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="curriculum.show" /> </a>
		<br />
	</display:column>

	<display:column titleKey="curriculum.professionalRecords">
		<spring:url value="/professionalRecord/handyworker/list.do"
			var="editURL">
			<spring:param name="curriculumId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="curriculum.show" /> </a>
		<br />
	</display:column>

	<display:column titleKey="curriculum.miscellaneousRecords">
		<spring:url value="/miscellaneousRecord/handyworker/list.do"
			var="editURL">
			<spring:param name="curriculumId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}"><spring:message code="curriculum.show" /> </a>
		<br />
	</display:column>

	<!-- BORRAR -->
	<jstl:if test="${owner}">
		<display:column>
			<input type="button" value="value="
				<spring:message code="curriculum.delete"/>
				onclick="javascript: confirm('<spring:message code="curricumlum.confirm" />');
			window.location.href = './curricula/handyWorker/delete.do?curriculumId=${row.id}';" />
		</display:column>
	</jstl:if>



</display:table>

<!-- CREAR -->
<security:authorize access="hasRole('HANDY')">
	<input type="button" value="<spring:message code="curriculum.create"/>"
		onclick="javascript: window.location.href = ' ./curricula/handyWorker/create.do';" />
</security:authorize>