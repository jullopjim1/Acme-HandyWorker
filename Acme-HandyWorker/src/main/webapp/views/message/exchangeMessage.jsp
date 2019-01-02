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

<form:form action="message/actor/exchangeMessage.do"
	modelAttribute="entityMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<form:hidden path="box" />

	<jstl:if test="${isRead==true}">
		<form:label path="sender">
			<spring:message code="message.sender" />:
	</form:label>
		<form:input path="sender" readonly="${isRead}" />
		<form:errors cssClass="error" path="sender" />
		<br />

		<form:label path="receiver">
			<spring:message code="message.receiver" />:
	</form:label>
		<form:input path="receiver" readonly="${isRead}" />
		<form:errors cssClass="error" path="receiver" />
		<br />


		<form:label path="priority">
			<spring:message code="message.priority" />:
	</form:label>
		<form:input path="priority" readonly="${isRead}" />
		<form:errors cssClass="error" path="priority" />
		<br />

		<form:label path="moment">
			<spring:message code="message.moment" />:
	</form:label>
		<form:input path="moment" readonly="${isRead}" />
		<form:errors cssClass="error" path="moment" />
		<br />


		<form:label path="box">
			<spring:message code="message.box" />:
	</form:label>
		<form:input path="box" readonly="${isRead}" />
		<form:errors cssClass="error" path="box" />
		<br />
	</jstl:if>
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:input path="subject" readonly="${isRead}" />
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:input path="body" readonly="${isRead}" />
	<form:errors cssClass="error" path="body" />
	<br />

	<form:label path="tags">
		<spring:message code="message.tags" />:
	</form:label>
	<form:input path="tags" readonly="${isRead}" />
	<form:errors cssClass="error" path="tags" />
	<br />



	<jstl:if test="${isRead!=true}">
		<form:label path="priority">
			<spring:message code="message.priority" />:
	</form:label>
		<form:select id="priorities" path="priority">
			<form:options items="${priorities}" itemValue="priority"
				itemLabel="priority" />
		</form:select>
		<form:errors cssClass="error" path="priority" />


		<form:label path="receiver">
			<spring:message code="message.receiver" />:
	</form:label>
		<form:select id="receivers" path="receiver">
			<form:options items="${receivers}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors cssClass="error" path="receiver" />
		<br />


		<input type="submit" name="save"
			value="<spring:message code="message.save" />" />&nbsp; 
	<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript: relativeRedir('welcome/index.do');" />
		<br />
	</jstl:if>




</form:form>
