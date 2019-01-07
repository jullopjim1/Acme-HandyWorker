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

<form:form action="${requestURI}" modelAttribute="complaint">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="fixUpTask" />
	<form:hidden path="customer" />


	<form:label path="description">
		<spring:message code="complaint.description" />
	</form:label>
	<form:input path="description" readonly="${isRead}" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="attachments">
		<spring:message code="complaint.attachments" />
	</form:label>
	<form:textarea path="attachments" readonly="${isRead}" />
	<form:errors cssClass="error" path="attachments" />
	<br />
	<br />
	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="complaint.save" />" />

		<jstl:if test="${complaint.id != 0}">
			<input type="submit" name="delete"
				value="<spring:message code="complaint.delete" />"
				onclick="javascript: return confirm('<spring:message code="complaint.confirmDelete" />')" />
		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="complaint.cancel" />"
			onclick="javascript: relativeRedir('complaint/customer/list.do?customerId=${customerId}');" />
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" name="cancel"
			value="<spring:message code="complaint.back" />"
			onclick="javascript: relativeRedir('complaint/list.do');" />
	</jstl:if>
</form:form>
