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

<display:table name="complaints" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('CUSTOMER')">
		<jstl:if test="${customerId==row.customer.id}">
			<display:column>
				<a href="complaint/customer/edit.do?complaintId=${row.id}">
					<spring:message code="complaint.edit" />
				</a>
			</display:column>
		</jstl:if>
	</security:authorize>

	<display:column titleKey="complaint.details">
		<a href="complaint/show.do?complaintId=${row.id}"> <spring:message
				code="complaint.show" />
		</a>
	</display:column>

	<display:column property="ticker" titleKey="complaint.ticker" />


	<security:authorize access="hasRole('REFEREE')">
		<display:column>
			<jstl:if test="${complaint.report==null}">
				<a href="complaint/referee/edit.do?complaintId=${row.id}"> <spring:message
						code="complaint.assign" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column titleKey="complaint.report">

		<a href="report/referee/show.do?complaintId=${row.id}"> <spring:message
				code="complaint.show" />
		</a>


	</display:column>



</display:table>
