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


	<display:column property="title" titleKey="section.title" />


	<display:column property="text" titleKey="section.text" />


	<display:column property="pictures" titleKey="section.pictures" />

	<display:column property="position" titleKey="section.position"
		sortable="true" />


</display:table>

<a href="section/handyworker/add.do"> <spring:message
		code="section.add" />
</a>
