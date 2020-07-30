//在庫一覧ボタンを押したときの処理
$("#inventoryListButton").click(function () {
	$(`#inventoryListForm`).submit();
});

//ログアウトボタンを押したときの処理
$("#logoutButton").click(function () {
    if (window.confirm(`ログアウトしますか?`)) {
        $(`#logoutForm`).submit();
    }
});