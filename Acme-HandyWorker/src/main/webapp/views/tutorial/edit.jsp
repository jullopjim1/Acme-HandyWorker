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

<form:form action="tutorial/handyworker/edit.do"
	modelAttribute="tutorial" >
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="handyWorker" />


	<form:label path="title">
		<spring:message code="tutorial.title" />
	</form:label>
	<form:input path="title" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="title" />
	<br />

	<form:label path="summary">
		<spring:message code="tutorial.summary" />
	</form:label>
	<form:input path="summary" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="summary" />
	<br />

	<form:label path="pictures">
		<spring:message code="tutorial.pictures" />
	</form:label>
	<form:input path="pictures" readonly="${isRead }"/>
	<form:errors ccsClass="error" path="pictures" />
	<br />

	<form:label path="sponsorship">
		<spring:message code="tutorial.sponsorship" />
	</form:label>
	<form:select id="sponsorships" path="sponsorship" readonly="${isRead }" >
		<form:options items="${sponsorships}" itemLabel="banner"
			itemValue="id" />
		<form:option value="0" label="------" />
	</form:select>
	<br />

	<jstl:if test="${isRead==false }">
		<input type="submit" name="save"
			value="<spring:message code="tutorial.save"/>" />

		<jstl:if test="${tutorial.id != 0}">

			<input type="submit" name="delete"
				value="<spring:message code="tutorial.delete" />"
				onclick="javascript: return confirm('<spring:message code="tutorial.confirmDelete" />')" />

		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="tutorial.cancel" />"
			onclick="javascript: relativeRedir('tutorial/handyworker/list.do?handyWorkerId=${handyWorkerId}');" />

		<br />
	</jstl:if>

	<jstl:if test="${isRead == true }">
		<input type="button" name="cancel"
			value="<spring:message code="tutorial.back" />"
			onclick="javascript: relativeRedir('tutorial/list.do');" />
	</jstl:if>

</form:form>