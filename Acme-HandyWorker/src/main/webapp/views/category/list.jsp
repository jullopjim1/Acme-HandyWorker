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

<display:table name="categories" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	<display:column>
		<jstl:if test="${null!=row.rootcategory}">
			<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message
					code="category.edit" />
			</a>
		</jstl:if>
	</display:column>

	<display:column property="name" titleKey="category.name" />
	<display:column property="rootcategory.name"
		titleKey="category.rootCategory" />
</display:table>

<br />
<a href="category/administrator/create.do"> <spring:message
		code="category.create" />
</a>