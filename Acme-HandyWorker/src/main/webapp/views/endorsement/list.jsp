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

<display:table name="endorsements" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column property="endorsee.userAccount.username" titleKey="endorsement.endorsee" />
	<display:column property="moment" titleKey="message.moment" />
	<display:column property="comments" titleKey="endorsement.comments" />

	<display:column>
		<a href="endorsement/edit.do?endorsementId=${row.id}"> <spring:message
				code="message.edit" />
		</a>
	</display:column>
	
	<display:column>
		<a href="endorsement/show.do?endorsementId=${row.id}"> <spring:message
				code="message.show" />
		</a>
	</display:column>


</display:table>

<a href="endorsement/create.do"> <spring:message
		code="endorsement.create" /></a>



