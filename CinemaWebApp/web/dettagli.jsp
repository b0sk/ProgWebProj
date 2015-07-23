<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="page" value="dettagli"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>

        <c:set var="film" value="${requestScope.film}" />
        <c:set var="genere" value="${requestScope.genere}" />
        
    </head>
    <body>
        <div class="container">
            <h1>${film.titolo}</h1>
            <h4>${genere.descrizione} - ${film.durata} min</h4>


            <div class="row">
                <div class="col-md-6 col-xs-12">
                    <p>${film.trama}</p>
                </div>

                <div class="col-md-6 col-sm-8 col-xs-12">
                    <div class="embed-responsive embed-responsive-16by9">
                        <iframe class="embed-responsive-item"
                                src="${film.urlTrailer}" allowfullscreen>
                        </iframe>
                    </div> 
                </div>
            </div>

            <h2>Spettacoli:</h2>
            <c:forEach items="${requestScope.spettacoli}" var="spettacolo"> 
                <div class="row">
                    <div class="col-xs-2">
                        <p><fmt:formatDate value="${spettacolo.dataOra}" type="BOTH" dateStyle="LONG" timeStyle="SHORT" /></p>
                    </div>
                    <div class="col-xs-1">
                        <p>Sala ${spettacolo.idSala}</p>
                    </div>
                    <div class="col-xs-1">
                        <a href="">Prenota!</a>
                    </div>
                </div>
            </c:forEach>

        </div>
    </body>
</html>
