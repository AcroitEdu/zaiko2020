package jp.co.acroit.zaiko2020.data;

/**
 * 検索条件クラス
 * @version 1.0
 * @author hiroki tajima
 */
public class SearchCondition {
	private String name;
	private String publisher;
	private String author;
	private String isbn;
	private String salesDate;
	private String stock;
	private String salesDateFlag;
	private String stockFlag;
	private int page;
	private int sort;	//ソートする項目(0: 発売日, 1: ISBN, 2: 在庫数)
	private int lift;	//ソート順序(1: 昇順, -1: 降順)

	//各種ゲッターとセッター
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

	public String getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getSalesDateFlag() {
		return salesDateFlag;
	}

	public void setSalesDateFlag(String salsDateFlag) {
		this.salesDateFlag = salsDateFlag;
	}

	public String getStockFlag() {
		return stockFlag;
	}

	public void setStockFlag(String stockFlag) {
		this.stockFlag = stockFlag;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getLift() {
		return lift;
	}

	public void setLift(int lift) {
		this.lift = lift;
	}

}
