package model;

public class Format {
    private int formatId;
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