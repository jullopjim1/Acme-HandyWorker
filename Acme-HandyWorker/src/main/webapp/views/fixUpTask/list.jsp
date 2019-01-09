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

<form:form action="fixUpTask/handyWorker/addFilter.do"
	modelAttribute="finder">

	<form:label path="keyword">
		<spring:message code="finder.keyword" />
	</form:label>
	<form:input path="keyword" />
	<form:errors ccsClass="error" path="keyword" />
	<br />

	<form:label path="priceMin">
		<spring:message code="finder.priceMin" />
	</form:label>
	<form:input path="priceMin" />
	<form:errors ccsClass="error" path="priceMin" />
	<br />

	<form:label path="priceMax">
		<spring:message code="finder.priceMax" />
	</form:label>
	<form:input path="priceMax" />
	<form:errors ccsClass="error" path="priceMax" />
	<br />

	<form:label path="dateMin">
		<spring:message code="finder.dateMin" />
	</form:label>
	<form:input path="dateMin" placeholder="yyyy/mm/dd hh:mm" />
	<form:errors ccsClass="error" path="dateMin" />
	<br />

	<form:label path="dateMax">
		<spring:message code="finder.dateMax" />
	</form:label>
	<form:input path="dateMax" placeholder="yyyy/mm/dd hh:mm" />
	<form:errors ccsClass="error" path="dateMax" />
	<br />

	<form:label path="namecategory">
		<spring:message code="finder.namecategory" />
	</form:label>
	<form:select id="categories" path="namecategory">
		<form:option value="" label="------" />
		<form:options items="${categories}" />
	</form:select>

	<form:label path="namewarranty">
		<spring:message code="finder.namewarranty" />
	</form:label>
	<form:select id="warranties" path="namewarranty">
		<form:option value="" label="------" />
		<form:options items="${warranties}" itemLabel="title"
			itemValue="title" />
	</form:select>

	<input type="submit" name="save"
		value="<spring:message code="finder.save"/>" />

</form:form>

<display:table name="fixUpTasks" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('CUSTOMER')">

		<display:column>
			<jstl:if test="${customerId==row.customer.id}">
				<a href="fixUpTask/customer/edit.do?fixUpTaskId=${row.id}"> <spring:message
						code="fixuptask.edit" />
				</a>
			</jstl:if>
		</display:column>

	</security:authorize>

	<display:column property="ticker.ticker" titleKey="fixuptask.ticker" />

	<display:column titleKey="fixuptask.category">
		<jstl:forEach var="entry" items="${row.category.name}">
			<jstl:if test="${lang==entry.key}">
				${entry.value}
			</jstl:if>
		</jstl:forEach>
	</display:column>

	<display:column titleKey="fixuptask.details">
		<a href="fixUpTask/customer/show.do?fixUpTaskId=${row.id}"> <spring:message
				code="fixuptask.show" />
		</a>
	</display:column>




	<security:authorize access="hasRole('CUSTOMER')">
		<display:column titleKey="complaint.create">
			<jstl:if
				test="${complaintService.findComplaintByTaskId(row.id) == null}">

				<a href="complaint/customer/create.do?fixUpTaskId=${row.id}"> <spring:message
						code="curriculum.create" />
				</a>
			</jstl:if>
			<jstl:if
				test="${complaintService.findComplaintFinalByTaskId(row.id) != null}">

				<a href="complaint/showComplaint.do?fixUpTaskId=${row.id}"> <spring:message
						code="complaint.show" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('HANDY')">
		<display:column titleKey="fixUpTask.customer">
			<a
				href="handyWorker/viewProfileCustomer.do?customerId=${row.customer.id}">
				<spring:message code="fixUpTask.viewProfile" />
			</a>
		</display:column>

		<display:column titleKey="fixUpTask.application.create">
			<jstl:if
				test="${applicationService.findApplicationByHandyWorkerIdAndTaskId(handyId, row.id) == null and applicationService.findApplicationAcceptedByFixUpTaskId(row.id) == null}">
				<a href="application/handyworker/create.do?fixUpTaskId=${row.id}"><spring:message
						code="fixUpTask.application.create" /></a>
			</jstl:if>
		</display:column>
	</security:authorize>


</display:table>

<br />
<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/create.do"> <spring:message
			code="application.create" />
	</a>
</security:authorize>