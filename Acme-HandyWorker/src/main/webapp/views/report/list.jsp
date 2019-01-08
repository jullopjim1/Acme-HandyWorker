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

<display:table name="reports" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('REFEREE')">
		<jstl:if test="${refereeId==row.referee.id and row.isFinal == false }">
			<display:column>

				<a href="report/referee/edit.do?reportId=${row.id}"> <spring:message
						code="report.edit" />
				</a>

			</display:column>
		</jstl:if>
	</security:authorize>


	<display:column property="moment" titleKey="report.moment" />
	<display:column property="description" titleKey="report.description" />
	<display:column property="attachments" titleKey="report.attachments" />

	<jstl:if test="${noteService.findNoteReportById(row.id) != null}">
		<display:column titleKey="report.notes">
			<a href="note/list.do?reportId=${row.id}"> <spring:message
					code="report.show" />
			</a>
		</display:column>
	</jstl:if>

	<security:authorize access="hasRole('REFEREE')">
		<jstl:if test="${noteService.findNoteReportById(row.id) == null && row.isFinal==true}">
			<display:column>

				<a href="note/referee/create.do?reportId=${row.id}"> <spring:message
						code="note.create" />
				</a>
		<jstl:if test="${noteService.findNoteReportById(row.id) != null}">
			<a href="note/list.do?reportId=${row.id}"> <spring:message
					code="note.show" />
			</a>
		</jstl:if>

			</display:column>
		</jstl:if>
	</security:authorize>





</display:table>
