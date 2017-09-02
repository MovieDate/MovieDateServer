package com.moviedateserver.entity;

public class PersonList {
    private String name;
    private int byPersonId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getByPersonId() {
        return byPersonId;
    }

    public void setByPersonId(int byPersonId) {
        this.byPersonId = byPersonId;
    }

    @Override
    public String toString() {
        return "PersonList{" +
                "name='" + name + '\'' +
                ", byPersonId=" + byPersonId +
                '}';
    }
}
