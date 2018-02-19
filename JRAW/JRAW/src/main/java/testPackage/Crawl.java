package testPackage;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.pagination.SearchPaginator;
import net.dean.jraw.pagination.SearchPaginator.QuerySyntax;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;

public class Crawl {
    String keyWord;
    Connection conn;
    String query, select;
    RedditClient reddit;
    List<String> comments = new ArrayList<String>();
    List<String> dateCreate = new ArrayList<String>();
    ResultSet rs;
    private String createDate;
    String time;
    SearchPaginator searchAll;


    public Crawl() {
        // TODO Auto-generated constructor stub
    }

    public Crawl(String word) {
        this.keyWord = word;
    }


    protected boolean sqlConnection() {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Reddit?serverTimezone=UTC", "root", "");
            this.query = "insert into crawldata (Author, Body, Keyword, edit_date)" + "values (?, ?, ?, ?)";
            this.select = "SELECT body, edit_date FROM crawldata WHERE Keyword = ?";
            return true;

        } catch (Exception e) {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            return false;
        }
    }

    protected boolean connectReddit() {
        UserAgent userAgent = new UserAgent("desktop", "testPackage", "v0.01", "BoringBar");
        String username = "BoringBar";
        Credentials credentials = Credentials.script(username, "ICT1009", "q2WWY1qFcgAkoA", "HzLIG0f8jP-Nh2axueYHYGhl39A");
        NetworkAdapter adapter = new OkHttpNetworkAdapter(userAgent);
        this.reddit = OAuthHelper.automatic(adapter, credentials);
        return true;
    }

    protected void executeQueryforInserting(String Author, String Body, String ED) throws SQLException {
        PreparedStatement preparedStmt  = this.conn.prepareStatement(this.query);
        preparedStmt.setString(1, Author);
        preparedStmt.setString(2, Body);
        preparedStmt.setString(3, this.keyWord);
        preparedStmt.setString(4, ED);
        preparedStmt.execute();
    }

    protected void executeQueryforSelecting() throws SQLException {
        this.comments.clear();
        this.sqlConnection();
        PreparedStatement state = this.conn.prepareStatement(this.select);
        state.setString(1,this.keyWord);
        this.rs = state.executeQuery();
        while (this.rs.next()) {
            this.comments.add(this.rs.getString("body"));
            this.dateCreate.add(this.rs.getString("edit_date"));
        }
    }

    protected void executeQueryforClear() throws SQLException {
        this.sqlConnection();
        String sql = "Delete from crawldata";
        PreparedStatement state = this.conn.prepareStatement(sql);
        state.execute();
    }
    public void closeconn() throws SQLException {
        this.conn.close();
    }

    protected void StartCrawl () throws SQLException {
        int counter = 0;
        if (connectReddit()) {
            if (this.time.equals("Past Month")) {
                this.searchAll = this.reddit
                        .search()
                        .query(keyWord)
                        .limit(Paginator.RECOMMENDED_MAX_LIMIT)
                        .syntax(QuerySyntax.PLAIN)
                        .timePeriod(TimePeriod.MONTH)
                        .build();
            }else if (this.time.equals("Past Year")) {
                this.searchAll = this.reddit
                        .search()
                        .query(keyWord)
                        .limit(Paginator.RECOMMENDED_MAX_LIMIT)
                        .syntax(QuerySyntax.PLAIN)
                        .timePeriod(TimePeriod.YEAR)
                        .build();
            }else if (this.time.equals("Past Week")) {
                this.searchAll = this.reddit
                        .search()
                        .query(keyWord)
                        .limit(Paginator.RECOMMENDED_MAX_LIMIT)
                        .syntax(QuerySyntax.PLAIN)
                        .timePeriod(TimePeriod.WEEK)
                        .build();
            }else {
                this.searchAll= this.reddit
                        .search()
                        .query(keyWord)
                        .limit(Paginator.RECOMMENDED_MAX_LIMIT)
                        .syntax(QuerySyntax.PLAIN)
                        .build();
            }
            //Search through whole reddit

            //Put all results into a list
            //java.util.List<Submission> allMovieRecord = searchAll.accumulateMerged(-1);
            java.util.List<Submission> allMovieRecord = this.searchAll.accumulateMerged(1);

            if (sqlConnection()) {
                //Iterate through all posts and submissions
                for (Submission sub: allMovieRecord) {
                    if (counter == 10) {
                        System.out.println("Crawled Done");
                        break;
                    }else {
                        if (sub.getTitle().toLowerCase().contains(this.keyWord.toLowerCase())) {
                            int incounter = 1;
                            RootCommentNode root = reddit.submission(sub.getId()).comments();
                            Iterator<CommentNode<PublicContribution<?>>> it = root.walkTree().iterator();
                            System.out.println(counter + "=========");
                            System.out.println("Title: " + sub.getTitle());
                            System.out.println("=========================================");
                            while (it.hasNext()) {
                                PublicContribution<?> thing = it.next().getSubject();
                                if (thing.getBody() != null) {
                                    System.out.println("    "+counter+"."+incounter+")"+"Comments: "+thing.getBody());
                                    System.out.println("Created Date:  " + thing.getCreated());
                                }
                                this.comments.add(thing.getBody());
		    				String user = thing.getAuthor();
		    				String comment = thing.getBody();
		    				if (comment == null) {
		    				    comment = "No comment";
                            }
		    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    				this.createDate = sdf.format(thing.getCreated());
		    				executeQueryforInserting(user, comment, this.createDate);
                                System.out.println("USer: " + thing.getAuthor());
                                incounter++;

                            }
                        }
                        counter++;
                    }

                }
            }
        }


    }

}
