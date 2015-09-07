<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--c:set var="page" value="postiSpettacolo"/--%>
<c:set var="page" value="altro"/>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>

        <!-- c:set var="film" value="${requestScope.film}" / -->

        <style>
            .posto {
                text-align: center;
                border: 1px solid #525252;
                border-radius: 3px;
                display: inline-block;
                width: 75%;
                height: 100%;
                min-height: 30px;
                min-width: 20px;
            }
            .posto-l {
                background-color: #00DB42;
            }
            .posto-l:hover{
                opacity: 0.6;
                cursor: pointer;
            }
            .posto-x {
                background-color: #fff;
                border: 0px;
            }
            .posto-p {
                background-color: #FF3300;
            }
            .posto-selected {
                background-color: #009DFF;
                opacity: 0.5;
            }
            .posto-selected:hover {
                cursor: pointer;
            }

            .posto-l-ex {
                min-height: 25px;
                min-width: 25px;
                background-color: #00DB42;
            }
            .posto-p-ex {
                min-height: 25px;
                min-width: 25px;
                background-color: #FF3300;
            }
            .posto-selected-ex {
                min-height: 25px;
                min-width: 25px;
                background-color: #009DFF;
                opacity: 0.5;
            }


            li.li-posto {
                padding: 5px;
                list-style-type: none;
            }
            ul.posti-selezionati {
                padding: 7px;
            }

            .schermo{
                text-align: center;
                color: white;
                border: 1px solid black;
                background-color: #525252;
                opacity: 0.8;
            }
        </style>



    </head>
    <body>
        <div class="container">
            <%-- <h2>Righe sala: ${requestScope.nRighe} - Colonne sala: ${requestScope.nColonne}</h2> --%>

            <h2>Seleziona i posti:</h2>

            <div class="row">
                <div class="col-xs-10 col-xs-offset-1 col-sm-5 col-sm-offset-1 schermo">Schermo</div>
            </div>
            <div class="row">
                <!-- Mappa dei posti -->
                <div class="col-sm-7 col-xs-12">
                    <table class="table">
                        <c:forEach var="i" begin="0" end="${requestScope.nRighe - 1}">
                            <tr>
                                <c:forEach var="j" begin="0" end="${requestScope.nColonne - 1}">
                                    <td>
                                        <c:if test="${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j]) == 'L'}">
                                            <div class="posto posto-l" id="${requestScope.mappaPosti[i][j]}">${requestScope.mappaPosti[i][j]}</div>
                                        </c:if>
                                        <c:if test="${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j]) == 'P'}">
                                            <div class="posto posto-p" id="${requestScope.mappaPosti[i][j]}">${requestScope.mappaPosti[i][j]}</div>
                                        </c:if>
                                        <c:if test="${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j]) == 'X'}">
                                            <div class="posto posto-x" id="${requestScope.mappaPosti[i][j]}"></div>
                                        </c:if>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>

                    <!-- LEGENDA -->
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-12"><h3>Legenda</h3></div>
                        </div>
                        <div class="row">
                            <div class="col-xs-2">Posto libero:</div>
                            <div class="col-xs-1"><div class="posto posto-l-ex"></div></div>
                            <div class="col-xs-2">Posto occupato:</div>
                            <div class="col-xs-1"><div class="posto posto-p-ex"></div></div>
                            <div class="col-xs-2">Posto selezionato:</div>
                            <div class="col-xs-1"><div class="posto posto-selected-ex"></div></div>
                        </div>
                    </div>

                </div>

                <!-- Lista dei posti selezionati -->
                <div class="col-sm-5 col-xs-12" id="containerSelezioni"  style="border: 1px solid black; border-radius: 5px; padding: 5px;">
                    <form method="POST" action="RiepilogoPrenotazioneServlet?idSpettacolo=${param["idSpettacolo"]}">
                        <ul class="posti-selezionati">
                            <%-- Esempio di li-posto %--%>
                            <%--li class="li-posto" id="p0">
                                <input type="hidden" name="id-posto" value="0" />
                                <div class="row">
                                    <div class="col-sm-3"><p>Posto 0</p></div>
                                    <div class="col-sm-9">
                                        <select class="form-control" name="tipo-biglietto">
                                            <c:forEach var="prezzo" items="${requestScope.prezzi}">
                                                <option <c:if test="${prezzo.tipo == 'normale'}">selected</c:if> value="${prezzo.idPrezzo}">${prezzo.tipo} ${prezzo.prezzo}€</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </li--%>      

                        </ul>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button id="btnSubmit" type="submit" class="btn btn-default" disabled>Prenota</button>
                            </div>
                        </div>
                    </form>
                </div>

            </div>

        </div>

        <%-- Include the footer --%>
        <%@ include file="include/footer.jsp" %>

        <script type="text/javascript">
            // click su un posto libero
            $(document.body).on('click', '.posto-l', function () {
                $(this).addClass("posto-selected").removeClass("posto-l");
                var id = $(this).attr("id");

                var postoLI = $("<li></li>").addClass('li-posto').attr("id", "p" + id);
                postoLI.append('<input type="hidden" name="id-posto" value="' + id + '" />');

                var contentRow = $("<div></div>").addClass('row');
                contentRow.append('<div class="col-sm-3"><p>Posto ' + id + '</p></div>');
                var selectDiv = $("<div></div>").addClass("col-sm-9");
                var select = $("<select></select>").addClass("form-control").attr("name", "tipo-biglietto");
                select.append(
            <c:forEach var="prezzo" items="${requestScope.prezzi}">
                '<option' <c:if test="${prezzo.tipo == 'normale'}"> + ' selected'</c:if> + ' value = "${prezzo.idPrezzo}" >${prezzo.tipo} ${prezzo.prezzo}€ </option>' +
            </c:forEach>
                ''
                        );
                        selectDiv.append(select);
                contentRow.append(selectDiv);

                postoLI.append(contentRow);
                $(".posti-selezionati").append(postoLI);

                var submitButton = $("#btnSubmit");
                submitButton.attr("disabled", false);


            }).on('click', '.posto-selected', function () { // Click su un posto selezionato
                $("#p" + $(this).attr("id")).remove();
                $(this).addClass("posto-l").removeClass("posto-selected");

                var containerSelezioni = $("#containerSelezioni");
                var submitButton = $("#btnSubmit");
                if (!(containerSelezioni.has('.li-posto').length)) {
                    submitButton.attr("disabled", true);
                }
            });

        </script>

    </body>
</html>
