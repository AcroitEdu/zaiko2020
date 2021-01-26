/**
 * 履歴一覧
 * @version 1.0
 * @author yohei nishida
 */
/**
 * 日付の入力に対する制限
 */

//1582年問題 取得->切出->比較->処理 format yyyy-mm-dd
$(function(){
  $('input[name="OperatingDate"]').on('input', function(){
	var saleDate = $(this).val();
	var limitYear = 2020;
	var limitMonth = 6;
	var limitDay = 1;
	var outputMessage = "2020年6月1日以降を入力して下さい。"
	//var sliceYear = saleDate.slice(0,3);
	var sliceDate = saleDate.split('-');
	if(sliceDate[0] < limitYear){
		$('#errorMessage').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] < limitMonth){
		$('#errorMessage').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] == limitMonth && sliceDate[2] <= limitDay){
		$('#errorMessage').text(outputMessage);
	}else{
		messagePlace.text("");
	}


	console.log(sliceDate[0] < limitYear);
	console.log(sliceDate[0] == limitYear && sliceDate[1] < limitMonth);
	console.log(sliceDate[1] == limitMonth && sliceDate[2] <= limitDay);
  });
});

//postリクエストを送信するリンク(「次へ」等)の処理
function linkSubmit() {
  if ($(this).hasClass("disabled")) return;
  var form = $(this).parent();
  form.submit();
}

//「次へ」及び「前へ」に対する範囲チェックと範囲外の操作の無効化
function linkDetectOutOfRange(index, element) {
  var input = $(element).parent().find(".inputPage");
  var value = parseInt(input.val());
  if (value < 1 || value > parseInt(input.attr("data-max"))) {
      $(element).addClass("disabled");
  }
}

//「次へ」をクリックした時の処理
var nextButtons = $(".nextButton");
nextButtons.click(linkSubmit);
//次のページが存在しないときにボタンを無効化する処理
nextButtons.each(linkDetectOutOfRange);

//「前へ」をクリックした時の処理
var prevButtons = $(".prevButton");
prevButtons.click(linkSubmit);
//前のページが存在しないときにボタンを無効化する処理
prevButtons.each(linkDetectOutOfRange);

//select要素のデフォルト選択項目の設定(検索条件の保持)
var selects = $("#beforeAfter, #operations");
selects.each(function (index, element) {
    var elem = $(element);
    var value = elem.attr("data-value");
    elem.find(`option[value='${value}']`).prop("selected", true);
});