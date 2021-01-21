/**
 * 多重押下に対する処理
 * 一度押下されたら、そのボタンを無効化し、二度以上の押下を抑制
 */

// 入出荷数共用
$(function(){
  $('#dialogExecute').on('click', function(){
    $(this).prop("disabled",true);
  });
});