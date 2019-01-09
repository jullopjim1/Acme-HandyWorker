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
			<jstl:if test="${row.status == 'PENDING' }">
				<jstl:if test="${customerId==row.fixUpTask.customer.id}">
					<a href="application/customer/edit.do?applicationId=${row.id}">
						<spring:message code="application.edit" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column property="moment" titleKey="application.moment" />


	<display:column property="price" titleKey="application.price" />


	<display:column property="comments" titleKey="application.comments" />

	<display:column property="status" titleKey="application.status" />
	
	<display:column property="edit" titleKey="application.edit" />
	
	<display:column property="show" titleKey="application.show" />
	
	<display:column property="phase" titleKey="application.phase" />


</display:table>