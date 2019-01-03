<%--
 * header.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div align="center">
	<a href="#"><img src="images/logo2.png"
		alt="Acme-HandyWorker Co., Inc." /></a>
</div>

<div align="center">
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a href="dashboard/administrator/dashboard.do"><spring:message
						code="master.page.administrator.dashboard" /></a></li>
						<li><a href="actor/administrator/list.do"><spring:message
						code="master.page.administrator.actors" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message
						code="master.page.customer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="customer/action-1.do"><spring:message
								code="master.page.customer.action.1" /></a></li>

				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv" href="sponsorship/sponsor/list.do"><spring:message
						code="master.page.sponsorship" /></a>

			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('HANDY')">
			<li><a href="curriculum/handyworker/list.do"><spring:message
						code="master.page.handy.curriculum.list" /></a></li>


			<li><a class="fNiv"><spring:message
						code="master.page.finder" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="finder/handy/update.do"><spring:message
								code="master.page.finder.update" /></a></li>
					<li><a href="finder/handy/listFixUpTasks.do"><spring:message
								code="master.page.finder.result" /></a></li>

				</ul></li>
		</security:authorize>
		

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

		</security:authorize>

		<security:authorize access="permitAll()">
			<li><a class="fNiv" href="tutorial/list.do"><spring:message
						code="master.page.tutorial" /></a>
			<ul>
				<li class="arrow"></li>
				<security:authorize access="hasRole('HANDY')">
					<li><a href="tutorial/handyworker/list.do"><spring:message
								code="master.page.handy.tutorial.list" /></a></li>
				</security:authorize>
			</ul>

			</li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">

			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/edit.do"><spring:message
								code="master.page.profile.edit" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div align="center">
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

