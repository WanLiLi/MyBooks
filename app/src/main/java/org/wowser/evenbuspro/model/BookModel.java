package org.wowser.evenbuspro.model;

import java.io.Serializable;
import java.util.List;

public class BookModel implements Serializable,Comparable<BookModel>{
    private String publisher;
    private String image;
    private String title;
    private String price;
    private   List<String> author;



    private int type = 0; //
    private boolean isVisibleFlag = false;

    public boolean isVisibleFlag() {
        return isVisibleFlag;
    }

    public void setVisibleFlag(boolean visibleFlag) {
        isVisibleFlag = visibleFlag;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    @Override
    public int compareTo(BookModel another) {
        int i ;
        i = this.title.compareTo(another.title);
        if(i == 0) {    // 如果名字一样,比较
            return  i;
        } else {
            return i; // 名字不一样, 返回比较名字的结果.
        }
        //价格，但是有元字符
        //return (int) (Double.parseDouble(this.price) - Double.parseDouble(another.price));
    }
}