package testPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class convert {
    protected StringProperty comment;
    protected StringProperty createdDate;
    protected  StringProperty sentiment;
    public  convert(String com, String date, String sen) {
        this.comment = new SimpleStringProperty(com);
        this.createdDate = new SimpleStringProperty(date);
        this.sentiment = new SimpleStringProperty(sen);
    }


}
