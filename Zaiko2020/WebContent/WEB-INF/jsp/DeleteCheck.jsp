<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
//書籍名や在庫数等の、本の情報を表示する。
//ArrivalForm.jspやShippingForm.jsp、Result.jspからincludeして使う。
//caption変数の値をcaption要素として表示する。
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年'MM'月'dd'日'");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
	<title>書籍削除確認</title>
	<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="js/dialog/dialog-polyfill.css" />
    <link href="styleBookInOut.css" rel="stylesheet">
    <link href="styleCheck.css" rel="stylesheet">
</head>
<body>
	<div id="main">
		<header>
			<span id="complete">以下の書籍を削除いたします。本当によろしいですか？</span>
		</header>
		<div id="error">
			<span>${sessionScope.error}</span>
		</div>
		<div id="details">
		<table>
<%--     <caption><%=request.getParameter("caption")%></caption> --%>
    <colgroup>
        <col class="table-header">
        <col class="table-content">
    </colgroup>
    <tr>
        <th scope="row">書籍名</th>
        <td>${deleteBook.name}</td>
    </tr>
    <tr>
        <th scope="row">著者</th>
        <td>${deleteBook.author}</td>
    </tr>
    <tr>
        <th scope="row">出版社</th>
        <td>${deleteBook.publisher}</td>
    </tr>
    <tr>
        <th scope="row">ISBN</th>
        <td>${deleteBook.isbn}</td>
    </tr>
    <tr>
        <th scope="row">発売日</th>
        <td>
            <%=((Book)session.getAttribute("deleteBook")).getSalesDate().format(dateFormat)%>
        </td>
    </tr>
    <tr>
        <th scope="row">価格</th>
        <td>${deleteBook.price} 円</td>
    </tr>
    <tr>
        <th scope="row">在庫数</th>
        <td>${deleteBook.stock} 冊</td>
    </tr>
</table>
<%-- 			<jsp:include page="part/BookDetails.jsp"> --%>
<%-- 				<jsp:param name="caption" value=" " /> --%>
<%-- 				<jsp:param name="book" value="${book}" /> --%>
<%-- 			</jsp:include> --%>
		</div>
		<div id="buttons">
			<form id="deleteForm" action="/Zaiko2020/DeleteProcess" method="post">
				<input type="hidden" name="id" value="${book.id}">
				<input type="submit" name="button" id="delete" class="button button-warning button-border" value="実行">
			</form>
			<form id="cancelForm" action="/Zaiko2020//Edit" method="post">
				<input type="submit" name="button" id="cancel" class="button button-cancel button-border" value="キャンセル">
			</form>
		</div>
    </div>
</body>
<script src="js/dialog/dialog-polyfill.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</html>