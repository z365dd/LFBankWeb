package com.adtec.pay.webResp;

public class ComRespBody<T> {
    private int start;
    private int limit;
    private T data;
    private int total;


    public ComRespBody(int start, int limit, T data, int total) {
        this.start = start;
        this.limit = limit;
        this.data = data;
        this.total = total;
    }


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
