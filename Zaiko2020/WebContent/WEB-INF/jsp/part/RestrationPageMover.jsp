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

	<li class="pagesPrevNext">
		<form name="prevPage" action="/Zaiko2020/Restoration" method="post">
			<input type="hidden" name="form" value="ページ">
			<input type="hidden" class="inputPage" name="page" value="${RestorationForm.page - 1}" data-max="${maxPage}">
			<span class="link prevButton">前へ</span>
		</form>
	</li>
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

	<li id="sumahoPage" class="pagesJump">
		<span>${conditions.page}</span>
		<span> / </span>
		<span>${maxPage}</span></li>

	<li class="pagesPrevNext">
		<form name="nextPage" action="/Zaiko2020/Restoration" method="post">
			<input type="hidden" name="form" value="ページ">
			<input type="hidden" class="inputPage" name="page" value="${RestorationForm.page + 1}" data-max="${maxPage}">
			<span class="link nextButton">次へ</span>
		</form>
	</li>

	<li id="pcPage" class="pagesSpace"></li>
	<li id="pcPage" class="pagesItemCount">${count}件</li>
</ul>