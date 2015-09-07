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

        <div class="container">
            <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1" id="erroreCampi">
                <h2 style="color: #ff6666">Si è verificato un errore.</h2>

                <c:if test='${requestScope.errorMessage != null}'>
                    <h3 style="color: #ff6666">${requestScope.errorMessage}</h3>
                </c:if>


                <br>
                <br>

                <c:if test='${requestScope.exception != null}'>
                    <p>-------------</p>
                    <p>${requestScope.exception}</p>
                    <p>-------------</p>
                </c:if>

            </div>
        </div>


        <%-- Include the footer --%>
        <%@ include file="include/footer.jsp" %>
    </body>
</html>
