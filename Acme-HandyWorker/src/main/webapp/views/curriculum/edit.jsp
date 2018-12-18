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


<form:form action="${requestURI}" modelAttribute="curriculum"
	readonly="${isRead}">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="handyWorker" />



	<form:label path="personalRecord.fullName">
		<spring:message code="personalRecord.fullName" />:
	</form:label>
	<form:input path="personalRecord.fullName" />
	<form:errors cssClass="error" path="personalRecord.fullName" />
	<br />

	<form:label path="personalRecord.email">
		<spring:message code="personalRecord.email" />:
	</form:label>
	<form:input path="personalRecord.email" />
	<form:errors cssClass="error" path="personalRecord.email" />
	<br />

	<form:label path="personalRecord.photo">
		<spring:message code="personalRecord.picture" />:
	</form:label>
	<form:input path="personalRecord.photo" />
	<form:errors cssClass="error" path="personalRecord.photo" />
	<br />

	<form:label path="personalRecord.link">
		<spring:message code="personalRecord.link" />:
	</form:label>
	<form:input path="personalRecord.link" />
	<form:errors cssClass="error" path="personalRecord.link" />
	<br />

	<form:label path="personalRecord.phone">
		<spring:message code="personalRecord.phone" />
	</form:label>
	<form:input path="personalRecord.phone" id="tlf" />
	<form:errors path="personalRecord.phone" cssClass="error" />
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
			onclick="document.location.href='curriculum/handyWorker/list.do';">
		<input type="submit" class="btn btn-success" name="save"
			value='<spring:message code="curriculum.save"/>'
			onclick=" javascript: return isValid('tlf');">
	</jstl:if>

	<jstl:if test="${isRead == true}">
		<input type="button" class="btn btn-warning" name="back"
			value='<spring:message code="curriculum.back"/>'
			onclick="document.location.href='curriculum/handyWorker/list.do';">
	</jstl:if>


</form:form>
