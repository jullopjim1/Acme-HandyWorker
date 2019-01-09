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

<a href="finder/handy/update.do"> <spring:message
		code="finder.update" />
</a>

<display:table name="fixUpTasks" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="ticker.ticker" titleKey="finder.fix.ticker" />
	
	<display:column property="description"
		titleKey="finder.fix.description" />
	
	<display:column property="adress" titleKey="finder.fix.adress" />

	<display:column property="maxPrice" titleKey="finder.fix.maxPrice" />
	
	<display:column property="moment" titleKey="finder.fix.moment" />
	
	<display:column property="deadline" titleKey="finder.fix.deadline" />

	<display:column titleKey="finder.fix.category" >
		<jstl:forEach var="entry" items="${row.category.name}">	
  				<jstl:if test="${lang==entry.key}">
  					<jstl:out value="${entry.value}" />
  				</jstl:if>
		</jstl:forEach>
	</display:column>
	
	<display:column property="warranty.title" titleKey="finder.fix.warranty" />
	

<%-- 	<display:column>
		<a href="fixUpTasks/handyworker/show.do?fixUpTaskId=${row.id}"> <spring:message
				code="finder.fix.show" />
		</a>
	</display:column> --%>


</display:table>


