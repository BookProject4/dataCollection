package book;

import java.util.Date;

public class BookVO {
	private long isbn;
	private String category,subCategory,poster,title,subtitle,writer,publisher,etcInfo,price,discount,infoText,imgs,contentsTable,tags,publicationDay;
	
	public long getIsbn() {
		return isbn;
	}
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getEtcInfo() {
		return etcInfo;
	}
	public void setEtcInfo(String etcInfo) {
		this.etcInfo = etcInfo;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getInfoText() {
		return infoText;
	}
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getContentsTable() {
		return contentsTable;
	}
	public void setContentsTable(String contentsTable) {
		this.contentsTable = contentsTable;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getPublicationDay() {
		return publicationDay;
	}
	public void setPublicationDay(String publicationDay) {
		this.publicationDay = publicationDay;
	}
	
}
