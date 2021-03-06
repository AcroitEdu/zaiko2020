package jp.co.acroit.zaiko2020.book;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 書籍クラス
 * @version 1.0
 * 書籍クラス新規作成
 * @version 1.1
 * isbn・price・stockの型をStringに変更
 * @version 1.1
 * 変数名の脱字修正
 * @author hiroki tajima
 */
public class Book implements Serializable {
	private int id;
	private String name;
	private String publisher;
	private String author;
	private String isbn;
	private LocalDate salesDate;
	private String price;
	private String stock;
	private int deleteFlag;

	//書籍情報の初期化
	public Book(int id, String name, String publisher, String author, String isbn, LocalDate dbSalesDate, String price,
			String stock,
			int deleteFlag) {
		this.id = id;
		this.name = name;
		this.publisher = publisher;
		this.author = author;
		this.isbn = isbn;
		this.salesDate = dbSalesDate;
		this.price = price;
		this.stock = stock;
		this.deleteFlag = deleteFlag;
	}

	//各種ゲッターとセッター
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public LocalDate getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(LocalDate salesDate) {
		this.salesDate = salesDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
