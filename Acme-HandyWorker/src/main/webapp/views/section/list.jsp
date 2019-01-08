<%--
 * list.jsp
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


<display:table name="sections" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<security:authorize access="hasRole('HANDY')">
		<jstl:if test="${handyWorkerId==row.tutorial.handyWorker.id}">
			<display:column>
				<a href="section/handyworker/edit.do?sectionId=${row.id}"> <spring:message
						code="section.edit" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>

	<display:column property="title" titleKey="section.title" />


	<display:column property="text" titleKey="section.text" />


	<display:column titleKey="section.pictures">
		<img src="${row.pictures}" height="100px" width="100px" />
	</display:column>

	<display:column property="position" titleKey="section.position"
		sortable="true" />


</display:table>
<br />

<input type="button" name="cancel"
	value="<spring:message code="section.back" />"
	onclick="javascript: relativeRedir('tutorial/list.do');" />



