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
  $('input[name="operatingDate"]').on('input', function(){
	var saleDate = $(this).val();
	var limitYear = 2020;
	var limitMonth = 6;
	var limitDay = 1;
	var outputMessage = "2020年6月1日以降を入力して下さい。"
	var messagePlace = $('#dateValidationMessage');
	var sliceDate = saleDate.split('-');
	if(sliceDate[0] < limitYear){
		// 2020年より以前
		$('#dateValidationMessage').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] < limitMonth){
		//2020年６月より以前
		$('#dateValidationMessage').text(outputMessage);
	//　↓日数用
	// }else if(sliceDate[0] == limitYear && sliceDate[1] == limitMonth && sliceDate[2] <= limitDay){
	// 	$('#dateValidationMessage').text(outputMessage);
	}else{
		messagePlace.text("");
	}

	//↓テスト用
	// console.log(sliceDate[0] < limitYear);
	// console.log(sliceDate[0] == limitYear && sliceDate[1] < limitMonth);
	// console.log(sliceDate[1] == limitMonth && sliceDate[2] <= limitDay);
  });
});

//共用変数
var limitedDigits = 0;
var totalInputData = "";

//移動ページ指定
var limitPagingDigits = 3;
//ページングの入力値チェック
$(function(){
  $('input[name="page"]').on('input', function(){
	limitedDigits = limitPagingDigits;
    checkNumberTypeLimit($(this));
  });
});

function checkNumberTypeLimit(obj){

  // ２．変数の定義
  var inputValue = $(obj).val();
  console.log(inputValue);
  console.log(totalInputData);
	var valueLength = inputValue.length;
	var messagePlace = $('#errorMessage'); //メッセージ出力先

  // ３．入力した文字が半角数字かどうかチェック
  if(inputValue.match(/^[0-9]+$/)){
       // ３．１．文字数チェック
       if(valueLength > limitedDigits){
            $(obj).val(totalInputData);
       }else{
            totalInputData = inputValue;
			messagePlace.text("");
       }
  }else{
       // ３．２．入力した文字が半角数字ではないとき
       if(valueLength == 0){
            totalInputData = "";
       }else{
            $(obj).val(totalInputData);
            $('#errorMessage').text("半角数字のみが入力できます。");
       }
  }

}

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