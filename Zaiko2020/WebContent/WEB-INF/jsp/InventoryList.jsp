<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.book.Book"%>
<%@ page import="java.util.List"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%
int currentPage = 1;
if (session.getAttribute("page") != null) {
	currentPage = (int) session.getAttribute("page");
}
int maxPage = 1;
if (session.getAttribute("maxPage") != null) {
	maxPage = (int) session.getAttribute("maxPage");
}
int count = 1;
if (session.getAttribute("count") != null) {
	count = (int) session.getAttribute("count");
}
//表示する本の取得
List<Book> items = (List<Book>) session.getAttribute("items");
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年<br/>'MM'月'dd'日'");
DateTimeFormatter dateFormatSumaho = DateTimeFormatter.ofPattern("YYYY'年'MM'月'dd'日'");
%>
<!DOCTYPE html>
<html lang='ja'>

<head>
<meta charset='UTF-8'>
<meta name='viewport' content='width=device-width, initial-scale=1.0'>
<meta http-equiv='X-UA-Compatible' content='ie=edge'>
<title>在庫一覧</title>
<link href="https://unpkg.com/sanitize.css" rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap"
	rel="stylesheet">
<link href="styleInventoryList.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.4/css/all.css">
<link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
</head>

<body>
	<div id="main">
		<header>
			<ul class="boxed-tabs">
				<li class="tab-current tab">在庫一覧</li>
				<li id="addButton" class="tab">
					<form id="addForm" class="button" name="button" action="/Zaiko2020/Add" method="post">
						<input type="hidden" name="form" value="追加">
						<span>追加</span>
					</form>
				</li>
				<li id="restorationButton" class="tab">
					<form id="restorationForm" action="/Zaiko2020/Restoration" method="post">
						<input type="hidden" name="form" value="復元">
						<span>復元</span>
					</form>
				</li>
				<li id="historyListButton" class="tab">
					<form id="historyListForm" action="/Zaiko2020/HistoryList" method="post">
						<input type="hidden" name="form" value="1">
						<span>履歴</span>
					</form>
				</li>
				<li id="logoutButton" class="tab-logout tab">
					<form id="logoutForm" action="/Zaiko2020/logout" method="post">
						<span>ログアウト</span>
					</form>
				</li>
			</ul>
		</header>

		<div class="content">
			<!-- 検索フォーム -->
			<!-- スマホ画面表示プルダウンメニュー -->
			<input type="button" id="searchDisplay" class="displayButton" value="検索条件　▼"
				onclick="document.getElementById('searchHidden').style.display = 'block'; document.getElementById('searchDisplay').style.display = 'none'; document.getElementById('search').style.display = 'block';">
			<input type="button" id="searchHidden" class="displayButton" value="検索条件　▲"
				onclick="document.getElementById('searchHidden').style.display = 'none'; document.getElementById('searchDisplay').style.display = 'block'; document.getElementById('search').style.display = 'none';">
			<!-- スマホ画面表示プルダウンメニュー -->
			<div id="search" class="search-options">
				<form name="searchOptions" action="/Zaiko2020/inventoryList" method="post" id="searchOptions">
					<ul id="flexFormWrappable">
						<li>
							<label>書籍名</label>
							<input type="text" id="bookName" class="flexFormItem" name="name" value="${conditions.name}">
						</li>
						<li>
							<label>著者</label> <input type="text" id="author" class="flexFormItem" name="author" value="${conditions.author}">
						</li>
						<li>
							<label>出版社</label>
							<input type="text" id="publisher" class="flexFormItem" name="publisher" value="${conditions.publisher}">
						</li>
						<li>
							<label>ISBN</label>
							<input
								type="tel" id="isbn" class="flexFormItem" name="isbn"
								pattern="^[0-9]+$" oncopy="return false" onpaste="return false"
								maxlength="13" value="${conditions.isbn}" onblur="resetTotalInputData()">
						</li>
						<li><label>発売日</label>
							<div class="flexFormItem select">
								<input
									type="date" id="date" name="date" value="${conditions.salesDate}"
									max="9999-12-31" min="1582-10-15">
								<select id="beforeAfter" name="beforeAfter" data-value="${conditions.salesDateFlag}">
									<option value="equals">に一致</option>
									<option value="before">以前</option>
									<option value="after">以降</option>
								</select>
							</div>
						</li>
						<li>
						<label>在庫数</label>
							<div class="flexFormItem select flexStock">
									<input
										type="tel" id="stock" name="stock" pattern="^[0-9]+$"
										oncopy="return false" onpaste="return false" maxlength="10"
										value="${conditions.stock}" onblur="resetTotalInputData()">
									<span id="stockText">冊</span>
									<select id="largeOrSmall" name="largeOrSmall" data-value="${conditions.stockFlag}">
										<option value="equals">に等しい</option>
										<option value="ltoe">以下</option>
										<option value="gtoe">以上</option>
									</select>
							</div>
						</li>
					</ul>
					<input type="hidden" name="form" value="0">
				</form>
				<div id="submitArea">
					<div id="pcSpace"></div>
					<div id="buttons">
						<input type="submit" id="searchButton" class="button" form="searchOptions" value="検　索">
						<input type="reset" id="clearButton" class="button" form="searchOptions" value="クリア">
					</div>

				</div>
				<form id="inventoryListForm" action="/Zaiko2020/inventoryList"
						method="post">
					<input type="hidden" name="form" value="3">
				</form>
			</div>
			<!-- 検索フォームここまで -->

			<!-- スマホ画面ソート -->
			<input type="button" id="sortDisplay" class="displayButton" value="ソート条件　▼"
				onclick="document.getElementById('sortHidden').style.display = 'block'; document.getElementById('sortDisplay').style.display = 'none';  document.getElementById('sort').style.display = 'block';">
			<input type="button" id="sortHidden" class="displayButton" value="ソート条件　▲"
				onclick="document.getElementById('sortHidden').style.display = 'none';  document.getElementById('sortDisplay').style.display = 'block'; document.getElementById('sort').style.display = 'none';">
			<div id="sort" class="sort-options">
				<form name="sortOptions" action="/Zaiko2020/inventoryList" method="post">
					<ul id="flexFormWrappable">
						<li>
							<label>条件</label>
							<div class="flexFormItem select">
								<select id="sortItem" name="index" data-value="${conditions.sort}">
									<option value="0">発売日</option>
									<option value="1">ISBN</option>
									<option value="2">在庫数</option>
								</select>
								<select id="sortOrder" name="direction" data-value="${conditions.lift}">
									<option value="1">昇順</option>
									<option value="-1">降順</option>
								</select>
							</div>
						</li>
					</ul>
					<input type="hidden" name="form" value="2">
					<input type="submit" id="sortButton" class="button" value="ソート">
				</form>
			</div>
			<!-- スマホ画面ソートここまで -->

			<!-- エラーメッセージ表示 -->
			<div id="error">
				<span id="errorMessage">${sessionScope.error}</span>
			</div>
			<!-- エラーメッセージ表示ここまで -->

			<!-- 書籍一覧表 -->
			<div class="list">
				<div class="pages">
					<%@ include file="part/PageMover.jsp"%>
				</div>
				<div id="list">
					<form id="sortForm" action="/Zaiko2020/inventoryList" method="post">
						<input type="hidden" id="sortIndex" name="index" value="${conditions.sort}"> <input type="hidden" id="sortDirection" name="direction" value="${conditions.lift}">
						<input type="hidden" name="form" value="2">
					</form>
					<!-- PC表示 -->
					<div id="pcList">
						<table id="listTable">
							<thead>
								<tr id="listHeaders">
									<th id="headerControl" class="headerFixed">操作</th>
									<th id="headerName" class="headerGrow2">書籍名</th>
									<th id="headerAuthor" class="headerGrow2">著者</th>
									<th id="headerPublisher" class="headerGrow2">出版社</th>
									<th id="headerSalesDate" class="listHeaderSortable headerFixed" data-sort-index="0">発売日
										<span class="sortArrows">
										<span class="sortArrow" title="発売日 昇順でソートします" data-sort-direction="1" id="saleDateAscendingOrder"> ↑ </span>
										<span class="sortArrow" title="発売日 降順でソートします" data-sort-direction="-1"> ↓ </span>
									</span>
									</th>
									<th id="headerIsbn" class="listHeaderSortable headerFixed" data-sort-index="1">ISBN <span class="sortArrows">
										<span class="sortArrow" title="ISBN 昇順でソートします" data-sort-direction="1"> ↑ </span>
										<span class="sortArrow" title="ISBN 降順でソートします" data-sort-direction="-1"> ↓ </span>
									</span>
									</th>
									<th id="headerPrice" class="headerGrow1">価格</th>
									<th id="headerStock" class="listHeaderSortable headerGrow1" data-sort-index="2">在庫数
										<span class="sortArrows">
										<span class="sortArrow" title="在庫数 昇順でソートします" data-sort-direction="1"> ↑ </span>
										<span class="sortArrow" title="在庫数 降順でソートします" data-sort-direction="-1"> ↓ </span>
									</span>
									</th>
								</tr>
							</thead>
							<tbody id="none">
								<%
									if (items != null) {
									for (Book item : items) {
								%>
								<tr style="width: 100%;">
									<td class="dataControl dataCenter">
										<form action="/Zaiko2020/arrivalForm" method="post" class="formEditLink inline">
											<input type="hidden" name="id" value="<%=item.getId()%>">
											<span class="link buttonArrive">入荷</span>
										</form> <br>
										<form action="/Zaiko2020/shippingForm" method="post" class="formEditLink inline">
											<input type="hidden" name="id" value="<%=item.getId()%>">
											<span class="link buttonShip">出荷</span>
										</form> <br>
										<form action="/Zaiko2020/Edit" method="post" class="formEditLink inline">
											<input type="hidden" name="id" value="<%=item.getId()%>">
											<input type="hidden" name="button" value="編集">
											<span class="link buttonEdit">編集</span>
										</form>
									</td>
									<td class="dataName data"><%=item.getName()%></td>
									<td class="dataAuthor data"><%=item.getAuthor()%></td>
									<td class="dataPublisher data"><%=item.getPublisher()%></td>
									<td class="dataSalesDate dataCenter"><%=item.getSalesDate().format(dateFormat)%></td>
									<td class="dataIsbn dataCenter"><%=item.getIsbn()%></td>
									<td class="dataPrice dataRight data"><%=item.getPrice()%> 円</td>
									<!--数字をダブルクリックしたときの利便性を考慮しスペースを挿入-->
									<td class="dataStock dataRight data"><%=item.getStock()%> 冊</td>
								</tr>
								<%
									}
								}
								%>
							</tbody>
						</table>
					</div>
					<!-- PC表示ここまで -->

					<!-- スマホ表示 -->
					<div id="sumahoList">
						<%
							if (items != null) {
							for (Book item : items) {
						%>
						<table id="listTable">
							<tbody>
								<tr>
									<th>書籍名</th>
									<td class="dataName data" colspan="3"><%=item.getName()%></td>
								</tr>
								<tr>
									<th>著者</th>
									<td class="dataAuthor data" colspan="3"><%=item.getAuthor()%></td>
								</tr>
								<tr>
									<th>出版社</th>
									<td class="dataPublisher data" colspan="3"><%=item.getPublisher()%></td>
								</tr>
								<tr>
									<th>発売日</th>
									<td class="dataSalesDate dataCenter data" colspan="3"><%=item.getSalesDate().format(dateFormatSumaho)%></td>
								</tr>
								<tr>
									<th>ISBN</th>
									<td class="dataIsbn dataCenter data" colspan="3"><%=item.getIsbn()%></td>
								</tr>
								<tr>
									<th>価格</th>
									<td class="dataPrice dataRight data"><%=item.getPrice()%> 円</td>
									<th>在庫数</th>
									<td class="dataStock dataRight data"><%=item.getStock()%> 冊</td>
								</tr>
								<tr>
									<th>操作</th>
									<td class="dataControl dataCenter" colspan="3">
										<form action="/Zaiko2020/arrivalForm" method="post" class="formEditLink inline">
											<input type="hidden" name="id" value="<%=item.getId()%>">
											<span class="link buttonArrive">入荷</span>
										</form>
										<span> / </span>
										<form action="/Zaiko2020/shippingForm" method="post" class="formEditLink inline">
											<input type="hidden" name="id" value="<%=item.getId()%>">
											<span class="link buttonShip">出荷</span>
										</form>
										<span> / </span>
										<form action="/Zaiko2020/Edit" method="post" class="formEditLink inline">
											<input type="hidden" name="id" value="<%=item.getId()%>">
											<input type="hidden" name="button" value="編集">
											<span class="link buttonEdit">編集</span>
										</form>
									</td>
								</tr>
							</tbody>
						</table>
						<%
							}
						}
						%>
					</div>
					<!-- スマホ表示ここまで -->
				</div>
				<div class="pages">
					<%@ include file="part/PageMover.jsp"%>
				</div>
			</div>
			<!-- 書籍一覧表ここまで -->
		</div>
	</div>

	<p class="pagetop">
		<a href="#"> <i class="fas fa-chevron-up"></i>
		</a>
	</p>

</body>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="js/InventoryLists.js"></script>
<script src="js/TabTransition.js"></script>
<script src="js/InputDataValidation.js" charset="utf-8"></script>
<script src="js/CalenderValidation.js" charset="utf-8"></script>
</html>