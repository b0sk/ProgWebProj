<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="page" value="prezzi"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>

    </head>
    <body>
        <div class="container">
            <h1>Prezzi dei biglietti:</h1>

            <c:forEach items="${requestScope.prezzi}" var="prezzo">
                <div class="row">
                    <div class="col-xs-2">
                        <p>${prezzo.tipo}</p>
                    </div>
                    <div class="col-xs-5">
                        <p>${prezzo.prezzo} €</p>
                    </div>
                </div>
            </c:forEach>
        </div>


    </body>
</html>
