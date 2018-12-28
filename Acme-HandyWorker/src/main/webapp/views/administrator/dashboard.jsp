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

<h3>
	<spring:message code="administrator.dashboard.C1" />
</h3>

<ul>
	<li><spring:message code="administrator.avg" />: ${avgC1}</li>
	<li><spring:message code="administrator.max" />: ${maxC1}</li>
	<li><spring:message code="administrator.min" />: ${minC1}</li>
	<li><spring:message code="administrator.stddev" />: ${stddevC1}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C2" />
</h3>
<ul>
	<li><spring:message code="administrator.avg" />: ${avgC2}</li>
	<li><spring:message code="administrator.max" />: ${maxC2}</li>
	<li><spring:message code="administrator.min" />: ${minC2}</li>
	<li><spring:message code="administrator.stddev" />: ${stddevC2}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C3" />
</h3>
<ul>
	<li><spring:message code="administrator.avg" />: ${avgC3}</li>
	<li><spring:message code="administrator.max" />: ${maxC3}</li>
	<li><spring:message code="administrator.min" />: ${minC3}</li>
	<li><spring:message code="administrator.stddev" />: ${stddevC3}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C4" />
</h3>
<ul>
	<li><spring:message code="administrator.avg" />: ${avgC4}</li>
	<li><spring:message code="administrator.max" />: ${maxC4}</li>
	<li><spring:message code="administrator.min" />: ${minC4}</li>
	<li><spring:message code="administrator.stddev" />: ${stddevC4}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C5" />
</h3>
<ul>
	<li><spring:message code="administrator.ratio" />: ${queryC5}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C6" />
</h3>
<ul>
	<li><spring:message code="administrator.ratio" />: ${queryC6}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C7" />
</h3>
<ul>
	<li><spring:message code="administrator.ratio" />: ${queryC7}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C8" />
</h3>
<ul>
	<li><spring:message code="administrator.ratio" />: ${queryC8}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C9" />
</h3>
<display:table name="queryC9" id="row" requestURI="${requestURI}"
	class="displaytag">
	<display:column property="name" titleKey="actor.name" />
</display:table>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.C10" />
</h3>
<display:table name="queryC10" id="row" requestURI="${requestURI}"
	class="displaytag">
	<display:column property="name" titleKey="actor.name" />
</display:table>
<br />
<br />

<h3>
	<spring:message code="administrator.dashboard.B1" />
</h3>
<ul>
	<li><spring:message code="administrator.avg" />: ${avgB1}</li>
	<li><spring:message code="administrator.max" />: ${maxB1}</li>
	<li><spring:message code="administrator.min" />: ${minB1}</li>
	<li><spring:message code="administrator.stddev" />: ${stddevB1}</li>
</ul>
<br />
<br />

<h3>
	<spring:message code="administrator.dashboard.B2" />
</h3>
<ul>
	<li><spring:message code="administrator.avg" />: ${avgB2}</li>
	<li><spring:message code="administrator.max" />: ${maxB2}</li>
	<li><spring:message code="administrator.min" />: ${minB2}</li>
	<li><spring:message code="administrator.stddev" />: ${stddevB2}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.B3" />
</h3>
<ul>
	<li><spring:message code="administrator.ratio" />: ${queryC6}</li>
</ul>
<br />
<br />
<h3>
	<spring:message code="administrator.dashboard.B4" />
</h3>
<display:table name="queryB4" id="row" requestURI="${requestURI}"
	class="displaytag">
	<display:column property="name" titleKey="actor.name" />
</display:table>
<br />
<br />


<h3>
	<spring:message code="administrator.dashboard.B5" />
</h3>
<display:table name="queryB5" id="row" requestURI="${requestURI}"
	class="displaytag">
	<display:column property="name" titleKey="actor.name" />
</display:table>
<br />
<br />
