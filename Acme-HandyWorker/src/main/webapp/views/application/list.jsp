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


<display:table name="applications" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
				<jstl:if test="${customerId==row.fixUpTask.customer.id}">
					<a href="application/customer/edit.do?applicationId=${row.id}">
						<spring:message code="application.edit" />
					</a>
				</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.status == 'PENDING' }">
				<jstl:if test="${customerId==row.fixUpTask.customer.id}">
					<a href="application/customer/accept.do?applicationId=${row.id}">
						<spring:message code="application.accept" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if test="${row.status == 'PENDING' }">
				<jstl:if test="${customerId==row.fixUpTask.customer.id}">
					<a href="application/customer/reject.do?applicationId=${row.id}">
						<spring:message code="application.reject" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column property="moment" titleKey="application.moment" />

	<display:column property="status" titleKey="application.status" />

	<security:authorize access="hasRole('HANDY')">
		<display:column titleKey="application.edit">
			<jstl:if test="${row.status != 'ACCEPTED' }">
				<a href="application/handyWorker/edit.do?applicationId=${row.id}">
					<spring:message code="application.edit.link" />
				</a>
			</jstl:if>
		</display:column>


		<display:column titleKey="application.show">
			<a href="application/handyWorker/show.do?applicationId=${row.id}">
				<spring:message code="application.show.link" />
			</a>
		</display:column>

		<display:column titleKey="application.phase">
			<jstl:if test="${row.status == 'ACCEPTED' }">
				<a href="phase/handyWorker/create.do"><spring:message
						code="application.phase.link" /> </a>
			</jstl:if>
		</display:column>
	</security:authorize>

</display:table>