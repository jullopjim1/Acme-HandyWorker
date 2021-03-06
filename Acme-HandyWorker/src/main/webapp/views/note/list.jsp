<%--
 * list.jsp
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


<display:table name="note" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('REFEREE')">

		<display:column>
			<jstl:if test="${row.isFinal==false && refereeId==row.report.referee.id}">
				<a href="note/edit.do?noteId=${row.id}"> <spring:message
						code="note.edit" />
				</a>
			</jstl:if>
		</display:column>
		
	</security:authorize>
	<security:authorize access="hasAnyRole('CUSTOMER','HANDY')">
		<display:column>
			<jstl:if test="${row.isFinal==false}">
				<a href="note/edit.do?noteId=${row.id}"> <spring:message
						code="note.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	<display:column property="moment" titleKey="note.moment" />

	<display:column property="mandatoryCommentReferee"
		titleKey="note.commentReferee" />


	<display:column property="mandatoryCommentCustomer"
		titleKey="note.commentCustomer" />


	<display:column property="mandatoryCommentHandyWorker"
		titleKey="note.commentHandyworker" />




</display:table>
<br />


