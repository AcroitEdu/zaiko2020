<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
%>
<!DOCTYPE html>
<html lang='ja'>

<head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
    <title>処理完了</title>
    <link href="https://unpkg.com/sanitize.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link href="styleBookInOut.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="js/dialog/dialog-polyfill.css" />
</head>

<body>
    <div id="main">
        <header>
            <span id="complete">処理を実行しました。</span>
        </header>
        <div id="error">
            <span>${sessionScope.error}</span>
        </div>
        <div id="details">
            <jsp:include page="BookDetails.jsp">
                <jsp:param value="caption" name="更新後の書籍情報" />
                <jsp:param value="book" name="${book }" />
            </jsp:include>
        </div>
        <div id="inout">
            <form id="inoutForm" action="/Zaiko2020/inventoryList" method="post">
                <input type="hidden" name="form" value="4">
                <input type="submit" id="execute" class="button button-main button-border" value="OK">
            </form>
        </div>
    </div>
</body>
<script src="js/dialog/dialog-polyfill.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/ResultForm.js"></script>

</html>
