package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел.:"),
    SKYPE("Skype:"),
    EMAIL("Почта:"),
    LINKEDINPROFILE("Профиль LinkedIn "),
    GITHUBPROFILE("Профиль GitHub "),
    STACKOVERFLOWPROFILE("Профиль Stackoverflow "),
    HOMEPAGE("Домашняя страница ");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
