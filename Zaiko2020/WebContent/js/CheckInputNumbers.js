/**
 *入力値の半角数字制限と桁数制限
 *＊対象：【検索】ISBN、在庫数 【ページ移動】ページ先
 */

//桁数設定
var limitIsbnDigits = 13;
var limitStockDigits = 6;
var limitPagingDigits = 6
var limitedDigits = 0;
var totalValue = "";
var previousInputName = "";
var currentInputName = "";

$(function(){
	$('input[type="tel"]').on('click', function(){
		console.log(this);
		cuucurrentInputName = $(this).data('name');
		console.log(currentInputName);
		if(previousInputName != currentInputName){
			totalValue = "";
		}

	});
});

//ISBNの入力値チェック
$(function(){
  $('input[name="isbn"]').on('input', function(){
	limitedDigits = limitIsbnDigits;
    checkNumberTypeLimit($(this));
  });
});

//在庫数の入力値チェック
$(function(){
  $('input[name="stock"]').on('input', function(){
	limitedDigits = limitStockDigits;
    checkNumberTypeLimit($(this));
  });
});

//ページングの入力値チェック
$(function(){
  $('input[name="page"]').on('input', function(){
	limitedDigits = limitPagingDigits;
    checkNumberTypeLimit($(this));
  });
});

// １．グローバル変数（一時的に保存しておく）を宣言
var messagePlace = $('#errorMessage');
// 入力値の半角数字チェック
function checkNumberTypeLimit(obj){

  // ２．変数の定義
  var inputValue = $(obj).val();
  console.log(inputValue);
console.log(totalValue);
  var valueLength = inputValue.length;

  // ３．入力した文字が半角数字かどうかチェック
  if(inputValue.match(/^[0-9]+$/)){
       // ３．１．文字数チェック
       if(valueLength > limitedDigits){
            $(obj).val(totalValue);
       }else{
            totalValue = inputValue;
			messagePlace.text("");
       }
  }else{
       // ３．２．入力した文字が半角数字ではないとき
       if(valueLength == 0){
            totalValue = "";
       }else{
            $(obj).val(totalValue);
            $('#errorMessage').text("半角数字のみが入力できます。");
       }
  }
  previousInputName = currentInputName;
}


//1582年問題 取得->切出->比較->処理 format yyyy-mm-dd
$(function(){
  $('input[name="date"]').on('input', function(){
	var saleDate = $(this).val();
	var limitYear = 1582;
	var limitMonth = 10;
	var limitDay = 14;
	var outputMessage = "1582年10月15日以降が入力できます。"
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
