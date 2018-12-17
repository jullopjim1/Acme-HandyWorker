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


	<display:column property="ticker" title="curriculum.ticker" />

	<!-- DETALLES -->
	<display:column>
		<input type="button" value="curriculum.show"
			onclick="javascript: window.location.href = './curricula/handyWorker/show.do?curriculumId=${row.id}';" />
	</display:column>

	<!-- Realizar vistas de los records -->


	<!-- BORRAR -->
	<jstl:if test="${owner}">
		<display:column>
			<input type="button" value="curriculum.delete"
				onclick="javascript: confirm('<spring:message code="curricumlum.confirm" />');
			window.location.href = './curricula/handyWorker/delete.do?curriculumId=${row.id}';" />
		</display:column>
	</jstl:if>



</display:table>

<!-- CREAR -->
<security:authorize access="hasRole('HANDY')">
	<input type="button" value="curriculum.create"
		onclick="javascript: window.location.href = './curricula/handyWorker/create.do';" />
</security:authorize>