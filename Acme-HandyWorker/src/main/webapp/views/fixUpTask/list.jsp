<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

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


<spring:message code="fixuptask.keyWord" />
<input type="text" id="textSearch" value="">
<br />
<spring:message code="fixuptask.category" />
<select id="category">
	<form:options items="${categories}" itemValue="id" itemLabel="name" />
</select>
<spring:message code="fixuptask.warranty" />
<form:select id="warranties" path="warranty">
	<form:options items="${warranties}" itemValue="id" itemLabel="title" />
</form:select>
<spring:message code="fixuptask.minPrice" />
<input type="number" id="minPrice" value="" min="0" max="999999">
<spring:message code="fixuptask.maxPrice" />
<input type="number" id="maxPrice" value="" min="0" max="999999">
<br />
<spring:message code="fixuptask.startDate" />
<input type="date" id="dateStart" value="">
<spring:message code="fixuptask.endDate" />
<input type="date" id="dateEnd" value="">
<br />

<input type="button" id="buttonSearch"
	value="<spring:message code="fixuptask.search"/>" />

<script type="text/javascript">
	$(document).ready(function() {
		$("#buttonSearch").click(function() {
			if ($("#textSearch").val() != "")
				window.location.replace('fixuptask/list.do?search=' + $("#textSearch").val());
		});
		$("#textSearch").on('keyup', function(e) {
			if (e.keyCode === 13 && $("#textSearch").val() != "")
				window.location.replace('fixuptask/list.do?search=' + $("#textSearch").val());
			e.preventDefault();
		});
	});
</script>


<display:table name="fixUpTasks" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column title="${customerHeader}" sortable="true">
		<spring:url value="/actor/details.do" var="editURL">
			<spring:param name="customerId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}">${row.customer}</a>
		<br />
	</display:column>

	<display:column title="${categoryHeader}" sortable="true">
		<spring:url value="/category/list.do" var="editURL">
			<spring:param name="categoryId" value="${row.id}" />
		</spring:url>
		<a href="${editURL}">${row.category}</a>
		<br />
	</display:column>
	<display:column property="ticker" titleKey="fixuptask.ticker" />
	<display:column property="moment" titleKey="fixuptask.moment" />
	<display:column property="maxPrice" titleKey="fixuptask.price" />
	<display:column property="description" titleKey="fixuptask.description" />
	<display:column property="address" titleKey="fixuptask.address" />
	<display:column property="deadline" titleKey="fixuptask.deadline" />
	<display:column property="warraty" titleKey="fixuptask.warranty" />
</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixuptask/customer/create.do"> <spring:message
			code="application.create" />
	</a>
</security:authorize>