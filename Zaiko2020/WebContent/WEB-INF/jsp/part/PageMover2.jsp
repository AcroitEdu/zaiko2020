<%@ page language="java" pageEncoding="UTF-8"%>
<%
//「次へ」や「前へ」、「移動」等の、ページ移動の機能を持つ。
//InventoryList.jspからincludeして使う。
%>
<ul class="pagesBox">
    <li class="pagesCounterWeight"></li>
    <li class="pagesSpace"></li>
    <li class="pagesPrevNext">
        <form name="prevPage" action="/Zaiko2020/RestorationProcess" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" class="inputPage" name="page" value="${conditions.page - 1}" data-max="${maxPage}">
            <span class="link prevButton">前へ</span>
        </form>
    </li>
    <li class="pagesJump">
        <form name="jumpPage" action="/Zaiko2020/Restoration" method="post">
            <input type="hidden" name="form" value="1">
            <span>
				<input type="text" class="inutPageNumber" name="page" required pattern="^[0-9]+$" value="${conditions.page}">
                <span class="pageDelimiter">/</span>
                <span class="maxPage">${maxPage}</span>
            </span>
            <input type="submit" class="button" value="移動">
        </form>
    </li>
    <li class="pagesPrevNext">
        <form name="nextPage" action="/Zaiko2020/RestorationProcess" method="post">
            <input type="hidden" name="form" value="1">
            <input type="hidden" class="inputPage" name="page" value="${conditions.page + 1}" data-max="${maxPage}">
            <span class="link nextButton">次へ</span>
        </form>
    </li>
    <li class="pagesSpace"></li>
    <li class="pagesItemCount">${count} 件</li>
</ul>