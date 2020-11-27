<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
//書籍の情報を表形式で表示
//ArrivalForm.jsp・ShippingForm.jsp・Result.jsp・AddCheck.jsp・EditCheck.jsp・DeleteChek.jsp
//上記ファイルがincludeして使用
//caption変数の値をcaption要素として表示

//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年'MM'月'dd'日'");
%>

<!--PC画面表示-->
<table id="pcDisplay">
	<caption><%=request.getParameter("caption")%></caption>
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
		<th scope="row">発売日</th>
		<td><%=((Book) session.getAttribute("book")).getSalesDate().format(dateFormat)%>
		</td>
	</tr>
	<tr>
		<th scope="row">ISBN</th>
		<td>${book.isbn}</td>
	</tr>
	<tr>
		<th scope="row">価格</th>
		<td>${book.price} 円</td>
	</tr>
	<tr>
		<th scope="row">在庫数</th>
		<td>${book.stock} 冊</td>
	</tr>
</table>
<!--PC画面表示ここまで-->


<!--スマホ画面表示-->
<!---->
<table id="sumahoDisplay">
	<caption><%=request.getParameter("caption")%></caption>
	<colgroup>
		<col class="table-header">
		<col class="table-content">
		<col class="table-header">
		<col class="table-content">
	</colgroup>
	<tr>
		<th>書籍名</th>
		<td colspan="3">${book.name}</td>
	</tr>
	<tr>
		<th>著者</th>
		<td colspan="3">${book.author}</td>
	</tr>
	<tr>
		<th>出版社</th>
		<td colspan="3">${book.publisher}</td>
	</tr>
	<tr>
		<th>発売日</th>
		<td colspan="3"><%=((Book) session.getAttribute("book")).getSalesDate().format(dateFormat)%>
		</td>
	</tr>
	<tr>
		<th>ISBN</th>
		<td colspan="3">${book.isbn}</td>
	</tr>
	<tr>
		<th>価格</th>
		<td>${book.price} 円</td>
		<th>在庫数</th>
		<td>${book.stock} 冊</td>
	</tr>
</table>
<!--スマホ画面表示ここまで-->