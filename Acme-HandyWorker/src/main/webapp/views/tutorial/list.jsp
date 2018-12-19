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
		<display:column>
			<jstl:if test="${handyWorkerId==row.handyWorker.id}">
				<a href="tutorial/handyworker/edit.do?tutorialId=${row.id}"> <spring:message
						code="tutorial.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column titleKey="tutorial.details">
		<a href="tutorail/show.do?tutorialId=${row.id}"> <spring:message
				code="tutorial.show" />
		</a>
	</display:column>

	<display:column property="sponsorship.banner"
		titleKey="tutorial.sponsorship">
		<img src="${row.sponsorship.banner}" />
	</display:column>

	<display:column title="tutorial.section">
		<a href="tutorial/handyworker/view.do?tutorialId=${row.id}"> <spring:message
				code="tutorial.view" />
		</a>
	</display:column>

	<display:column titleKey="tutorial.handyworker">
		<a href="profile/view.do?tutorialId=${row.id}"> <spring:message
				code="tutorial.profile" />
		</a>
	</display:column>

</display:table>
<security:authorize access="hasRole('HANDY')">
	<a href="tutorial/handyworker/create.do"> <spring:message
			code="tutorial.create" />
	</a>
</security:authorize>

<security:authorize access="isAnonymous()">
	<input type="button" name="home"
		value="<spring:message code="tutorial.home" />"
		onclick="javascript: relativeRedir('welcome/index.do');" />
</security:authorize>
