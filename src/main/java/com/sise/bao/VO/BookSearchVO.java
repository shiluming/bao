package com.sise.bao.VO;

/**
 * 搜索条件
 * Created by rola on 2017/4/26.
 */
public class BookSearchVO {

    private String bookName;
    private String author;
    private Double price;
    private String pushling;
    private String desc;
    private String categoryName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPushling() {
        return pushling;
    }

    public void setPushling(String pushling) {
        this.pushling = pushling;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
