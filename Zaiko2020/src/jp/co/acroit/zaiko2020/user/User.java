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

	public User (long number, String id,String password) {
		this.number = number;
		this.id = id;
		this.password = password;
	}

	// ゲッター
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
