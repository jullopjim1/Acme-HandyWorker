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

<form:form action="${requestURI}" modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="status" />


	<form:label path="price">
		<spring:message code="application.price" />
	</form:label>
	<form:input path="price" />
	<form:errors ccsClass="error" path="price" />
	<br />

	<form:label path="comments">
		<spring:message code="application.comments" />
	</form:label>
	<form:input path="comments" />
	<form:errors ccsClass="error" path="comments" />
	<br />

	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${application.id != 0 }">

			<form:label path="status">
				<spring:message code="application.status"></spring:message>
			</form:label>
			<form:select id="statuses" path="status">
				<form:options items="${status}" />
			</form:select>
			<br />
		</jstl:if>

	</security:authorize>

	<jstl:if
		test="${application.id !=0 && application.status == 'ACCEPTED'}">
		<fieldset>
			<legend>
				<spring:message code="application.creditcard" />
			</legend>

			<form:label path="creditCard.holderName">
				<spring:message code="creditCard.holderName" />:
     		 </form:label>
			<form:input path="creditCard.holderName" />
			<form:errors cssClass="error" path="creditCard.holderName" />
			<br>

			<form:label path="creditCard.brandName">
				<spring:message code="creditCard.brandName" />:
      		</form:label>
			<form:input path="creditCard.brandName" />
			<form:errors cssClass="error" path="creditCard.brandName" />
			<br>

			<form:label path="creditCard.number">
				<spring:message code="creditCard.number" />:
     		 </form:label>
			<form:input path="creditCard.number" />
			<form:errors cssClass="error" path="creditCard.number" />
			<br>

			<form:label path="creditCard.expirationMonth">
				<spring:message code="creditCard.expirationMonth" />:
      		</form:label>
			<form:input path="creditCard.monthExpiration" />
			<form:errors cssClass="error" path="creditCard.expirationMonth" />
			<br>

			<form:label path="creditCard.expirationYear">
				<spring:message code="creditCard.expirationYear" />:
     		</form:label>
			<form:input path="creditCard.yearExpiration" />
			<form:errors cssClass="error" path="creditCard.expirationYear" />
			<br>

			<form:label path="creditCard.CVVCode">
				<spring:message code="creditCard.cvvCode" />:
      		</form:label>
			<form:input path="creditCard.cvv" />
			<form:errors cssClass="error" path="creditCard.CVVCode" />
			<br>

		</fieldset>
	</jstl:if>



	<input type="submit" name="save"
		value="<spring:message code="tutorial.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="tutorial.cancel" />"
		onclick="javascript: relativeRedir('tutorial/handyworker/list.do');" />

	<br />


</form:form>