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


<display:table name="applications" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('HANDY')">
		<display:column>
			<jstl:if test="${handyWorkerId==row.handyWorker.id}">
				<a href="application/handyworker/edit.do?applicationId=${row.id}">
					<spring:message code="tutorial.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="application/customer/edit.do?applicationId=${row.id}"> <spring:message
					code="tutorial.edit" />
			</a>
		</display:column>
	</security:authorize>

	<display:column property="moment" titleKey="application.moment" />


	<display:column property="price" titleKey="application.price" />


	<display:column property="comments" titleKey="application.comments" />

	<display:column property="status" titleKey="application.status" />


</display:table>

<security:authorize access="hasRole('HANDY')">
	<a href="application/handyworker/create.do"> <spring:message
			code="tutorial.create" />
	</a>
</security:authorize>
