package org.wowser.evenbuspro.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Wowser on 2016/3/18.
 */
public class Books implements Serializable{
    private int count;
    private int start;
    private int total;
    private List<BookModel> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<BookModel> getBooks() {
        return books;
    }

    public void setBooks(List<BookModel> books) {
        this.books = books;
    }


}
