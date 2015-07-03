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

        <div class="container">
            <div class="row">
                <div class="col-md-4 col-md-offset-4 col-xs-10 col-xs-offset-1">

                    <form class="form-signin">
                        <h2 class="form-signin-heading">Inserisci la tua email</h2>
                        <label for="inputEmail" class="sr-only">Email address</label>
                        <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                        <br>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Invia password</button>
                    </form>
                
                </div>
            </div>
        </div> <!-- /container -->

    </body>
</html>
