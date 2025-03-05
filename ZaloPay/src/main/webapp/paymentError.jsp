<%-- 
    Document   : paymentError
    Created on : Mar 5, 2025, 12:14:20 AM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lỗi thanh toán</title>
    </head>
    <body>
        <h2>Có lỗi xảy ra</h2>
        <p>${error}</p>
        <a href="createOrder">Thử lại</a>
    </body>
</html>
