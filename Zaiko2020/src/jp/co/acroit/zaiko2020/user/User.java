package jp.co.acroit.zaiko2020.user;

/**
 * ユーザークラス
 * @version 1.0
 * @author Hiroe Ishioka
 */
public class User {

    private long number;
    private String id;
    private String password;

    // ユーザー情報の初期化
    public User(long number, String id, String password) {
        this.number = number;
        this.id = id;
        this.password = password;
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

}
