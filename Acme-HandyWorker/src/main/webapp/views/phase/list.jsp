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

<display:table name="phases" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	

	
	<display:column property="title" titleKey="phase.title"/>
	<display:column property="description" titleKey="phase.decription"/>
	<display:column property="startMoment" titleKey="phase.startMoment" />
	<display:column property="endMoment" titleKey="phase.endMoment"/>

	<display:column titleKey="phase.fixUpTask">
		<a href="fixUpTask/list.do?phaseId=${phase.id}"> <spring:message
				code="phase.show" />
		</a>
	</display:column>

	<security:authorize access="hasRole('HANDY')">
		<display:column>
			<a href="phase/handyWorker/edit.do?phaseId=${phase.id}"> <spring:message
					code="phase.edit" />
			</a>
		</display:column>
	</security:authorize>




</display:table>
