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

<security:authorize access="isAuthenticated()">
	<security:authentication property="principal.username" />
</security:authorize>
<form:form action="sponsorship/edit.do" modelAttribute="sponsorship"
	readonly="${isRead}">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="banner">
		<spring:message code="sponsorship.banner" />
	</form:label>
	<form:input path="banner" />
	<form:errors ccsClass="error" path="banner" />
	<br />

	<form:label path="link">
		<spring:message code="sponsorship.banner" />
	</form:label>
	<form:input path="banner" />
	<form:errors ccsClass="error" path="banner" />
	<br />


	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="sponsorship.save" />" />
			
		<jstl:if test="${sponsorship.id != 0}">

			<input type="submit" name="delete"
				value="<spring:message code="sponsorship.delete" />"
				onclick="javascript: return confirm('<spring:message code="sponsorship.confirmDelete" />')" />

		</jstl:if>
		

		<input type="button" name="cancel"
			value="<spring:message code="sponsorship.cancel" />"
			onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');" />
		<br />
		
		
	</jstl:if>

</form:form>