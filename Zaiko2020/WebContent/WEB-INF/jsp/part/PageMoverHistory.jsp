<%@ page language="java" pageEncoding="UTF-8"%>
<%
	//「次へ」や「前へ」、「移動」等の、ページ移動の機能を持つ。
//InventoryList.jspからincludeして使う。
%>
<ul class="pagesBox">
	<li id="pcPage" class="pagesCounterWeight">
		<input type="submit" id="initializeButton" class="button" form="inventoryListForm" value="一覧初期化">
	</li>
	<li id="pcPage" class="pagesSpace"></li>

	<div class="flexPageJump">
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
			<form name="jumpPage" action="/Zaiko2020/HistoryList" method="post">
				<input type="hidden" name="form" value="1">
				<span class="pegeText">
					<input type="tel" class="inutPageNumber" name="page" required ma4width
					pattern="^[1-9][0-9]*$" oncopy="return false" onpaste="return false"
					maxlength="3" value="${conditions.page}" onblur="resetTotalInputData()">
					<span class="pageDelimiter">/</span> <span class="maxPage">${maxPage}</span>
				</span>
				<input type="submit" class="button" value="移動">
			</form>
			
		</li>
		<!-- 移動ページ指定ここまで -->

		<!-- 現在のページ/総ページ数 (スマホのみ) -->
		<li id="sumahoPage" class="pagesJump">
			<span>${conditions.page}</span>
			<span> / </span>
			<span>${maxPage}</span>
		</li>
		<!-- 現在のページ/総ページ数ここまで (スマホのみ)-->

		<!-- 1ページ進む -->
		<li class="pagesPrevNext">
			<form name="nextPage" action="/Zaiko2020/inventoryList" method="post">
				<input type="hidden" name="form" value="1">
				<input type="hidden" class="inputPage" name="page" value="${conditions.page + 1}" data-max="${maxPage}">
			<span class="link nextButton">次へ</span>
			</form>
		</li>
	</div>


	<!-- 1ページ進むここまで -->

	<li id="pcPage" class="pagesSpace"></li>
	<!-- 総件数 -->
	<li id="pcPage" class="pagesItemCount">${count}件</li>
	<!-- 総件数ここまで -->
</ul>