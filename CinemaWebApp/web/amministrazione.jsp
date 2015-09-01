<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="page" value="amministrazione"/>

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
                    <h4>Credito: ${utente.credito} €</h4>

                </div>

                <div class="col-sm-2 col-xs-2 pull-rigt">
                    <h3><a href="Logout">Logout</a></h3>
                </div>

            </div>

            <br>

            <div class="row">
                <div class="col-md-6 col-xs-9">
                    <h3>Incassi Film:</h3>

                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Film</th>
                                <th>Incasso</th>
                            </tr>    
                        </thead>
                        <tbody>
                            <!--tr>
                                <td>forrest gump</td>
                                <td>${requestScope.incasso} €</td>
                            </tr-->
                            <c:forEach items="${requestScope.incassiFilm}" var="incassoFilm">                
                                <tr>
                                    <td class="col-md-3">${incassoFilm.key}</td>
                                    <td class="col-md-2">${incassoFilm.value} €</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>
            </div>

            <br>

            <div class="row">
                <div class="col-md-6 col-xs-9">
                    <h3>Top Users:</h3>

                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Email</th>
                                <th>Incasso generato</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.top10Users}" var="topUser" varStatus="myIndex">                
                                <tr>
                                    <th class="col-md-1">${myIndex.index + 1}</th>
                                    <td class="col-md-3">${topUser.key}</td>
                                    <td class="col-md-2">${topUser.value} €</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>
            </div>

            <br>

            <div class="row">
                <div class="col-md-6 col-xs-9">
                    <h3>Prenotazioni attive:</h3>

                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Email</th>
                                <th>Film</th>
                                <th>Data e ora spettacolo</th>
                                <th>Posto</th>
                                <th>Prezzo</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.prenotazioniA}" var="prenotazione" varStatus="myIndex">                
                                <tr>
                                    <td class="col-md-1">${requestScope.utentiPA.get(myIndex.index).getEmail()}</td> <!-- prenotazione.idUtente -->
                                    <td class="col-md-3">${requestScope.filmPA.get(myIndex.index).getTitolo()}</td> <!-- prenotazione.idUtente -->
                                    <td class="col-md-3"><fmt:formatDate value="${requestScope.spettacoliPA.get(myIndex.index).getDataOra()}" type="BOTH" dateStyle="LONG" timeStyle="SHORT" /></td> <!-- prenotazione.idSpettacolo -->
                                    <td class="col-md-1">${prenotazione.idPosto}</td>
                                    <td class="col-md-2">${requestScope.prezziPA.get(myIndex.index).getPrezzo()} €</td> <!-- prenotazione.idPrezzo -->
                                    <td class="col-md-2"><a href="AmministrazioneServlet?idPrenotazioneCanc=${prenotazione.idPrenotazione}">Cancella</a></td> <!-- prenotazione.idPrezzo -->
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>
            </div>
            
            <br>

            <div class="row">
                <div class="col-md-6 col-xs-9">
                    <h3>Spettacoli attivi:</h3>

                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Film:</th>
                                <th>Data e ora:</th>
                                <th>Numero posti prenotati</th>
                                <th>Incasso</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.spettacoliAtt}" var="spettacolo" varStatus="i">                
                                <tr>
                                    <td class="col-md-4">${requestScope.filmSpettacoliAtt.get(i.index).getTitolo()}</td>
                                    <td class="col-md-3"><fmt:formatDate value="${spettacolo.getDataOra()}" type="BOTH" dateStyle="LONG" timeStyle="SHORT" /></td>
                                    <td class="col-md-2">${requestScope.nPrenotazioniSpettAtt.get(i.index)}</td>
                                    <td class="col-md-1">${requestScope.incassiSpettacoliAtt.get(i.index)} €</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>
            </div>
            
        </div>

    </div>                    

</body>
</html>
