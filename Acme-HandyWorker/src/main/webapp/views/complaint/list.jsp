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

<security:authorize access="hasRole('CUSTOMER')">
	<security:authorize access="hasRole('REFEREE')">
		<display:table name="complaints" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">
			<display:column property="ticker" titleKey="complaint.ticker" />
			<display:column property="moment" titleKey="complaint.moment" />
			<display:column property="description"
				titleKey="complaint.description" />
			<display:column property="attachments"
				titleKey="complaint.attachments" />

			<security:authorize access="hasRole('REFEREE')">
				<display:column>
					<jstl:if test="${complaint.report==Null}">
						<spring:message code="complaint.assign" />
						<input type="checkbox" value="True" />
					</jstl:if>
					<a href="report/referee/list.do?reportId=${report.id}"> <spring:message
							code="report.list" />
					</a>
				</display:column>
			</security:authorize>

			<security:authorize access="hasRole('CUSTOMER')">
				<display:column>
					<a href="complaint/customer/edit.do?complaintId=${complaint.id}">
						<spring:message code="complaint.edit" />
					</a>
				</display:column>
			</security:authorize>
		</display:table>
	</security:authorize>
</security:authorize>