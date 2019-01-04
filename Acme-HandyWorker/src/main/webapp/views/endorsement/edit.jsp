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

<form:form action="endorsement/edit.do" modelAttribute="endorsement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="endorser" />
	<form:hidden path="score" />


	<form:label path="comments">
		<spring:message code="endorsement.comments" />:
	</form:label>
	<form:textarea cols="40" rows="7" path="comments"
		readonly="${isRead }" />
	<form:errors cssClass="error" path="comments" />
	<br />
<jstl:if test="${isRead==true }">
	<form:label path="endorsee">
		<spring:message code="endorsement.endorsee" />:
	</form:label>
	<form:input path="endorsee.userAccount.username" readonly="${isRead }" />
	<form:errors cssClass="error" path="endorsee" />
	<br />
</jstl:if>
<jstl:if test="${isRead==false }">

	<form:label path="endorsee">
		<spring:message code="endorsement.endorsee" />:
	</form:label>
	<form:select id="endorsees" path="endorsee" readonly="${isRead }">
		<form:options items="${endorsees}" itemValue="id"
			itemLabel="userAccount.username" />
	</form:select>
	<form:errors cssClass="error" path="endorsee" />
	<br />



	<input type="submit" name="save"
		value="<spring:message code="box.save" />" />&nbsp; 
	
	<jstl:if test="${endorsement.id != 0}">

		<input type="submit" name="delete"
			value="<spring:message code="message.delete" />"
			onclick="javascript: return confirm('<spring:message code="message.confirm.delete" />')" />

	</jstl:if>

	<input type="button" name="cancel"
		value="<spring:message code="message.cancel" />"
		onclick="javascript: relativeRedir('endorsement/list.do');" />
	<br />
	</jstl:if>
</form:form>
