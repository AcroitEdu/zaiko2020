<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>書籍追加の確認</title>

</head>
<body>
	<h1>以下の内容で書籍の追加を行います。</h1>
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
	<input type="submit" id="addButton" class="button" name="button" value="実行">
	<input type="button" id="cancelButton" class="button" name="button" value="キャンセル">

</body>
</html>