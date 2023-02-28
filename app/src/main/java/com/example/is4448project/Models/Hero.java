package com.example.is4448project.Models;

public class Hero {
    private String id, name, realname, rating, teamaffiliation;

    public Hero() {
    }

    public Hero(String id, String name, String realname, String rating, String teamaffiliation) {
        this.id = id;
        this.name = name;
        this.realname = realname;
        this.rating = rating;
        this.teamaffiliation = teamaffiliation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTeamaffiliation() {
        return teamaffiliation;
    }

    public void setTeamaffiliation(String teamaffiliation) {
        this.teamaffiliation = teamaffiliation;
    }

    @Override
    public String toString() {
        return "id: " + id + "\n" +
                "name: " + name + "\n" +
                "realname: " + realname + "\n" +
                "rating: " + rating + "\n" +
                "teamaffiliation: " + teamaffiliation;
    }
}
