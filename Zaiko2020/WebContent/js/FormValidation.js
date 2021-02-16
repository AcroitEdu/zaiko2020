/**
 *
 * @version 1.0
 * @author yohei nishida
 *
 * 追加・編集における入力に対するエラー出力
 */

//1582年問題 取得->切出->比較->処理 format yyyy-mm-dd
$(function(){
  $('input[name="date"]').on('input', function(){
	var saleDate = $(this).val();
	var limitYear = 1582;
	var limitMonth = 10;
	var limitDay = 14;
	var outputMessage = "1582年10月15日以降を入力して下さい。"
	//var sliceYear = saleDate.slice(0,3);
	var sliceDate = saleDate.split('-');
	if(sliceDate[0] < limitYear){
		$('.dateError').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] < limitMonth){
		$('.dateError').text(outputMessage);
	}else if(sliceDate[0] == limitYear && sliceDate[1] == limitMonth && sliceDate[2] <= limitDay){
		$('.dateError').text(outputMessage);
	}else{
		$('.dateError').text("");
	}

  // 機能確認用
	// console.log(sliceDate[0] < limitYear);
	// console.log(sliceDate[0] == limitYear && sliceDate[1] < limitMonth);
	// console.log(sliceDate[1] == limitMonth && sliceDate[2] <= limitDay);
  });
});

// ISBN・在庫数・価格の入力制御

//共用変数
var limitedDigits = 0;
var totalInputData = "";
var messagePlace = $('#errorMessage'); //メッセージ出力先

//桁数制限と入力値保存用変数

//ISBN
var limitIsbnDigits = 13;
var isbnInputData = "";

//ISBNの入力値チェック
$(function(){
  $('input[name="isbn"]').on('input', function(){
	limitedDigits = limitIsbnDigits;
  messagePlace = $('.isbnError');
    checkNumberTypeLimit($(this));
  });
});

//価格
var limitPagingDigits = 7;

//価格の入力値チェック
$(function(){
  $('input[name="price"]').on('input', function(){
  messagePlace = $('.priceError');
	limitedDigits = limitPagingDigits;
    checkNumberTypeLimit($(this));
  });
});


//在庫数
var limitStockDigits = 9;

//在庫数の入力値チェック
$(function(){
  $('input[name="stock"]').on('input', function(){
  messagePlace = $('.stockError');
	limitedDigits = limitStockDigits;
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
            messagePlace.text("半角数字のみが入力できます。");
       }
  }

}

function resetTotalInputData() {
  totalInputData = "";
}