<%-- 
    Document   : home
    Created on : 24-giu-2015, 10.29.59
    Author     : Patrik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="page" value="login"/>

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
                    <h4>Ti abbiamo inviato una mail con la tua password.</h4>
                    <h4>Clicca <a href="login.jsp">QUI</a> per effettuare il login.</h4>
                </div>

            </div>    
        </div>

    </body>
</html>
