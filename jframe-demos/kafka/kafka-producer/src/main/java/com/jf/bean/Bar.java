package com.jf.bean;

public class Bar {

    private Integer id;
    private String number;

    public Bar() {
    }

    public Bar(Integer id, String number) {
        this.id = id;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
