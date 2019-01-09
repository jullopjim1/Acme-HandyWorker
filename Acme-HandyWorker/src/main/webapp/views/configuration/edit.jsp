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

<form:form action="${requestURI}" modelAttribute="configurationForm">


	<jstl:if test="${isRead==true}">
		<img src="${configurationForm.banner}" height="250px" width="350px" />
		<br />
	</jstl:if>

	<form:label path="varTax">
		<spring:message code="configuration.varTax" />
	</form:label>
	<form:input path="varTax" readonly="${isRead}" />
	<form:errors cssClass="error" path="varTax" />
	<br />

	<form:label path="countryCode">
		<spring:message code="configuration.countryCode" />
	</form:label>
	<form:input path="countryCode" readonly="${isRead}" />
	<form:errors cssClass="error" path="countryCode" />
	<br />

	<jstl:if test="${isRead==false}">
		<form:label path="banner">
			<spring:message code="configuration.banner" />
		</form:label>
		<form:input path="banner" />
		<form:errors cssClass="error" path="banner" />
		<br />
	</jstl:if>
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

	<form:label path="welcomeMessageES">
		<spring:message code="configuration.welcomeMessageES" />
	</form:label>
	<form:input path="welcomeMessageES" readonly="${isRead}" />
	<form:errors cssClass="error" path="welcomeMessageES" />
	<br />

	<form:label path="welcomeMessageEN">
		<spring:message code="configuration.welcomeMessageEN" />
	</form:label>
	<form:input path="welcomeMessageEN" readonly="${isRead}" />
	<form:errors cssClass="error" path="welcomeMessageEN" />
	<br />

	<form:label path="spamWordsES">
		<spring:message code="configuration.spamES" />
	</form:label>
	<form:input path="spamWordsES" readonly="${isRead}" />
	<form:errors cssClass="error" path="spamWordsES" />
	<br />

	<form:label path="spamWordsEN">
		<spring:message code="configuration.spamEN" />
	</form:label>
	<form:input path="spamWordsEN" readonly="${isRead}" />
	<form:errors cssClass="error" path="spamWordsEN" />
	<br />

	<form:label path="negativeWordsES">
		<spring:message code="configuration.negativeES" />
	</form:label>
	<form:input path="negativeWordsES" readonly="${isRead}" />
	<form:errors cssClass="error" path="negativeWordsES" />
	<br />

	<form:label path="negativeWordsEN">
		<spring:message code="configuration.negativeEN" />
	</form:label>
	<form:input path="negativeWordsEN" readonly="${isRead}" />
	<form:errors cssClass="error" path="negativeWordsEN" />
	<br />

	<form:label path="positiveWordsES">
		<spring:message code="configuration.positiveES" />
	</form:label>
	<form:input path="positiveWordsES" readonly="${isRead}" />
	<form:errors cssClass="error" path="positiveWordsES" />
	<br />

	<form:label path="positiveWordsEN">
		<spring:message code="configuration.positiveEN" />
	</form:label>
	<form:input path="positiveWordsEN" readonly="${isRead}" />
	<form:errors cssClass="error" path="positiveWordsEN" />
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
