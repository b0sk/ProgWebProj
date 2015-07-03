<%-- 
    Document   : home
    Created on : 24-giu-2015, 10.29.59
    Author     : Patrik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="page" value="home"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="include/header.jsp" %>
        
        
    </head>
    <body>
        <h1>Film disponibili:</h1>
        
        <!-- Film Box --> 
        <div class="row">
            <c:forEach items="${requestScope.films}" var="film">                
                <div class="col-md-4 col-sm-6">
                    <div class="thumbnail">
                        <!--img src="http://budapesttimes.hu/wp-content/themes/newsroom14/img/placeholder.png" alt=""-->
                        <img src="${film.uriLocandina}" alt="" style="height: 500px">
                        <div class="caption">
                            <h3><c:out value="${film.titolo}"></c:out></h3>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        
    </body>
</html>
