<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="servlet.PaginaUtenteServlet" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="page" value="mioaccount"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>

        <c:set var="utente" value="${sessionScope.utente}" />

    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-10 col-xs-7">
                    <h3>${utente.email}</h3>
                    <h4>Credito: <fmt:formatNumber value="${utente.credito}" minFractionDigits="2"/> &euro;</h4>

                </div>

                <div class="col-sm-2 col-xs-2 pull-rigt">
                    <h3><a href="Logout">Logout</a></h3>
                </div>
            </div>

            <br>

            <h3>Lista prenotazioni:</h3>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>Film</th>
                        <th>Sala</th>
                        <th>Posto</th>
                        <th>Data ora</th>
                        <th>Prezzo</th>
                    </tr>    
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.prenotazioniUtente}" var="prenotazioneUtente">                
                        <tr>
                            <td>${prenotazioneUtente.titoloFilm}</td>
                            <td>${prenotazioneUtente.sala}</td>
                            <td>${prenotazioneUtente.idPosto}</td>
                            <td><fmt:formatDate value="${prenotazioneUtente.dataOra}" type="BOTH" dateStyle="LONG" timeStyle="SHORT" /></td>
                            <td>${prenotazioneUtente.prezzo} €</td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>

        <%-- Include the footer --%>
        <%@ include file="include/footer.jsp" %>

    </body>
</html>
