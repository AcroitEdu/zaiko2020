<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
String mode = "入荷";
String caption = "";
%>
<!DOCTYPE html>
<html lang='ja'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
    <title><%=mode%></title>
    <link href="https://unpkg.com/sanitize.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="js/dialog/dialog-polyfill.css" />
    <link href="styleBookInOut.css" rel="stylesheet">
</head>

<body>
    <jsp:include page="part/BookInOutForm.jsp">
        <jsp:param name="mode" value="<%=mode%>" />
        <jsp:param name="book" value="${book }" />
        <jsp:param name="action" value="arrive" />
    </jsp:include>
</body>
<script src="js/dialog/dialog-polyfill.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/ArrivalForm.js"></script>

</html>
