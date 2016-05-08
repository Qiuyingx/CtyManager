<%@ page language="java" contentType="text/html; charset=UTF-8"	import="com.yard.baidu.ueditor.ActionEnter" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	///usr/Image/ （内网测试图片地址）
	// D:/server/tomcats/tomcat7V2/webapps/ (本地测试图片地址)
	// /usr/local/linju/Image/ （外网正式图片地址）
	// /usr/local/linju/test/Image (外网测试图片地址)
	//"D:/server/tomcats/tomcat7/webapps/";
	
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String rootPath = application.getRealPath( "/" );
	String picRootPath = "D:/server/tomcats/tomcat7/webapps/";
	
	out.write( new ActionEnter( request, rootPath, picRootPath ).exec() );
	
%>