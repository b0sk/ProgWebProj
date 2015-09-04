<%-- 
    Document   : home
    Created on : 24-giu-2015, 10.29.59
    Author     : Patrik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="page" value="altro"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>


    </head>
    <body>
        <div class="container" style="padding-top: 5%">
            <div class="row">
                <div class="col-md-8 col-md-offset-2 col-xs-8 col-xs-offset-2" style="text-align: center;">
                    <c:choose>
                        <c:when test="${requestScope.succes == 1}">
                            <h3>Prenotazione effettuata con successo.</h3>
                            <h4>Riceverai un e-mail con i dettagli.</h4>
                        </c:when>

                        <c:otherwise>
                            <h3>Si è verificato un errore nella prenotazione.</h3>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <h4>Clicca <a href="Home">QUI</a> per tornare alla Home.</h4>
                </div>

            </div>    
        </div>

    </body>
</html>
