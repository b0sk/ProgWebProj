<%-- 
    Document   : home
    Created on : 24-giu-2015, 10.29.59
    Author     : Patrik
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <div class="col-md-4 col-sm-6">
                <div class="thumbnail">
                    <img src="http://budapesttimes.hu/wp-content/themes/newsroom14/img/placeholder.png" alt="..">
                    <div class="caption">
                        <h3>Thumbnail label</h3>
                        <p>.......</p>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-6">
                <div class="thumbnail">
                    <img src="http://budapesttimes.hu/wp-content/themes/newsroom14/img/placeholder.png" alt="..">
                    <div class="caption">
                        <h3>Thumbnail label</h3>
                        <p>.......</p>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-6">
                <div class="thumbnail">
                    <img src="http://budapesttimes.hu/wp-content/themes/newsroom14/img/placeholder.png" alt="..">
                    <div class="caption">
                        <h3>Thumbnail label</h3>
                        <p>.......</p>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 col-sm-6">
                <div class="thumbnail">
                    <img src="http://budapesttimes.hu/wp-content/themes/newsroom14/img/placeholder.png" alt="..">
                    <div class="caption">
                        <h3>Thumbnail label</h3>
                        <p>.......</p>
                        <p><a href="#" class="btn btn-primary" role="button">Button</a></p>
                    </div>
                </div>
            </div>
            
        </div>
    </body>
</html>
