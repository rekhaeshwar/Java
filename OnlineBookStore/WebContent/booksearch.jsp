<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.bookstore.infra.misc.wrapper.ResponseMessageWrapper"%>
<%@page
	import="org.springframework.web.servlet.support.RequestContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>
<%@page import="com.bookstore.web.util.RESTUtil"%>

<%@page import="org.apache.shiro.session.mgt.SessionContext"%>
<%@page import="org.apache.shiro.session.*"%>

<%
	Subject currentUser = SecurityUtils.getSubject();
	ApplicationContext context = RequestContextUtils.getWebApplicationContext(request);
	Session userSession = currentUser.getSession();
	RESTUtil restUtil = (RESTUtil) context.getBean("restUtil");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>ONLINE BOOK STORE</title>

<link rel="stylesheet"
	href="<c:url value="/resources/styles/style.css"/>" />

<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery-1.8.3.min.js"/>"></script>

<script type="text/javascript"
	src="<c:url value="/resources/scripts/myJQuery.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery.validate.min.js"/>"></script>

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#tablediv").hide();
						$("#normalsearch")
								.bind(
										'click',
										function() {
											var title = $("#title").val();
											var url = '/OnlineBookStore/booksearchsubmit/'
													+ title;
											$.get(url, processResponse);
											function processResponse(data) {
												if (data != null) {
													alert(data.length);
													for (i = 0; i < data.length; i++) {
														$("#tablediv")
																.html(
																		"<table></table>");
													}
												}
											}
										});

						$("#advancedsearch")
								.bind(
										'click',
										function() {
											var title = $("#advancedtitle")
													.val();
											var author = $("#advancedauthor")
													.val();
											var url = '/OnlineBookStore/booksearchsubmit?title='
													+ title
													+ '&author='
													+ author;

											$.get(url, processResponse);
											function processResponse(data) {
												displayTable(data);
												$("#tablediv").show();

												for (i = 0; i < data.length; i++) {
													$('#countrytable tr:last')
															.after(
																	'<tr><td align="right" >'
																			+ data[i].author
																			+ '</td><td>'
																			+ data[i].title
																			+ '</td><td><input type="button" class="addtocart" value="Add to cart" id="'+data[i].id+'"/> </td></tr>');
												}
												/* $("#countrytable").find(
														"tr:gt(0)").remove();
												var table1 = $("#countrytable");
												$
														.each(
																data,
																function(key,
																		value) {
																	var rowNew = $("<tr><td></td><td></td><td class='addbutton'></td></tr>");
																	rowNew
																			.children()
																			.eq(
																					0)
																			.text(
																					value['author']);
																	rowNew
																			.children()
																			.eq(
																					1)
																			.text(
																					value['title']);
															
																	var ele = $('<td></td>')
																    .attr('class',"")
																    .html("<input type='button' id='"+value['id']+"'/>");
																	alert(ele);
																	$(".addbutton").append(ele);
																	html("<input type='button' id='"+value['id']+"/' value='Add to cart'"); 
																	rowNew
																			.appendTo(table1); 
																});*/
											}
											/* var table=$('#display'), row=null, data1=null;
											row=$('<tr><th>Book Title</th></tr>');

											for(i=0;i<data.length;i++)
											{
												
											$("#tablediv").html("<table></table>");
											} */

										});

					});
</script>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<div id="logo">
				<h1>
					<a href="#">ONLINE BOOK STORE</a>
				</h1>
			</div>
		</div>
		<!-- end #header -->
		<!-- <div id="menu">
			<ul>
				<li class="current_page_item"><a href="index.html">Home</a></li>
				<li><a href="giftcard">GiftCards</a></li>
				<li><a href="cart">My Cart</a></li>
				<li><a href="#">Purchase History</a></li>
				<br>
		</div> -->
		<jsp:include page="/header.jsp" />

		<div id="two-columns">
			<div id="col1">
				<form:form commandName="book" method='get' accept-charset='UTF-8'
					id="springsearchform">
					<label for="Search">Search</label>
					<input type="text" name="search" id="title">

					<input type="button" value="Search" id="normalsearch" />
					<br>
					<br>
					<br>
				</form:form>
				<form:form commandName="book" method='get' accept-charset='UTF-8'
					id="springadvancedsearch">
Advanced Search:<br>
					<br>
					<label for="Title">Title</label>
					<input type="text" name="title" id="advancedtitle">
					<br>
					<br>
					<br>
					<label for="Author">Author</label>
					<input type="text" name="author" id="advancedauthor">
					<br>
					<br>
					<input type="button" value="Search" id="advancedsearch" />
					<%
						if (!currentUser.isAuthenticated()) {
					%>
					<label>Please login to purchase</label>
					<%
						}
					%>
				</form:form>
				<br> <br> <br>
				<div id="tablediv">
					<table cellspacing="2" id="countrytable">
						<tr>
							<th scope="col">Book title</th>
							<th scope="col">Author</th>
							<th scope="col">
							<th>
						</tr>
					</table>
					<br>
					<br>
					<table id="cartcontent" style="width: 300px; height: auto;">
						<tr>
							<th field="name" width=140>Name</th>
							<th field="quantity" width=60 align="right">Quantity</th>
							<th field="price" width=60 align="right">Unit Price</th>
						</tr>
					</table>
					<br>
					<br> 
					<%
						if (currentUser.isAuthenticated()) {
					%>
					<form:form id="makePurchase">
						<input type="submit" value="Check out" />
					</form:form>
					<%
						}
					%>

				</div>
			</div>
			<div id="col2">
				<img src="<c:url value="/resources/images/books.jpg"/>" alt=""
					width="260" height="240" class="image-style" />
				<div class="text-style">BOOKS</div>
			</div>
		</div>
	</div>
</body>
</html>