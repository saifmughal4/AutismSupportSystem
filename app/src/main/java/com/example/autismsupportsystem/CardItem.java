package com.example.autismsupportsystem;

public class CardItem {
    private String headerTitle;
    private String bodyText;
    private int headerImageResId;
    private int bodyImageResId;

    public CardItem(String headerTitle, String bodyText, int headerImageResId, int bodyImageResId) {
        this.headerTitle = headerTitle;
        this.bodyText = bodyText;
        this.headerImageResId = headerImageResId;
        this.bodyImageResId = bodyImageResId;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public String getBodyText() {
        return bodyText;
    }

    public int getHeaderImageResId() {
        return headerImageResId;
    }

    public int getBodyImageResId() {
        return bodyImageResId;
    }
}
