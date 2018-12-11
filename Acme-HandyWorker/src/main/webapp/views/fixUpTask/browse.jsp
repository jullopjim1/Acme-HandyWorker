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

<security:authorize access="hasRole('HANDY')">
	<display:table name="fixUpTasks" id="row" requestURI="${requestURI}"
		pagesize="5" class="displaytag">
		<display:column property="ticker" titleKey="fixUpTask.ticker" />
		<display:column property="moment" titleKey="fixUpTask.moment" />
		<display:column property="description"
			titleKey="fixUpTask.description" />
		<display:column property="address" titleKey="fixUpTask.address" />
		<display:column property="maxPrice" titleKey="fixUpTask.maxPrice" />
		<display:column property="deadline" titleKey="fixUpTask.deadline" />
		<display:column>
			<a href="profile/handyWorker/profile.do?customerId=${customer.id}">
				<spring:message code="fixUpTask.profile" />
			</a>
		</display:column>
		<display:column>
			<a href="category/list.do?categoryId=${category.id}"> <spring:message
					code="fixUpTask.category" />
			</a>
		</display:column>
	</display:table>

	<input type="button" name="inicio"
		value="<spring:message code="message.inicio" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />

	<br />
</security:authorize>