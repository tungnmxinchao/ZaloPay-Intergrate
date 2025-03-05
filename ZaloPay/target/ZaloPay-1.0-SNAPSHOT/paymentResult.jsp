<%-- 
    Document   : paymentResult
    Created on : Mar 4, 2025, 11:53:30 PM
    Author     : TNO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Kết quả thanh toán</title>
    </head>
    <body>
        <h2>Kết quả thanh toán</h2>
        <p>Kết quả từ ZaloPay:</p>
        <pre>${result}</pre>
        <p>Link thanh toán:</p>
        <a href="${orderUrl}" target="_blank">${orderUrl}</a>
        <br><br>
        <a href="createOrder">Quay lại</a>
    </body>
</html>