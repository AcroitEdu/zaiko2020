<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
//書籍名や在庫数等の、本の情報を表示する。
//ArrivalForm.jspやShippingForm.jsp、Result.jspからincludeして使う。
//caption変数の値をcaption要素として表示する。
%>
<table>
    <caption>${caption }</caption>
    <colgroup>
        <col class="table-header">
        <col class="table-content">
    </colgroup>
    <tr>
        <th scope="row">書籍名</th>
        <td>${book.name}</td>
    </tr>
    <tr>
        <th scope="row">著者</th>
        <td>${book.author}</td>
    </tr>
    <tr>
        <th scope="row">出版社</th>
        <td>${book.publisher}</td>
    </tr>
    <tr>
        <th scope="row">ISBN</th>
        <td>${book.isbn}</td>
    </tr>
    <tr>
        <th scope="row">発売日</th>
        <td>
            <fmt:formatDate value="${book.salesDate.format(dateFormat)}" pattern="YYYY'年'MM'月'dd'日'" />
        </td>
    </tr>
    <tr>
        <th scope="row">価格</th>
        <td>${book.price}</td>
    </tr>
    <tr>
        <th scope="row">在庫数</th>
        <td>${book.stock}</td>
    </tr>
</table>
