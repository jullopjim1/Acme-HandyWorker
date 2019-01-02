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


<display:table name="actors" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag">

	<display:column titleKey="actor.profile">
		<a href="actor/show.do?actorId=${row.id}"> <spring:message
				code="application.edit" />
		</a>
	</display:column>

	<security:authorize access="hasRole('ADMIN')">

		<display:column>
			<input type="button" value="<spring:message code="actor.ban"/>"
				onclick="
			javascript:
			window.location.href='actor/administrator/ban.do?actorId=${row.id}' ;" />

			<input type="button" value="<spring:message code="actor.unban"/>"
				onclick="
			javascript:
			window.location.href='actor/administrator/unban.do?actorId=${row.id}' ;" />
		</display:column>

	</security:authorize>



</display:table>