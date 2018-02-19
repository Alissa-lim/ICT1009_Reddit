package testPackage;

import javafx.beans.property.StringProperty;

public class twitterPassing extends convert {
    private StringProperty twitter_comment;
    private StringProperty twitter_date;
    private StringProperty twitter_sen;

    public twitterPassing(String comments, String date, String sen) {
        super(comments, date, sen);
    }

    public  String gettwitter_comment() {
        return super.comment.get();
    }

    public void setID(String comments) {
        super.comment.set(comments);
    }

    public StringProperty twitter_commentProperty() {
        return super.comment;
    }

    public StringProperty twitter_dateProperty() {
        return super.createdDate;
    }

    public String gettwitter_date() {
        return super.createdDate.get();
    }

    public StringProperty twitter_senProperty() {
        return super.sentiment;
    }
    public  String gettwitter_sen() {
        return super.sentiment.get();
    }
}
