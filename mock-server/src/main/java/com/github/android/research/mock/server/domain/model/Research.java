package com.github.android.research.mock.server.domain.model;

public class Research {

    protected int id;
    protected String name;
    protected String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Research(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Research(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
