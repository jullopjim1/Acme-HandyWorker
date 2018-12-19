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

<form:form action="note/edit.do" modelAttribute="note">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />


	<security:authorize access="hasRole('REFEREE')">
	<form:label path="mandatoryCommentReferee">
		<spring:message code="note.comment" />
	</form:label>
	<form:input path="mandatoryCommentReferee" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="mandatoryCommentReferee" />
	<br />
	</security:authorize>

	<security:authorize access="hasRole('CUSTOMER')">
	<form:label path="mandatoryCommentCustomer">
		<spring:message code="note.comment" />
	</form:label>
	<form:input path="mandatoryCommentCustomer" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="mandatoryCommentCustomer" />
	<br />
	</security:authorize>
	
	<security:authorize access="hasRole('HANDY')">
	<form:label path="mandatoryCommentHandyWorker">
		<spring:message code="note.comment" />
	</form:label>
	<form:input path="mandatoryCommentHandyWorker" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="mandatoryCommentHandyWorker" />
	<br />
	</security:authorize>


	<jstl:if test="${isRead==false }">
		<input type="submit" name="save"
			value="<spring:message code="note.save"/>" />
		
		<security:authorize access="hasRole('REFEREE')">
		<jstl:if test="${note.id != 0}">

			<input type="submit" name="delete"
				value="<spring:message code="note.delete" />"
				onclick="javascript: return confirm('<spring:message code="note.confirmDelete" />')" />

		</jstl:if>
		</security:authorize>
		
		<input type="button" name="cancel"
			value="<spring:message code="note.cancel" />"
			onclick="javascript: relativeRedir('note/list.do');" />

		<br />
	</jstl:if>

	<jstl:if test="${isRead == true }">
		<input type="button" name="cancel"
			value="<spring:message code="note.back" />"
			onclick="javascript: relativeRedir('note/list.do');" />
	</jstl:if>

</form:form>