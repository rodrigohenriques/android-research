package com.github.android.research.domain.model;

public class Research {

    protected int id;
    protected String name;

    public Research(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
