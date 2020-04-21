package com.nordsgn.mymovies.data;

public class Trailer {

    private String name;
    private String key;

    // Додавим конструктор alt+insert
    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    // Добавим гетеры и сеттеры alt+insert

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
