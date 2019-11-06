package ru.javawebinar.basejava.model;

import java.util.Objects;

public class HyperLinkStorage {
    private String link;

    public HyperLinkStorage(ContactType type, String text) {
        switch (type) {
            case PHONE:
                link = type.getTitle() + "<a href=\"tel:" + text + "\">" + text + "</a>";
                break;
            case SKYPE:
                link = type.getTitle() + "<a href=\"skype:" + text + "\">" + text + "</a>";
                break;
            case EMAIL:
                link = type.getTitle() + "<a href=\"mailto:" + text + "\">" + text + "</a>";
                break;
            case LINKEDINPROFILE:
                link = "<a href=\"" + text + "\">" + type.getTitle() + "</a>";
                break;
            case GITHUBPROFILE:
                link = "<a href=\"" + text + "\">" + type.getTitle() + "</a>";
                break;
            case STACKOVERFLOWPROFILE:
                link = "<a href=\"" + text + "\">" + type.getTitle() + "</a>";
                break;
            case HOMEPAGE:
                link = "<a href=\"" + text + "\">" + type.getTitle() + "</a>";
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HyperLinkStorage that = (HyperLinkStorage) o;
        return link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
