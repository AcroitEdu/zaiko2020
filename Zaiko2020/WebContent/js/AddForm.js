//ログアウトボタンを押したときの処理
$("#logoutButton").click(function () {
    if (window.confirm(`ログアウトしますか?`)) {
        $(`#logoutForm`).submit();
    }
});