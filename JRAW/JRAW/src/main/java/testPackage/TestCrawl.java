package testPackage;
import java.sql.SQLException;
import java.util.*;

public class TestCrawl {

    public static void main(String[] args) throws SQLException {
        System.out.println("Please enter keyword");
        Scanner input = new Scanner(System.in);
        String word = input.nextLine();
        Crawl test = new Crawl();
        test.keyWord = word;
        test.StartCrawl();
    }

}
