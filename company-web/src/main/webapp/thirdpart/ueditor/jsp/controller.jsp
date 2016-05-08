<%@ page language="java" contentType="text/html; charset=UTF-8"	import="com.yard.baidu.ueditor.ActionEnter" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String rootPath = application.getRealPath( "/" );
	String picRootPath = "D:/server/tomcats/tomcat7/webapps/";
	
	out.write( new ActionEnter( request, rootPath, picRootPath ).exec() );
	
%>