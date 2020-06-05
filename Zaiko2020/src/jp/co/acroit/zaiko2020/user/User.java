package jp.co.acroit.zaiko2020.user;

/**
 * ユーザー クラス
 * @version 1.0
 * @author Hiroe Ishioka
 */
public class User {

    private long number;
    private String id;
    private String password;

    /**
     * ユーザー情報の初期化
     * @param number 通し番号
     * @param id ログインID
     * @param password パスワード
     */
    public User(long number, String id, String password) {
        this.number = number;
        this.id = id;
        this.password = password;
    }

    /**
     * 通し番号を返す
     * @return 通し番号
     */
    public long getNumber() {
        return number;
    }

    /**
     * IDを返す
     * @return ユーザーID
     */
    public String getId() {
        return id;
    }

    /**
     * パスワードを返す
     * @return パスワード
     */
    public String getPassword() {
        return password;
    }

}
