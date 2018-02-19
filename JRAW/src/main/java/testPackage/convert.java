package testPackage;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class convert {
    protected StringProperty comment;
    protected StringProperty createdDate;
    public  convert(String com, String date) {
        this.comment = new SimpleStringProperty(com);
        this.createdDate = new SimpleStringProperty(date);
    }


}
