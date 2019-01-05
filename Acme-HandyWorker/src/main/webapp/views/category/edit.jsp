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

<form:form action="category/administrator/edit.do"
	modelAttribute="categoryForm">

	<form:hidden path="id" />

	<form:label path="nameEN">
		<spring:message code="category.nameEN" />:
	</form:label>
	<form:input path="nameEN" />
	<form:errors cssClass="error" path="nameEN" />
	<br />
	
	<form:label path="nameES">
		<spring:message code="category.nameES" />:
	</form:label>
	<form:input path="nameES" />
	<form:errors cssClass="error" path="nameES" />
	<br />

	<form:label path="rootcategory">
		<spring:message code="category.rootCategory" />:
	</form:label>
	<form:select id="categories" path="rootcategory" readonly="${isRead }">
		<form:options items="${categories}" itemValue="id" itemLabel="name" />
	</form:select>
	<form:errors cssClass="error" path="rootcategory" />

	<br />

	<input type="submit" name="save"
		value="<spring:message code="category.save" />" />

	<input type="submit" name="delete"
		value="<spring:message code="category.delete" />"
		onclick="javascript: return confirm('<spring:message code="category.confirmDelete" />')" />

	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
	<br />

</form:form>