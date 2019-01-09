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

<security:authorize access="hasRole('SPONSOR')">
	<form:form action="${requestURI}" modelAttribute="sponsorship">
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="sponsor" />

		<form:label path="banner">
			<spring:message code="sponsorship.banner" />
		</form:label>
		<form:input path="banner" readonly="${isRead}" />
		<form:errors cssClass="error" path="banner" />
		<br />

		<form:label path="link">
			<spring:message code="sponsorship.link" />
		</form:label>
		<form:input path="link" readonly="${isRead}" />
		<form:errors cssClass="error" path="link" />
		<br />


		<fieldset>
			<legend>
				<spring:message code="sponsorship.creditcard" />
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
			<form:select path="creditCard.brandName" readonly="${isRead}">
				<form:option value="VISA"/>
				<form:option value="MASTER"/>
				<form:option value="DINNERS"/>
				<form:option value="AMEX"/>
			</form:select>
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
			<form:input path="creditCard.expirationMonth" />
			<form:errors cssClass="error" path="creditCard.expirationMonth" />
			<br>

			<form:label path="creditCard.expirationYear">
				<spring:message code="creditCard.expirationYear" />:
     		</form:label>
			<form:input path="creditCard.expirationYear" />
			<form:errors cssClass="error" path="creditCard.expirationYear" />
			<br>

			<form:label path="creditCard.CVVCode">
				<spring:message code="creditCard.cvvCode" />:
      		</form:label>
			<form:input path="creditCard.CVVCode" />
			<form:errors cssClass="error" path="creditCard.CVVCode" />
			<br>

		</fieldset>



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
</security:authorize>