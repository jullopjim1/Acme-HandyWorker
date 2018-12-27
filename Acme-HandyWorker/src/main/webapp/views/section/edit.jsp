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

<form:form action="section/handyworker/edit.do" modelAttribute="section">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tutorial" />



	<form:label path="title">
		<spring:message code="section.title" />
	</form:label>
	<form:input path="title" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="title" />
	<br />

	<form:label path="text">
		<spring:message code="section.text" />
	</form:label>
	<form:input path="text" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="text" />
	<br />

	<form:label path="pictures">
		<spring:message code="section.pictures" />
	</form:label>
	<form:input path="pictures" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="pictures" />
	<br />

	<form:label path="position">
		<spring:message code="section.position" />
	</form:label>
	<form:input path="position" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="position" />
	<br />

	<jstl:if test="${isRead==false }">
		<input type="submit" name="save"
			value="<spring:message code="section.save"/>" />

		<jstl:if test="${section.id != 0}">

			<input type="submit" name="delete"
				value="<spring:message code="section.delete" />"
				onclick="javascript: return confirm('<spring:message code="section.confirmDelete" />')" />

		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="section.cancel" />"
			onclick="javascript: relativeRedir('section/list.do');" />

		<br />
	</jstl:if>

	<jstl:if test="${isRead == true }">
		<input type="button" name="cancel"
			value="<spring:message code="section.back" />"
			onclick="javascript: relativeRedir('section/handyworker/list.do');" />
	</jstl:if>

</form:form>