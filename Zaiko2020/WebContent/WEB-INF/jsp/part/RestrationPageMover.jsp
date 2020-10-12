<%@ page language="java" pageEncoding="UTF-8"%>
<%
	//「次へ」や「前へ」、「移動」等の、ページ移動の機能を持つ。
//RestrationForm.jspからincludeして使う。
%>
<ul class="pagesBox">
	<li id="pcPage" class="pagesCounterWeight">
		<input type="button" class="restrationButton execute button-main button-border" type="button" form="check" value="選択項目の復元">
	</li>

	<li id="pcPage" class="pagesSpace"></li>

	<!-- 1ページ戻る -->
	<li class="pagesPrevNext">
		<form name="prevPage" action="/Zaiko2020/Restoration" method="post">
			<input type="hidden" name="form" value="ページ">
			<input type="hidden" class="inputPage" name="page" value="${RestorationForm.page - 1}" data-max="${maxPage}">
			<span class="link prevButton">前へ</span>
		</form>
	</li>
	<!-- 1ページ戻るここまで -->

	<!-- 移動ページ指定 -->
	<li id="pcPage" class="pagesJump">
		<form name="jumpPage" action="/Zaiko2020/Restoration" method="post">
			<input type="hidden" name="form" value="ページ">
			<span>
				<input type="tel" class="inutPageNumber" name="page" required pattern="^[1-9][0-9]*$" maxlength="9" value="${RestorationForm.page}">
				<span class="pageDelimiter">/</span>
				<span class="maxPage">${maxPage}</span>
			</span>
			<input type="submit" class="button" value="移動">
		</form>
	</li>
	<!-- 移動ページ指定 -->

	<!-- 現在のページ/総ページ数 -->
	<li id="sumahoPage" class="pagesJump">
		<span>${RestorationForm.page}</span>
		<span> / </span>
		<span>${maxPage}</span>
	</li>
	<!-- 現在のページ/総ページ数ここまで -->

	<!-- 1ページ進む -->
	<li class="pagesPrevNext">
		<form name="nextPage" action="/Zaiko2020/Restoration" method="post">
			<input type="hidden" name="form" value="ページ">
			<input type="hidden" class="inputPage" name="page" value="${RestorationForm.page + 1}" data-max="${maxPage}">
			<span class="link nextButton">次へ</span>
		</form>
	</li>
	<!-- 1ページ進むここまで -->

	<li id="pcPage" class="pagesSpace"></li>
	<!-- 総件数 -->
	<li id="pcPage" class="pagesItemCount">${count}件</li>
	<!-- 総件数ここまで -->
</ul>