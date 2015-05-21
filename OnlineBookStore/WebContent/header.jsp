<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.subject.Subject"%>

<%@page import="org.apache.shiro.session.mgt.SessionContext"%>
<%@page import="org.apache.shiro.session.*"%>
<%
	/* ApplicationContext context = RequestContextUtils.getWebApplicationContext(request);

	RESTUtil restUtil = (RESTUtil) context.getBean("restUtil");
	
	List<BusinessParameterDTO> businessParameter = (List<BusinessParameterDTO>) restUtil
	.getData(EFMSWebConstants.Common.BUSINESS_PARAMETER, BusinessParameterDTOList.class);

	pageContext.setAttribute("businessParameter", businessParameter);  */
		
%>
<div id="menu">
		<ul>
			<li class="current_page_item"><a href="/OnlineBookStore/index">Home</a></li>
			<li><a href="/OnlineBookStore/booksearch">Book Search</a></li>
			<li><a href="/OnlineBookStore/giftcard">GiftCards</a></li>
			<%
			Subject currentUser = SecurityUtils.getSubject();
			Session userSession=currentUser.getSession();
		if (!currentUser.isAuthenticated()) {
%>
                        <li><a href="/OnlineBookStore/login">Login</a></li>
			<li class="last"><a href="/OnlineBookStore/signup">Sign Up</a></li>
		</ul>
		<%}
		else{
			%>
						<li><a href="cart">cart</a></li>
						<!-- <li><a href="giftcard">GiftCards</a></li> -->
			
			<%
		}
		%>
	</div>
	
	