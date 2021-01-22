<%@ page language="java" pageEncoding="UTF-8"%>
<%
	//本の情報と入荷・出荷フォームを表示する。
//ArrivalForm.jspやShippingForm.jspからincludeして使う。
//入荷・出荷の表示切り替えは変数modeにより行う。
%>

<div id="modal">
	<div id="modalContent">
		<p>
			<span id="countConfirm" class="align-text"></span> 冊の<%=request.getParameter("mode")%>処理を行います。
		</p>
		<p>
			※ボタンを複数回押さないように<br>注意してください。
		</p>
		<button id="dialogExecute" class="button button-main">OK</button>
		<button id="dialogCancel" class="button button-cancel">キャンセル</button>
	</div>
</div>
<div id="overlay"></div>

<div id="main">
	<header>
		<span id="mode"><%=request.getParameter("mode")%></span>
	</header>
	<div id="error">
		<span>${sessionScope.error}</span>
	</div>
	<div id="details">
		<jsp:include page="BookDetails.jsp">
			<jsp:param name="caption" value="" />
			<jsp:param name="book" value="${book }" />
		</jsp:include>
	</div>
	<footer>
		<div id="inout">
			<form id="inoutForm" action="/Zaiko2020/<%=request.getParameter("action")%>" method="post">
				<span>
					<label><%=request.getParameter("mode")%>数
				</label>
				<input type="tel" id="count" name="count" required pattern="^[0-9]+$" 
					maxlength="10"  oncopy="return false" onpaste="return false">
				<input type="hidden" id="formId" name="id" value="${book.id }">
				<span class="unit">冊</span>
				</span>
				<br>
			</form>
		</div>
		<div id="inputMessage">
			<span id="inputErrorMessage">sampleTest</span>
		</div>
		<span id="buttons">
			<button id="execute" class="button button-main button-border">実行</button>
			<input type="submit" id="cancel" class="button button-cancel button-border" value="キャンセル" form="cancelForm">
		</span>
		<form id="cancelForm" action="/Zaiko2020/inventoryList" method="post">
			<input type="hidden" name="form" value="4">
		</form>
	</footer>
</div>