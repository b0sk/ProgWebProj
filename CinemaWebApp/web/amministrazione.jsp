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
                <div class="col-xs-8">
                    <h3>${utente.email}</h3>
                    <h4>Credito: ${utente.credito} €</h4>

                </div>

                <div class="col-xs-4">
                    <h3><a href="Logout">Logout</a></h3>
                </div>

            </div>

            <br>
            <div class="row">
                <div class="col-md-6 col-xs-10">
                    <h3>Incassi Film:</h3>
                    
                    <table class="table table-striped table-hover table-bordered">
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
                                    <td>${incassoFilm.key}</td>
                                    <td>${incassoFilm.value} €</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>
                            
                </div>
            </div>


        </div>

    </body>
</html>
