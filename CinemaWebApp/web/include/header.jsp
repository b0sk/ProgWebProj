<!-- TODO: fixare link Prezzi, Il mio account e Amministrazione -->

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
            <a class="navbar-brand" href="Home">CinemaWebApp</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">

                <!-- Differenzia il menu per utenti loggati/amministratori e utenti non-loggati -->
                <c:choose>
                    <c:when test="${sessionScope.utente.getIdRuolo() == 2}">
                    <!-- Menu per utente loggato -->
                        <p>${sessionScope.utente.getEmail()}</p>
                        <c:if test='${pageScope.page == "home"}'>
                            <li class="active"><a href="Home">Home<span class="sr-only">(current)</span></a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li><a href="#">Il mio account</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "prezzi"}'>
                            <li><a href="Home">Home</a></li>
                            <li class="active"><a href="PrezziServlet">Prezzi<span class="sr-only">(current)</span></a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li><a href="#">Il mio account</a></li>
                            </c:if>

                        <c:if test='${pageScope.page == "contatti"}'>
                            <li><a href="Home">Home</a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li class="active"><a href="contatti.jsp">Contatti<span class="sr-only">(current)</span></a></li>
                            <li><a href="#">Il mio account</a></li>
                            </c:if>

                        <c:if test='${pageScope.page == "mioaccount"}'>
                            <li><a href="Home">Home</a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li class="active"><a href="#">Il mio account<span class="sr-only">(current)</span></a></li>
                        </c:if>
                    </c:when>
                    
                    <c:when test="${sessionScope.utente.getIdRuolo() == 1}">
                    <!-- Menu per utente amministratore -->
                        <p>${sessionScope.utente.getEmail()}</p>
                        <c:if test='${pageScope.page == "home"}'>
                            <li class="active"><a href="Home">Home<span class="sr-only">(current)</span></a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li><a href="#">Amministrazione</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "prezzi"}'>
                            <li><a href="Home">Home</a></li>
                            <li class="active"><a href="PrezziServlet">Prezzi<span class="sr-only">(current)</span></a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li><a href="#">Amministrazione</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "contatti"}'>
                            <li><a href="Home">Home</a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li class="active"><a href="contatti.jsp">Contatti<span class="sr-only">(current)</span></a></li>
                            <li><a href="#">Amministrazione</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "mioaccount"}'>
                            <li><a href="Home">Home</a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li class="active"><a href="#">Amministrazione<span class="sr-only">(current)</span></a></li>
                        </c:if>
                    </c:when>
                    
                    <c:otherwise>
                    <!-- Menu per utente non loggato -->
                        <c:if test='${pageScope.page == "home"}'>
                            <li class="active"><a href="Home">Home<span class="sr-only">(current)</span></a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li><a href="login.jsp">Login</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "prezzi"}'>
                            <li><a href="Home">Home</a></li>
                            <li class="active"><a href="PrezziServlet">Prezzi<span class="sr-only">(current)</span></a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li><a href="login.jsp">Login</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "contatti"}'>
                            <li><a href="Home">Home</a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li class="active"><a href="contatti.jsp">Contatti<span class="sr-only">(current)</span></a></li>
                            <li><a href="login.jsp">Login</a></li>
                        </c:if>

                        <c:if test='${pageScope.page == "login"}'>
                            <li><a href="Home">Home</a></li>
                            <li><a href="PrezziServlet">Prezzi</a></li>
                            <li><a href="contatti.jsp">Contatti</a></li>
                            <li class="active"><a href="login.jsp">Login<span class="sr-only">(current)</span></a></li>
                        </c:if>
                    </c:otherwise>
                </c:choose>


            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

