//在庫一覧ボタンを押したときの処理
$("#inventoryListButton").click(function () {
	$(`#inventoryListForm`).submit();
});

//追加ボタンを押したときの処理
$("#addButton").click(function () {
	$(`#addForm`).submit();
});

//復元ボタンを押したときの処理
$("#restorationButton").click(function () {
	$(`#restorationForm`).submit();
});

//ログアウトボタンを押したときの処理
$("#logoutButton").click(function () {
    if (window.confirm(`ログアウトしますか?`)) {
        $(`#logoutForm`).submit();
    }
});