<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--c:set var="page" value="riepilogoPrenotazione"/--%>
<c:set var="page" value="altro"/>


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
                <div class="col-sm-4 col-sm-offset-4 col-xs-9 col-xs-offset-1">
                    <h2>Conferma</h2>
                    <c:forEach var="elemento" items="${sessionScope.carrello}">
                        <p>Posto ${elemento.key} - Tipo biglietto: ${elemento.value.tipo} ${elemento.value.prezzo}€ </p>
                    </c:forEach>

                    <h4>Totale: ${requestScope.prezzoTotale} €</h4>

                    <form method="POST" action="CheckoutServlet">
                        <h3>Metodo di pagamento:</h3>
                        <div class="radio">
                            <label>
                                <input type="radio" name="tipoPagamento" id="optionsRadios1" value="cartaCredito" checked>
                                Carta di credito
                            </label>

                            <input type="text" class="form-control" name="nrCarta" placeholder="Numero di carta" pattern="[0-9]{13,16}" title="Formato carta non valido" />
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="tipoPagamento" id="optionsRadios2" value="creditoUtente">
                                Credito utente (${sessionScope.utente.credito} €)
                            </label> 
                        </div>
                        <input class="btn btn-primary" type="submit" value="Conferma"/>
                        <a href="CheckoutServlet?annulla=1" class="btn btn-danger" role="button">Annulla</a>


                    </form>

                </div>
            </div>

        </div>
        <%--
                <!-- include jQuery -->
                <script src="js/jQuery/jquery-2.1.4.js"></script>
                <!-- Latest compiled and minified JavaScript -->
                <script src="bootstrap/js/bootstrap.min.js"></script>
        --%>
    </body>
</html>
