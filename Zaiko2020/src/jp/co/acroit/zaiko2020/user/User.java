package jp.co.acroit.zaiko2020.user;

/**
 * ユーザークラス
 * @version 1.0
 * @version 1.1
 * プロパティ：ログイン状況'loginStatus'とゲッターを追加
 * @author Yohei.Nishida
 */
public class User {

    private long number;
    private String id;
    private String password;
    private int loginStatus;

    // ユーザー情報の初期化
    public User(long number, String id, String password, int loginStatus) {
        this.number = number;
        this.id = id;
        this.password = password;
        this.loginStatus = loginStatus;
    }

    // 各種ゲッター
    public long getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public int getLoginStatus() {
		return loginStatus;
	}

}
