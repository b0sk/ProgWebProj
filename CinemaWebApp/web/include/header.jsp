<%-- 
    Document   : header
    Created on : 24-giu-2015, 11.24.31
    Author     : Patrik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>Cinema WebApp - 
<c:choose>
    <c:when test='${page == "home"}'>Home</c:when>
    <c:when test='${page == "prezzi"}'>Prezzi</c:when>
    <c:when test='${page == "contatti"}'>Contatti</c:when>
    <c:when test='${page == "login"}'>Login</c:when>
</c:choose>
</title>

<!-- Bootstrap core CSS -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Optional Bootstrap theme -->
<link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="../bootstrap/css/bootstrap-theme.min.css">


<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Brand</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">

            <c:if test='${pageScope.page == "home"}'>
                <li class="active"><a href="#">Home<span class="sr-only">(current)</span></a></li>
                <li><a href="#">Prezzi</a></li>
                <li><a href="#">Contatti</a></li>
                <li><a href="#">Login</a></li>
            </c:if>

            <c:if test='${pageScope.page == "prezzi"}'>
                <li><a href="#">Home</a></li>
                <li class="active"><a href="#">Prezzi<span class="sr-only">(current)</span></a></li>
                <li><a href="#">Contatti</a></li>
                <li><a href="#">Login</a></li>
            </c:if>
                
            <c:if test='${pageScope.page == "contatti"}'>
                <li><a href="#">Home</a></li>
                <li><a href="#">Prezzi</a></li>
                <li class="active"><a href="#">Contatti<span class="sr-only">(current)</span></a></li>
                <li><a href="#">Login</a></li>
            </c:if>

            <c:if test='${pageScope.page == "login"}'>
                <li><a href="#">Home</a></li>
                <li><a href="#">Prezzi</a></li>
                <li><a href="#">Contatti</a></li>
                <li class="active"><a href="#">Login<span class="sr-only">(current)</span></a></li>
            </c:if>
            
        </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

