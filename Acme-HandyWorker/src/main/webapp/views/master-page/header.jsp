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
			<li><a href="configuration/administrator/list.do"><spring:message
						code="master.page.administrator.configuration" /></a></li>
			<li><a class="fNiv" href="category/administrator/list.do"><spring:message
						code="master.page.categories" /></a></li>
			<li><a href="actor/administrator/list.do"><spring:message
						code="master.page.administrator.actors" /></a></li>
			<li><a href="warranty/administrator/list.do"><spring:message
						code="master.page.administrator.warranty" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">

			<li><a class="fNiv" href="fixUpTask/customer/list.do"><spring:message
						code="master.page.fixuptasks" /></a></li>

			<li><a class="fNiv" href="application/customer/list.do"><spring:message
						code="master.page.application" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv" href="sponsorship/sponsor/list.do"><spring:message
						code="master.page.sponsorship" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv" href="complaint/list.do"><spring:message
						code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/customer/list.do"><spring:message
								code="master.page.myComplaint" /></a></li>
				</ul></li>
		</security:authorize>


		<security:authorize access="hasRole('HANDY')">
			<li><a class="fNiv" href="fixUpTask/handyWorker/list.do"><spring:message
						code="master.page.fixuptasks2" /></a></li>
			<li><a class="fNiv" href="complaint/list.do"><spring:message
						code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/handyWorker/list.do"><spring:message
								code="master.page.myComplaint" /></a></li>
				</ul></li>

			<li><a href="application/handyworker/list.do"><spring:message
						code="master.page.handy.application.list" /></a></li>

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

		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv" href="report/referee/list.do"><spring:message
						code="master.page.report" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv" href="note/referee/list.do"><spring:message
						code="master.page.note" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('REFEREE')">
			<li><a class="fNiv" href="complaint/list.do"><spring:message
						code="master.page.complaint" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="complaint/referee/list.do"><spring:message
								code="master.page.myComplaint" /></a></li>
				</ul></li>

		</security:authorize>


		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="box/actor/list.do"><spring:message
						code="master.page.box" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/actor/exchangeMessage.do"><spring:message
								code="master.page.message.exchange" /></a></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="message/administrator/broadcastMessage.do"><spring:message
									code="master.page.message.broadcast" /></a></li>
					</security:authorize>

				</ul></li>


		</security:authorize>


		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="register/actor.do?authority=CUSTOMER"><spring:message
								code="master.page.register.customer" /></a></li>

					<li><a href="register/actor.do?authority=HANDY"><spring:message
								code="master.page.register.handy" /></a></li>
					<li><a href="register/actor.do?authority=SPONSOR"><spring:message
								code="master.page.register.sponsor" /></a></li>


				</ul></li>

		</security:authorize>

		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message
						code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a
						href="<jstl:url value="register/administrator/newActor.do?authority=REFEREE"/>"><spring:message
								code="master.page.register.referee" /></a></li>
					<li><a
						href="<jstl:url value="register/administrator/newActor.do?authority=ADMIN"/>"><spring:message
								code="master.page.register.admin" /></a></li>
				</ul></li>
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
				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="profile/list.do"><spring:message
						code="master.page.profile.social" /></a></li>

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

