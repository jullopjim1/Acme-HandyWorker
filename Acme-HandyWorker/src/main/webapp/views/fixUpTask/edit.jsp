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

<security:authorize
	access="hasRole('CUSTOMER')">
	<form:form action="fixUpTask/customer/edit.do"
		modelAttribute="fixUpTask">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="ticker" />
		<form:hidden path="moment" />


		<form:label path="description">
			<spring:message code="fixuptask.description" />:
	</form:label>
		<form:input path="description" />
		<form:errors cssClass="error" path="description" />
		<br />

		<form:label path="adress">
			<spring:message code="fixuptask.adress" />:
	</form:label>
		<form:input path="adress" />
		<form:errors cssClass="error" path="adress" />
		<br />

		<form:label path="maxPrice">
			<spring:message code="fixuptask.maxPrice" />:
	</form:label>
		<form:input path="maxPrice" />
		<form:errors cssClass="error" path="maxPrice" />
		<br />

		<form:label path="deadline">
			<spring:message code="fixuptask.deadline" />:
	</form:label>
		<form:input path="deadline" />
		<form:errors cssClass="error" path="deadline" />
		<br />

		<form:label path="category">
			<spring:message code="fixuptask.category" />:
	</form:label>
		<form:select id="categories" path="category">
			<form:options items="${categories}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors cssClass="error" path="category" />

		<form:label path="warranty">
			<spring:message code="fixuptask.warranty" />:
	</form:label>
		<form:select id="warranties" path="warranty">
			<form:options items="${warranties}" itemValue="id" itemLabel="title" />
		</form:select>
		<form:errors cssClass="error" path="warranty" />
		<br />

		<input type="submit" name="save"
			value="<spring:message code="fixuptask.save" />" />&nbsp;
	
	
	<input type="submit" name="delete"
			value="<spring:message code="fixuptask.delete"/>" />


		<input type="button" name="cancel"
			value="<spring:message code="fixuptask.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
		<br />
	</form:form>
</security:authorize>