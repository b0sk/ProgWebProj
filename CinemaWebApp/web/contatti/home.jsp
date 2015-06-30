<%-- 
    Document   : contatti
    Created on : 24-giu-2015, 18.56.32
    Author     : Fabio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="page" value="contatti"/>

<script type="text/javascript">

function initialize() {

var map = new google.maps.Map(document.getElementById("map-canvas"), mapOptions);

// Resize stuff...
google.maps.event.addDomListener(window, "resize", function() {
   var center = map.getCenter();
   google.maps.event.trigger(map, "resize");
   map.setCenter(center); 
});

</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%@ include file="../include/header.jsp" %>
        
        
    </head>
    <body>
        <center><h1>CONTATTI</h1>
            <br>
            <p>498-367-2985</p>
            <p>498-865-7358</p>
            <br>
            <p>3883 Howard Hughes Pkwy, Las Vegas, NV 89169</p>
            <br>
            <p><a href="mailto:emailaddress">cinema@email.com</a></p>
            <br>
            <br>
            <h3>Orari:</h3>
            <p>Tutti i giorni: 12.00 – 24.00 (UTC/GMT -7)</p>
            <br>
            <br>
            <h3>Seguici su:</h3>
            <p><a href="home.jsp"><img border="0" alt="W3Schools" src="img/fb-logo.png" width="200" height="200"></a>
               <a href="home.jsp"><img border="0" alt="W3Schools" src="img/t-logo.png" width="200" height="200"></a>
            </p>
            <br>
            <br>
                <div class="map">
                    <iframe src="https://www.google.com/maps/embed?pb=!1m21!1m12!1m3!1d3222.9620535689755!2d-115.1588911034126!3d36.11878221149257!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!4m6!3e6!4m0!4m3!3m2!1d36.118621499999996!2d-115.1586564!5e0!3m2!1sit!2sit!4v1435165457251" width="80%" height="60%" frameborder="0" style="border:1" allowfullscreen></iframe>
                </div>
        </center>
    </body>
</html>
