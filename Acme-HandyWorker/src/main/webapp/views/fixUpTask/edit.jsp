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

<form:form action="${requestURI}" modelAttribute="fixUpTask">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="customer" />

	<form:label path="description">
		<spring:message code="fixuptask.description" />:
	</form:label>
	<form:input path="description" readonly="${isRead}" />
	<form:errors cssClass="error" path="description" />
	<br />

	<form:label path="adress">
		<spring:message code="fixuptask.address" />:
	</form:label>
	<form:input path="adress" readonly="${isRead}" />
	<form:errors cssClass="error" path="adress" />
	<br />

	<form:label path="maxPrice">
		<spring:message code="fixuptask.maxPrice" />:
	</form:label>
	<form:input path="maxPrice" readonly="${isRead}" />
	<form:errors cssClass="error" path="maxPrice" />
	<br />

	<form:label path="deadline">
		<spring:message code="fixuptask.deadlineWith" />:
	</form:label>
	<form:input path="deadline" readonly="${isRead}"
		placeholder="yyyy/mm/dd 00:00" />
	<form:errors cssClass="error" path="deadline" />
	<br />

	<form:label path="category">
		<spring:message code="fixuptask.category" />:
	</form:label>
	<form:select path="category" readonly="${isRead}">
		<form:options items="${categories}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="category" />

	<br />

	<form:label path="warranty">
		<spring:message code="fixuptask.warranty" />:
	</form:label>
	<form:select id="warranties" path="warranty" readonly="${isRead }">
		<form:options items="${warranties}" itemValue="id" itemLabel="title" />
	</form:select>
	<form:errors cssClass="error" path="warranty" />
	<br />

	<jstl:if test="${isRead == false}">
		<input type="submit" name="save"
			value="<spring:message code="fixuptask.save" />" />

		<input type="submit" name="delete"
			value="<spring:message code="fixuptask.delete" />"
			onclick="javascript: return confirm('<spring:message code="fixuptask.confirmDelete" />')" />

		<input type="button" name="cancel"
			value="<spring:message code="fixuptask.cancel" />"
			onclick="javascript: relativeRedir('fixUpTask/customer/list.do');" />
		<br />

	</jstl:if>


	<jstl:if test="${isRead == true}">

		<security:authorize access="hasRole('CUSTOMER')">
			<input type="button" name="back"
				value="<spring:message code="fixuptask.back" />"
				onclick="javascript: relativeRedir('/fixUpTask/customer/list.do');" />
		</security:authorize>

		<security:authorize access="hasRole('HANDY')">
			<input type="button" name="back"
				value="<spring:message code="fixuptask.back" />"
				onclick="javascript: relativeRedir('/fixUpTask/handyWorker/list.do');" />
		</security:authorize>

		<br />

	</jstl:if>

</form:form>
