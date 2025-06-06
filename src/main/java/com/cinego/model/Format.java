package com.cinego.model;

import jakarta.persistence.*;

@Entity
@Table(name = "formats")
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "format_id")
    private int formatId;

    @Column(name = "format_name")
    private String formatName;

    public Format() {
    }

    public Format(int FormatId, String FormatName) {
        this.formatId = FormatId;
        this.formatName = FormatName;
    }

    @Override
    public String toString() {
        return "Format{" +
                "formatId=" + formatId +
                ", formatName='" + formatName + '\'' +
                '}';
    }

    public int getFormatId() {
        return formatId;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }
}