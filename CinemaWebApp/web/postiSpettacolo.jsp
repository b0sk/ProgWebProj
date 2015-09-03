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

        <script src="js/jQuery/jquery-2.1.4.js"></script>

        <style>
            .posto {
                text-align: center;
                border: 1px solid #525252;
                border-radius: 3px;
                display: inline-block;
                width: 100%;
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
            .posto-selected{
                background-color: #009DFF;
                opacity: 0.5;
            }
        </style>

        <script type="text/javascript">
            $(document.body).on('click', '.posto-l', function () {
                $(this).addClass("posto-selected").removeClass("posto-l");
                $(".posti-selezionati").append('<li id="p' + $(this).attr("id") + '">Posto: ' + $(this).attr("id") + "</li>");
                //console.log("asd");
            }).on('click', '.posto-selected', function () {
                $("#p" + $(this).attr("id")).remove();
                $(this).addClass("posto-l").removeClass("posto-selected");
                //console.log("lol");
            });
/*
            $(document).ready(function () {
                 $(".posto-l").on("click", function () {
                 //$(this).toggleClass("posto-selected");
                 $(this).addClass("posto-selected").removeClass("posto-l");
                 $(".posti-selezionati").append('<li id="p' + $(this).attr("id") + '">Posto: ' + $(this).attr("id") + "</li>");
                 console.log("asd");
                 });
                 $(".posto-selected").on("click", function () {
                 //$(this).toggleClass("posto-selected");
                 $("#p"+$(this).attr("id")).remove();
                 $(this).addClass("posto-l").removeClass("posto-selected");
                 console.log("lol");
                 });
            });
*/
        </script>

    </head>
    <body>
        <div class="container">
            <h2>Righe sala: ${requestScope.nRighe} - Colonne sala: ${requestScope.nColonne}</h2>

            <h2>Posti:</h2>
            <!-- c:forEach items="${requestScope.asd}" var="postiAsd" varStatus="index" -->
                <!--p>${postiAsd.key.idPosto} - ${postiAsd.value}</p-->
            <!-- /c:forEach -->
            <div class="row">
                <!-- Mappa dei posti -->
                <div class="col-sm-7">
                    <table class="table">
                        <c:forEach var="i" begin="0" end="${requestScope.nRighe - 1}">
                            <tr>
                                <c:forEach var="j" begin="0" end="${requestScope.nColonne - 1}">
                                    <td>
                                        <c:if test="${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j]) == 'L'}">
                                            <div class="posto posto-l" id="${requestScope.mappaPosti[i][j]}">${requestScope.mappaPosti[i][j]}</div>
                                            <!-- button type="button" style="background-color: greenyellow"> <p></p> </button -->
                                        </c:if>
                                        <c:if test="${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j]) == 'P'}">
                                            <div class="posto posto-p" id="${requestScope.mappaPosti[i][j]}">${requestScope.mappaPosti[i][j]}</div>
                                            <!--button type="button" disabled style="background-color: red"> <p></p> </button-->
                                        </c:if>
                                        <c:if test="${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j]) == 'X'}">
                                            <div class="posto posto-x" id="${requestScope.mappaPosti[i][j]}"></div>
                                            <!-- button type="button" disabled style="background-color: white"> <p></p> </button -->
                                        </c:if>
                                    </td>
                                     <!-- ${requestScope.mappaPosti[i][j]}:${requestScope.hmapPosti.get(requestScope.mappaPosti[i][j])} - --> 
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <!-- Lista dei posti selezionati -->
                <div class="col-sm-5" style="border: 1px solid black;">
                    <form>
                        <ul class="posti-selezionati">
                        </ul>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">Prenota</button>
                            </div>
                        </div>
                    </form>

                </div>

            </div>
        </div>
    </body>
</html>
