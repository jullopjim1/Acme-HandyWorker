<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="box/actor/edit.do" modelAttribute="box">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="subboxes" />
	<form:hidden path="actor" />
	


	<form:label path="name">
		<spring:message code="box.name" />:
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<jstl:choose>
		<jstl:when test="${box.id == 0}">
			<form:label path="rootbox">
				<spring:message code="box.rootbox" />:
			</form:label>
			<form:select id="boxes" path="rootbox">
				<form:options items="${boxes}" itemValue="id" itemLabel="name" />
			</form:select>
			<form:errors cssClass="error" path="rootbox" /><br/>
		</jstl:when>
		<jstl:otherwise>
				<form:hidden path="rootbox" />
				<spring:message code="box.rootbox" />:
				<jstl:out value="${box.rootbox.name}"/>
				<br/>
		</jstl:otherwise>
	</jstl:choose>


		<input type="submit" name="save"
			value="<spring:message code="box.save" />" />&nbsp; 
	
	<jstl:if test="${box.id != 0}">

			<input type="submit" name="delete"
				value="<spring:message code="box.delete" />"
				onclick="javascript: return confirm('<spring:message code="box.confirm.delete" />')" />

		</jstl:if>

		<input type="button" name="cancel"
			value="<spring:message code="box.cancel" />"
			onclick="javascript: relativeRedir('box/actor/list.do');" />
		<br />
</form:form>
