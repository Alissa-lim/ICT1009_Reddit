package testPackage;

import javafx.beans.property.StringProperty;

public class TestData extends convert {

    private  StringProperty reddit_comment;
    private  StringProperty dateCreated;

    public TestData(String comments, String date) {
        super(comments, date);
    }

    public  String getreddit_comment() {
        return super.comment.get();
    }

    public void setID(String comments) {
        super.comment.set(comments);
    }

    public StringProperty reddit_commentProperty() {
        return super.comment;
    }

    public StringProperty dateCreatedProperty() {
        return super.createdDate;
    }
    public String getdateCreated() {
        return  super.createdDate.get();
    }
}
