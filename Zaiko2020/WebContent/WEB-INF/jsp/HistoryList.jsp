<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page import="jp.co.acroit.zaiko2020.history.History"%>
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
List<History> lists = (List<History>) session.getAttribute("lists");
//日付フォーマットの作成
DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY'年<br/>'MM'月'dd'日'");
DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH'時'mm'分'ss'秒'");
%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset='UTF-8'>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='X-UA-Compatible' content='ie=edge'>
    <title>履歴一覧</title>
    <link href="https://unpkg.com/sanitize.css" rel="stylesheet">
    <link
      href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP&display=swap"
      rel="stylesheet">
    <link href="styleHistoryList.css" rel="stylesheet">
    <link rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.6.4/css/all.css">
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
  </head>
  <body>
    <!-- header + nav -->
    <header>
      <nav>
        <ul class="boxed-tabs">
          <li id="inventoryListButton" class="tab">
            <form id="inventoryListForm" action="/Zaiko2020/inventoryList"
              method="post">
              <input type="hidden" name="form" value="3">
              <span>在庫一覧</span>
            </form>
          </li>
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
          <li class="tab-current tab">履歴</li>
          <li id="logoutButton" class="tab-logout tab">
            <form id="logoutForm" action="/Zaiko2020/logout" method="post">
              <span>ログアウト</span>
            </form>
          </li>
        </ul>
      </nav>
    </header>
    <!-- header + nav 此処まで -->
    <!-- 検索 + 履歴一覧 -->
    <div id="main">
      <!-- 検索条件pc -->
      <article id="search">
        <div class="searchConditions">
          <form action="#" method="post" id="conditions">
            <ul class="flexForm">
              <li>
                <label for="operatingDate">日付</label>
                <input type="date" name="operatingDate" id="operatingDate" 
                value="${conditions.operatingDate}" max="9999-12-31" min="2020-06-01">
                <select name="beforeAfter" id="beforeAfter" data-value="${conditions.salesDateFlag}">
                  <option value="lower">以前</option>
                  <option value="equals" selected>一致</option>
                  <option value="higher">以降</option>
                </select>
              </li>
              <li>
                <span id="dateValidationMessage"></span>
              </li>
              <li>
                <label for="operator">実行者</label>
                <input type="text" name="operator" id="operator" 
                  value="${conditions.stock}" 
                  oncopy="return false" onpaste="return false">
              </li>
              <li>
                <label for="operations">操作</label>
                <select name="operation" id="operations" 
                  data-value="${conditions.stockFlag}">
                  <!-- 順番 テーブル定義書に準拠 -->
                  <option value="1">入　荷</option>
                  <option value="2">出　荷</option>
                  <option value="3">削　除</option>
                  <option value="4">編　集</option>
                  <option value="5">追　加</option>
                  <option value="6">復　元</option>
                  <option value="99" selected>全操作</option>
                </select>
              </li>
            </ul>
          </form>
        </div>
        <div class="searchButtons">
          <input type="submit" value="絞り込み検索" form="conditions">
          <input type="reset" value="" form="conditions">
        </div>
        <div>
          <span id="errorMessage">${sessionScope.error}</span>
        </div>
      </article>
      <!-- 検索条件pc 此処まで -->
      <!-- 履歴一覧pc -->
      <article id="pcList">
        <div class="pages">
          <%@ include file="part/PageMover.jsp"%>
        </div>
        <div class="historyList">
          <table>
            <thead>
              <tr>
                <th class="headerDate">日　付</th>
                <th class="headerTime">時　刻</th>
                <th class="headerOperator">実行者</th>
                <th class="headerBookName">書籍名</th>
                <th class="headerOperation">操　作</th>
              </tr>
            </thead>
            <tbody>
              <%
                if (lists != null) {
                  for (History list : lists) {
              %>
              <tr>
                <td class="data dataDate"><%=list.getDate().format(dateFormat)%></td>
                <td class="data dataTime"><%=list.getTime().format(timeFormat)%</td>
                <td class="data dataOperator"><%=list.getUserId()%></td>
                <td class="data dataBookName"><%=list.getBookId()%></td>
                <td class="data dataOperation"><%=list.getOperationId()%></td>
              </tr>
              <%
                  }
                } 
              %>
            </tbody>
          </table>
        </div>
        <div class="pages">

        </div>
      </article>
      <!-- 履歴一覧pc 此処まで -->
      <!-- 履歴一覧sp -->
      <article id= "spList">
        <p>sample-Text</p>
      </article>
      <!-- 履歴一覧 此処まで -->
    </div>
    <!-- 検索 + 履歴一覧 此処まで -->
    <!-- footer(予備) -->
    <footer>
      <span>&copy; 2020 Acroit 技術研修 秋季卒</span>
    </footer>
    <!-- footer(予備) 此処まで -->
  </body>
  <script
  	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="js/TabTransition.js"></script>
  <script src="js/HistoryList.js"></script>
</html>