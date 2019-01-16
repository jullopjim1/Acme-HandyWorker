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




<form:form action="${requestURI}" modelAttribute="handyworker">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="isSuspicious" />
	<form:hidden path="userAccount" />
	<form:hidden path="name"/>
	<form:hidden path="middleName"/>
	<form:hidden path="surname"/>
	<form:hidden path="photo"/>
	<form:hidden path="email"/>
	<form:hidden path="address"/>
	<form:hidden path="phone"/>

	<security:authorize access="hasRole('HANDY')">
		<form:label path="make">
			<spring:message code="actor.make" />
		</form:label>
		<form:input path="make" readonly="${isRead}" />
		<form:errors cssClass="error" path="make" />
		<br />
	</security:authorize>


	<jstl:if test="${isRead == false}">
		<br />
		<input type="submit" name="save"
			value='<spring:message code="actor.save"/>'>


		<input type="button" name="cancel"
			value="<spring:message code="actor.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
		<br />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" name="back"
			value="<spring:message code="actor.back" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
		<br />

	</jstl:if>

</form:form>



