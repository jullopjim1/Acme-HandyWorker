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

<form:form action="message/actor/edit.do" modelAttribute="entityMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="sender" />
	<jstl:if test="${isRead==true}">
		<spring:message code="message.sender" />:
		<jstl:out value="${entityMessage.sender.userAccount.username}"></jstl:out>
		<br />

		<form:hidden path="recipient" />
		<spring:message code="message.receiver" />:
		<jstl:out value="${entityMessage.recipient.userAccount.username}"></jstl:out>
		<br />
		<jstl:if test="${isMove==false }">
			<form:hidden path="box" />
			<spring:message code="message.box" />:
			<jstl:out value="${entityMessage.box.name}"></jstl:out>
			<br />
		</jstl:if>
		<jstl:if test="${isMove==true }">
			<form:label path="box">
				<spring:message code="message.box" />:
			</form:label>
			<form:select id="boxes" path="box">
				<form:options items="${boxes}" itemValue="id" itemLabel="name" />
			</form:select>
			<form:errors cssClass="error" path="box" />
			<br />
		</jstl:if>
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


	</jstl:if>
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<form:textarea path="subject" readonly="${isRead}" />
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<form:textarea path="body" readonly="${isRead}" />
	<form:errors cssClass="error" path="body" />
	<br />


	<form:label path="tags">
		<spring:message code="message.tags" />:
	</form:label>
	<form:textarea path="tags" readonly="${isRead}" />
	<form:errors cssClass="error" path="tags" />
	<br />



	<jstl:if test="${isRead!=true}">
		<form:hidden path="box" />
		<form:label path="priority">
			<spring:message code="message.priority" />:
	</form:label>
		<form:select id="priorities" path="priority">
			<form:options items="${priorities}" />
		</form:select>
		<form:errors cssClass="error" path="priority" />


		<form:label path="recipient">
			<spring:message code="message.receiver" />:
	</form:label>
		<form:select id="receivers" path="recipient">
			<form:options items="${receivers}" itemValue="id"
				itemLabel="userAccount.username" />
		</form:select>
		<form:errors cssClass="error" path="recipient" />
		<br />
	</jstl:if>
	<jstl:if test="${isRead!=true || (isMove==true && isRead==true)}">
		<input type="submit" name="save"
			value="<spring:message code="message.save" />" />&nbsp; 
		
		<jstl:if test="${entityMessage.id!=0}">
			<input type="submit" name="delete"
				value="<spring:message code="message.delete" />"
				onclick="javascript: return confirm('<spring:message code="message.confirm.delete" />')" />
		</jstl:if>
		<input type="button" name="cancel"
			value="<spring:message code="message.cancel" />"
			onclick="javascript: relativeRedir('box/actor/list.do');" />
		<br />

	</jstl:if>



</form:form>
<p>
	<jstl:out value="${oops }"/>
</p>




