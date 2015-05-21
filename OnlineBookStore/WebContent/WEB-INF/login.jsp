<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>ONLINE BOOK STORE</title>
<link href="http://fonts.googleapis.com/css?family=Arvo" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<c:url value="/resources/styles/styles.css"/>" />
<link rel="stylesheet" href="/resources/styles/style.css" />

<script type="text/javascript" src="jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="jquery/jquery.gallerax-0.2.js"></script>
<style type="text/css">
@import "gallery.css";

</style>
</head>
<body>
<div id="wrapper">
	<div id="header">
		<div id="logo">
			<h1><a href="#">ONLINE BOOK STORE</a></h1>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<ul>
			<li class="current_page_item"><a href="#">Home</a></li>
			<li><a href="#">Book Search</a></li>
			<li><a href="#">GiftCards</a></li>
                        <li><a href="login.html">Login</a></li>
			<li class="last"><a href="Sign Up.html">Sign Up</a></li>
		</ul>
	</div>
	<div id="two-columns">
		<div id="col1">
			<h2>Login: </h2>
<form:form action="authenticate" commandName="user"
				method='post' accept-charset='UTF-8' id="springloginform"><label for="Username">Username</label>
<input type="text" name="name"><br><br><br>
<label for="Password">Password</label>
<input type="password" name="pwd"><br><br><br>
<input type="submit" value="Login"><br><br><br>
</form:form>			
		</div>
		<div id="col2"><img src="images/books.jpg" alt="" width="260" height="240" class="image-style" />
			<div class="text-style">BOOKS</div>
		</div>
	</div>
	</div>
</body>
</html>