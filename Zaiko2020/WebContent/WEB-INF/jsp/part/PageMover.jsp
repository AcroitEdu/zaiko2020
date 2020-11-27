<%@ page language="java" pageEncoding="UTF-8"%>
<%
	//「次へ」や「前へ」、「移動」等の、ページ移動の機能を持つ。
//InventoryList.jspからincludeして使う。
%>
<ul class="pagesBox">
	<li id="pcPage" class="pagesCounterWeight"></li>
	<li id="pcPage" class="pagesSpace"></li>

	<!-- 1ページ戻る -->
	<li class="pagesPrevNext">
		<form name="prevPage" action="/Zaiko2020/inventoryList" method="post">
			<input type="hidden" name="form" value="1">
			<input type="hidden" class="inputPage" name="page" value="${conditions.page - 1}" data-max="${maxPage}">
			<span class="link prevButton">前へ</span>
		</form>
	</li>
	<!-- 1ページ戻るここまで -->

	<!-- 移動ページ指定 -->
	<li id="pcPage" class="pagesJump">
		<form name="jumpPage" action="/Zaiko2020/inventoryList" method="post">
			<input type="hidden" name="form" value="1">
			<span>
				<input type="tel" class="inutPageNumber" name="page" required pattern="^[1-9][0-9]*$" maxlength="9" value="${conditions.page}">
				<span class="pageDelimiter">/</span> <span class="maxPage">${maxPage}</span>
			</span>
			<input type="submit" class="button" value="移動">
		</form>
	</li>
	<!-- 移動ページ指定ここまで -->

	<!-- 現在のページ/総ページ数 -->
	<li id="sumahoPage" class="pagesJump">
		<span>${conditions.page}</span>
		<span> / </span>
		<span>${maxPage}</span>
	</li>
	<!-- 現在のページ/総ページ数ここまで -->

	<!-- 1ページ進む -->
	<li class="pagesPrevNext">
		<form name="nextPage" action="/Zaiko2020/inventoryList" method="post">
			<input type="hidden" name="form" value="1">
		<input type="hidden" class="inputPage" name="page" value="${conditions.page + 1}" data-max="${maxPage}">
		<span class="link nextButton">次へ</span>
		</form>
	</li>
	<!-- 1ページ進むここまで -->

	<li id="pcPage" class="pagesSpace"></li>
	<!-- 総件数 -->
	<li id="pcPage" class="pagesItemCount">${count}件</li>
	<!-- 総件数ここまで -->
</ul>