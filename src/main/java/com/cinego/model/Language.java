package com.cinego.model;

import jakarta.persistence.*;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int languageId;

    @Column(name = "language_name")
    private String languageName;

    public Language() {
    }

    public Language(int languageId, String languageName) {
        this.languageId = languageId;
        this.languageName = languageName;
    }

    @Override
    public String toString() {
        return "Language{" +
                "languageId=" + languageId +
                ", languageName='" + languageName + '\'' +
                '}';
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}