package jp.co.acroit.zaiko2020.history;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 履歴クラス
 * @version 1.0
 *
 * @author yohei nishida
 *
 */

public class History {
	private int id;
	private LocalDate date;
//	private LocalDate time;
	private LocalTime time;
	private String userId;
	private String bookId;
	private String operationId;

	//履歴クラスの初期化(一覧表示用)

	public History (int id, LocalDate date, LocalTime time,
			String userId, String bookId, String operationId) {
		this.id = id;
		this.date = date;
		this.time = time;
		this.userId = userId;
		this.bookId = bookId;
		this.operationId = operationId;
	}

	//各種ゲッター、セッター
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}


}
