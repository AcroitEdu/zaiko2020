<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍削除確認</title>

</head>
<body>
	<h1>以下の書籍を削除いたします。本当によろしいですか？</h1>
	<table>
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
			<th scope="row">ISBN</th>
			<td>${book.isbn}</td>
		</tr>
		<tr>
			<th scope="row">発売日</th>
			<td>${book.salesDate}</td>
		</tr>
		<tr>
			<th scope="row">価格</th>
			<td>${book.price}円</td>
		</tr>
		<tr>
			<th scope="row">在庫数</th>
			<td>${book.stock}冊</td>
		</tr>
	</table>
	<span>
		<form id="deleteForm" action="/Zaiko2020/DeleteProcess" method="post">
			<input type="hidden" name="id" value="${book.id}">
			<input type="submit" name="button" id="delete" class="button button-main button-border" value="実行">
		</form>
		<form id="cancelForm" action="/Zaiko2020//Edit" method="post">
			<input type="submit" name="button" id="cancel" class="button button-cancel button-border" value="キャンセル">
		</form>
	</span>
</body>
</html>