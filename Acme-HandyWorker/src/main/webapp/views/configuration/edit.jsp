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

<form:form action="${requestURI}" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<form:label path="varTax">
		<spring:message code="configuration.varTax" />
	</form:label>
	<form:input path="varTax" readonly="${isRead}" />
	<form:errors cssClass="error" path="varTax" />
	<br />

	<form:label path="banner">
		<spring:message code="configuration.banner" />
	</form:label>
	<form:input path="banner" readonly="${isRead}" />
	<form:errors cssClass="error" path="banner" />
	<br />

	<form:label path="finderCacheTime">
		<spring:message code="configuration.cache" />
	</form:label>
	<form:input path="finderCacheTime" readonly="${isRead}" />
	<form:errors cssClass="error" path="finderCacheTime" />
	<br />

	<form:label path="finderMaxResults">
		<spring:message code="configuration.maxResults" />
	</form:label>
	<form:input path="finderMaxResults" readonly="${isRead}" />
	<form:errors cssClass="error" path="finderMaxResults" />
	<br />

	<form:label path="welcomeMessage">
		<spring:message code="configuration.welcomeMessage" />
	</form:label>
	<form:input path="welcomeMessage" readonly="${isRead}" />
	<form:errors cssClass="error" path="welcomeMessage" />
	<br />

	<form:label path="spamWords">
		<spring:message code="configuration.spam" />
	</form:label>
	<form:input path="spamWords" readonly="${isRead}" />
	<form:errors cssClass="error" path="spamWords" />
	<br />

	<form:label path="negativeWords">
		<spring:message code="configuration.negative" />
	</form:label>
	<form:input path="negativeWords" readonly="${isRead}" />
	<form:errors cssClass="error" path="negativeWords" />
	<br />

	<form:label path="positiveWords">
		<spring:message code="configuration.positive" />
	</form:label>
	<form:input path="positiveWords" readonly="${isRead}" />
	<form:errors cssClass="error" path="positiveWords" />
	<br />


	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="configuration.save" />" />

		<input type="button" name="cancel"
			value="<spring:message code="configuration.cancel" />"
			onclick="javascript: relativeRedir('configuration/administrator/list.do');" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" name="cancel"
			value="<spring:message code="complaint.back" />"
			onclick="javascript: relativeRedir('configuration/administrator/list.do');" />
	</jstl:if>
</form:form>
