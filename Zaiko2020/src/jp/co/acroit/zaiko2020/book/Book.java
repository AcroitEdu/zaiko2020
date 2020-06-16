package jp.co.acroit.zaiko2020.book;

import java.io.Serializable;
import java.util.Date;

/**
 * ユーザークラス
 * @version 1.0
 * @author Hiroki Tajima
 */
public class Book implements Serializable {
	private int id;
	private String name;
	private String publisher;
	private String author;
	private String isbn;
	private Date salesDate;
	private int price;
	private int stock;
	private int deleteFlag;

	public Book(int id, String name, String publisher, String author, String isbn, Date salesDate, int price, int stock,
			int deleteFlag) {
		this.id = id;
		this.name = name;
		this.publisher = publisher;
		this.author = author;
		this.isbn = isbn;
		this.salesDate = salesDate;
		this.price = price;
		this.stock = stock;
		this.deleteFlag = deleteFlag;
	}

	public Book() {

	}

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

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}
