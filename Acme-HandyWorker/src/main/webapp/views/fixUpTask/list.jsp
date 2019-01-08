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
				test="${complaintService.findComplaintFinalByTaskId(row.id)}">

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
	</security:authorize>


</display:table>

<br />
<security:authorize access="hasRole('CUSTOMER')">
	<a href="fixUpTask/customer/create.do"> <spring:message
			code="application.create" />
	</a>
</security:authorize>
