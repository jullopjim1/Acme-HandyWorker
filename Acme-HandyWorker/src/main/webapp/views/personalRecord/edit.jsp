<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="${requestURI}" modelAttribute="personalRecord">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculum" />



	<form:label path="fullName">
		<spring:message code="personalRecord.fullName" />:
	</form:label>
	<form:input path="fullName" readonly="${isRead}" />
	<form:errors cssClass="error" path="fullName" />
	<br />

	<form:label path="email">
		<spring:message code="personalRecord.email" />:
	</form:label>
	<form:input path="email" readonly="${isRead}" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="photo">
		<spring:message code="personalRecord.picture" />:
	</form:label>
	<form:input path="photo" readonly="${isRead}" />
	<form:errors cssClass="error" path="photo" />
	<br />

	<form:label path="link">
		<spring:message code="personalRecord.link" />:
	</form:label>
	<form:input path="link" readonly="${isRead}" />
	<form:errors cssClass="error" path="link" />
	<br />

	<form:label path="phone">
		<spring:message code="personalRecord.phone" />
	</form:label>
	<form:input path="phone" id="tlf" readonly="${isRead}" />
	<form:errors path="phone" cssClass="error" />
	<br />


	<script type="text/javascript">
		function isValid(phoneElement) {
			var phoneRe = /^(((\+[1-9][0-9]{0,2}) \(([1-9][0-9]{0,2})\) (\d\d\d\d+))|((\+[1-9][0-9]{0,2}) (\d\d\d\d+))|((\d\d\d\d+)))$/;
			var digits = document.getElementById(phoneElement).value;
			var res = phoneRe.test(digits);
			if (res) {
				return true;
			} else {
				return confirm('<spring:message code="phone.confirm" />');
			}
		}
	</script>

	<jstl:if test="${isRead == false}">
		<input type="button" class="btn btn-warning" name="cancel"
			value='<spring:message code="curriculum.cancel"/>'
			onclick="document.location.href='curriculum/handyworker/list.do?handyWorkerId=${handyWorkerId}';">
		<input type="submit" class="btn btn-success" name="save"
			value='<spring:message code="curriculum.save"/>'
			onclick=" javascript: return isValid('tlf');">
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" class="btn btn-warning" name="back"
			value='<spring:message code="curriculum.back"/>'
			onclick="document.location.href='curriculum/handyworker/list.do?handyWorkerId=${handyWorkerId}';">
	</jstl:if>


</form:form>
