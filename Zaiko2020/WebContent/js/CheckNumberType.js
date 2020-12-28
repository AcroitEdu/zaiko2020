/**
 *
 */
//数字桁数設定
  isbnDigits = 13;
  stockDigits = 6;
  pageDigits = $('.maxPage').val();
  var limitedNumberDigits;

//ISBNの入力値チェック
$(function(){
  $('input[name="isbn"]').on('input', function(){
	   limitedNumberDigits = isbnDigits;
       check_numtype($(this));
  });
});

//在庫数の入力値チェック
$(function(){
  $('input[name="stock"]').on('input', function(){
       check_numtype($(this));
  });
});

//ページ移動の入力値チェック
$(function(){
  $('input[name="page"]').on('input', function(){
       check_numtype($(this));
  });
});

// １．グローバル変数（一時的に保存しておく）を宣言
var _chknum_value = "";
// 入力値の半角数字チェック
function check_numtype(obj){
  console.log(Math.floor(pageDigits / 10) + 1);

  // ２．変数の定義
  var txt_obj = $(obj).val();
  var text_length = txt_obj.length;
  var messagePlace = $('#errorMessage');

  // ３．入力した文字が半角数字かどうかチェック
  if(txt_obj.match(/^[0-9]+$/)){
       // ３．１．文字数チェック
       if(text_length > limitedNumberDigits){
            $(obj).val(_chknum_value);
       }else{
            _chknum_value = txt_obj;
			messagePlace.text("");

       }
  }else{
       // ３．２．入力した文字が半角数字ではないとき
       if(text_length == 0){
            _chknum_value = "";
       }else{
            $('input[name="isbn"]').val(_chknum_value);
            $('#errorMessage').text("半角数字のみが入力できます。");
       }
  }
}