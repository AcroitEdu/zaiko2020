package jp.co.acroit.zaiko2020.auth;
/**
 * パスワード照合クラス
 * @version 1.0
 * @author Hiroe Ishioka
 */
public class PasswordComparator {

	/**
	 * 入力されたパスワードを、実際のパスワードと照合するメソッド
	 * @param formPass 入力されたパスワード
	 * @param pass 実際のパスワード
	 * @return 一致ならtrue、不一致ならfalse
	 */
    public boolean compare(String formPass, String pass) {
        return formPass.equals(pass);
    }

}
