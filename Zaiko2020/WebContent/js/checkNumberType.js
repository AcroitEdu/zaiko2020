$(function(){
  $('input[name="isbn"]').on('input', function(){
       check_numtype($(this));
  });
});

// １．グローバル変数（一時的に保存しておく）を宣言
var _chknum_value = "";
// 入力値の半角数字チェック
function check_numtype(obj){

  // ２．変数の定義
  var txt_obj = $(obj).val();
  var text_length = txt_obj.length;

  // ３．入力した文字が半角数字かどうかチェック
  if(txt_obj.match(/^[0-9]+$/)){
       // ３．１．文字数チェック
       if(text_length > 9){
            $('input[name="isbn"]').val(_chknum_value);
       }else{
            _chknum_value = txt_obj;
       }
  }else{
       // ３．２．入力した文字が半角数字ではないとき
       if(text_length == 0){
            _chknum_value = "";
       }else{
            $('input[name="isbn"]').val(_chknum_value);
            $('.errorTest').innerHTML = "outputTest";
       }
  }
}