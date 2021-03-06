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


<display:table name="tutorials" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<security:authorize access="hasRole('HANDY')">
		<jstl:if test="${handyWorkerId==row.handyWorker.id}">
			<display:column>

				<a href="tutorial/handyworker/edit.do?tutorialId=${row.id}"> <spring:message
						code="tutorial.edit" />
				</a>

			</display:column>
		</jstl:if>
	</security:authorize>

	<display:column titleKey="tutorial.details">
		<a href="tutorial/show.do?tutorialId=${row.id}"> <spring:message
				code="tutorial.show" />
		</a>
	</display:column>

	<display:column titleKey="tutorial.sponsorship">
		<img src="${row.sponsorship.banner}" height="100px" width="100px" />
	</display:column>

	<display:column titleKey="tutorial.section">
		<a href="section/list.do?tutorialId=${row.id}"> <spring:message
				code="tutorial.view" />
		</a>
	</display:column>

	<display:column titleKey="tutorial.handyworker">
		<a href="actor/showProfileTutorial.do?tutorialId=${row.id}"> <spring:message
				code="tutorial.profile" />
		</a>
	</display:column>

	<security:authorize access="hasRole('HANDY')">
		<jstl:if test="${handyWorkerId==row.handyWorker.id}">
			<display:column>

				<a href="section/handyworker/create.do?tutorialId=${row.id}"> <spring:message
						code="section.create" />
				</a>

			</display:column>
		</jstl:if>
	</security:authorize>
</display:table>
<security:authorize access="hasRole('HANDY')">
	<a href="tutorial/handyworker/create.do"> <spring:message
			code="tutorial.create" />
	</a>
</security:authorize>
<br />

<br />
<input type="button" name="home"
	value="<spring:message code="tutorial.back" />"
	onclick="javascript: relativeRedir('welcome/index.do');" />

