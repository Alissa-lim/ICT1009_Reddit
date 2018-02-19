package testPackage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class imdbpulls {
    public static String movies[] = new String[8];
    public static String keyword;

    public static void parse() throws IOException {
        if (keyword == " " || keyword == null) {
            System.out.print("KeyWord cannot be null");
        }else {
            String mov = keyword;

            mov = mov.replace(" ", "+");

            Document doc2 = Jsoup.connect("http://www.imdb.com/find?ref_=nv_sr_fn&q="+mov+"&s=all").userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36")
                    .get();

            Elements links = doc2.select("a[href*=tt_1]");
            Element link;
            String array[] = new String [links.size()];
            int i =0;
            for (Element n: links) {
                String url = n.attr("href");
                String str = url.substring(7,16);
                array[i] = str;
                i++;

            }
            Document doc = Jsoup.connect("http://www.omdbapi.com/?i=" + array[0] + "&apikey=e115cdf7&r=xml")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.87 Safari/537.36")
                    .get();
            Elements links2 = doc.select("movie[title]");

            for (Element element : links2) {
                movies[0] = element.attr("title");
                movies[1] = element.attr("rated");
                movies[2] = element.attr("runtime");
                movies[3] = element.attr("genre");
                movies[4] = element.attr("released");
                movies[5] = element.attr("plot");
                movies[6] = element.attr("imdbRating");
                movies[7] = element.attr("poster");
                //array[i] = (element.absUrl("href")); //<-------HERE*****
                //i++;

            }
        }



    }

    /*public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            parse();


        } catch (Exception e){
            System.out.println("Exception found!");
            e.printStackTrace();
        }
    }*/
}