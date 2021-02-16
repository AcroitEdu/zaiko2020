/**
 * 入力値の入力値種別制限と桁数制限
 * ＊対象
 * 【検索条件】：ISBN 在庫数
 * 【検索結果一覧】：移動ページ指定
 *
 */

//共用変数
var limitedDigits = 0;
var totalInputData = "";
// var previousInputValue = "";
// var currentInputValue = 0
var messagePlace = $('#errorMessage'); //メッセージ出力先

//桁数制限と入力値保存用変数

//ISBN
var limitIsbnDigits = 13;
var isbnInputData = "";
//ISBNの入力値チェック
$(function(){
  $('input[name="isbn"]').on('input', function(){
	limitedDigits = limitIsbnDigits;
    checkNumberTypeLimit($(this));
  });
});


//在庫数
var limitStockDigits = 9;
//在庫数の入力値チェック
$(function(){
  $('input[name="stock"]').on('input', function(){
	limitedDigits = limitStockDigits;
    checkNumberTypeLimit($(this));
  });
});

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

function resetTotalInputData() {
  totalInputData = "";
}