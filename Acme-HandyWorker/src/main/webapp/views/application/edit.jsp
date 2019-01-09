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
				<form:options items="${statuses}" />
			</form:select>
			<br />
		</jstl:if>

	</security:authorize>

	<fieldset>
		<legend>
			<spring:message code="application.creditcard" />
		</legend>

		<form:label path="holderName">
			<spring:message code="creditCard.holderName" />:
     		 </form:label>
		<form:input path="holderName" />
		<form:errors cssClass="error" path="holderName" />
		<br>

		<form:label path="brandName">
			<spring:message code="creditCard.brandName" />:
      		</form:label>
		<form:input path="brandName" />
		<form:errors cssClass="error" path="brandName" />
		<br>

		<form:label path="number">
			<spring:message code="creditCard.number" />:
     		 </form:label>
		<form:input path="number" />
		<form:errors cssClass="error" path="number" />
		<br>

		<form:label path="expirationMonth">
			<spring:message code="creditCard.expirationMonth" />:
      		</form:label>
		<form:input path="expirationMonth" />
		<form:errors cssClass="error" path="expirationMonth" />
		<br>

		<form:label path="expirationYear">
			<spring:message code="creditCard.expirationYear" />:
     		</form:label>
		<form:input path="expirationYear" />
		<form:errors cssClass="error" path="expirationYear" />
		<br>

		<form:label path="CVVCode">
			<spring:message code="creditCard.cvvCode" />:
      		</form:label>
		<form:input path="CVVCode" />
		<form:errors cssClass="error" path="CVVCode" />
		<br>
		<br>
		<legend>
			<spring:message code="application.warningCreditcard" />
		</legend>
	</fieldset>

	<input type="submit" name="save"
		value="<spring:message code="application.save"/>" />


	<input type="button" name="cancel"
		value="<spring:message code="application.cancel" />"
		onclick="javascript: relativeRedir('application/customer/list.do');" />

	<br />

</form:form>