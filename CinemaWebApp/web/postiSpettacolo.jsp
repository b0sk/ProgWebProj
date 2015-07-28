<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="page" value="postiSpettacolo"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>

        <!-- c:set var="film" value="${requestScope.film}" / -->

    </head>
    <body>
        <div class="container">
            <h2>Righe sala: ${requestScope.nRighe} - Colonne sala: ${requestScope.nColonne}</h2>
            <h2>Posti:</h2>
            <c:forEach items="${requestScope.posti}" var="posto">
                <div class="row" <c:if test='${posto.esiste == false}'>style="color: #ff6666"</c:if>>
                    <div class="col-xs-1">
                        Sala ${posto.idSala}
                    </div>
                    <div class="col-xs-1">
                        Posto ${posto.idPosto}
                    </div>
                    <div class="col-xs-3">
                        Colonna ${posto.colonna} - Riga ${posto.riga}
                    </div>
                </div>
            </c:forEach>

            <br>
            
            <h2>Posti Prenotati:</h2>
            <c:forEach items="${requestScope.postiPrenotati}" var="postoPrenotato"> 
                <div class="row">
                    <div class="col-xs-1">
                        Sala ${postoPrenotato.idSala}
                    </div>
                    <div class="col-xs-1">
                        Posto ${postoPrenotato.idPosto}
                    </div>
                    <div class="col-xs-3">
                        Colonna ${postoPrenotato.colonna} - Riga ${postoPrenotato.riga}
                    </div>
                </div>
            </c:forEach>


        </div>
    </body>
</html>
