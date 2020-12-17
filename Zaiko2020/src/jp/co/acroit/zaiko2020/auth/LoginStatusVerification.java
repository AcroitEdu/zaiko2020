package jp.co.acroit.zaiko2020.auth;

/**
 * ログイン状況確認クラス
 * @version 1.0
 * @author 西田洋平
 *
 */

public class LoginStatusVerification {

	/**
	 * ログインを試みたユーザーが二重ログインの有無を確認するメソッド
	 * @param loginStatus DBから取得したログイン状況フラグ
	 * @param statusOfNoLogin 未ログイン時のフラグ
	 * @return 未ログインならtrue, ログイン済みならfalse
	 */
	public boolean verify(int loginStatus) {
		int statusOfNoLogin = 0;
		return loginStatus == statusOfNoLogin;
	}

}
