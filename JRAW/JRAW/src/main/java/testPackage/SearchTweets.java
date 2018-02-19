package testPackage;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchTweets {

    public static int tweetCount =0;
    public String keyWord;
    public List<String> comments = new ArrayList<String>();
    public List<String> dateCreate = new ArrayList<String>();
    private final Object lock = new Object();
    public Connection conn;
    boolean connected;

    public SearchTweets() {

    }
    public SearchTweets(String word){
        keyWord = word;
    }

    //Twitter streaming function
    public void streamTweets() {
        final List<Status> statuses = new ArrayList();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        //cb.setOAuthConsumerKey("xpC0bZQzicKi86PkiAMbGnH5r");
        cb.setOAuthConsumerKey("UYleS1XoOv2FWdRK1wC5KGGOu");
        //cb.setOAuthConsumerSecret("C8UHlacOqoW0aK6Z5Gm1dlAuilKg0VkAAYy3HhN8pn2R2bmkjs");
        cb.setOAuthConsumerSecret("A2cgxJoqilO8TdaLV9BYcngeIRbiHYjgYUtFh2lUtv2lEU056M");
        //cb.setOAuthAccessToken("297930922-KrKImjlAIPYsEXrvYb2Cu9B6D8prupXrU26EPWeZ");
        cb.setOAuthAccessToken("959040167775412224-ht6JjhL8NPaH9WPqpnh99v8QDt28wYA");
        //cb.setOAuthAccessTokenSecret("CK0VPLFaCMyLNYCtd90yWHAXjsrrgIUifZHsSVLoOICNW");
        cb.setOAuthAccessTokenSecret("en7e5kGYYX2zD5zZa0SprhjoHzy1MGFhBo6R6I7fwkknd");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        StatusListener listener = new StatusListener() {

//        	@Override
//        	public void onStallWarning(twitter4j.StallWarning warning){
//        		// TODO Auto-generated method stub
//            }

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub
            }



            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
            }

            //This function is for the streaming of tweets
            //using keywords found in the array.
            @Override
            public void onStatus(Status status) {
                statuses.add(status);
                if (statuses .size() > 1000) {
                    synchronized (lock) {
                        lock.notify();
                    }

                } else {
                    //Increment the amount of tweets crawled
                    tweetCount ++;

                    User user = status.getUser();
                    String word = keyWord;

                    //Increment the amount of tweets crawled
                    System.out.println(tweetCount);

                    // Parsing JSON data
                    String username = status.getUser().getScreenName();
                    System.out.println("Username: " + username);

                    // Get location
                    String profileLocation = user.getLocation();
                    System.out.println("Location: " + profileLocation);

                    // Get tweet ID
                    long tId = status.getId();
                    System.out.println("Tweet ID: " +tId);

                    // Get tweet contents
                    String content = status.getText();
                    System.out.println("Content: " + content);

                    //Get time tweet posted
                    java.util.Date timeStamp = status.getCreatedAt();
                    System.out.println("Timestamp: " + timeStamp +"\n");

                    try {
                        //Establish connection
                        System.out.println(connected);
                        if (connected) {
                            System.out.println("Connected");
                            Statement myStat = conn.createStatement();

                            if (content.contains("'") == false && content.contains("RT") == false) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String createDate = sdf.format(timeStamp);
                                //SQL query to insert
                                String sql = "INSERT into tweets"
                                        + "(`username`, `location`, `content`, `Keyword`, `created_date`)"
                                        + "values('"+username+"', '"+profileLocation+"', '"+content+"', '"+word+"', '"+createDate+"')";

                                myStat.executeUpdate(sql);

                                System.out.println("Update complete\n");
                            }
                        }

                    }
                    catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }



            }



            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning arg0) {
                // TODO Auto-generated method stub

            }
        };

        FilterQuery fq = new FilterQuery();

        String keywords = this.keyWord;
        fq.track(keywords);

        //fq.language("en");
        sqlConnection();
        twitterStream.addListener(listener);
        twitterStream.filter(fq);

        try {
            synchronized (lock){
                lock.wait();
            }
        }catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public void StartCrawl() {
        SearchTweets tweetsObj = new SearchTweets(this.keyWord);
        tweetsObj.streamTweets();
    }

    public boolean executeSelect() throws SQLException {
        String word = keyWord;
        this.comments.clear();
        if (this.connected){
            String select = "SELECT content,created_date FROM tweets WHERE Keyword = ?";
            this.comments = new ArrayList<String>();
            PreparedStatement state = this.conn.prepareStatement(select);
            state.setString(1,word);
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                this.comments.add(rs.getString("content"));
                this.dateCreate.add(rs.getString("created_date"));
            }
            return true;
        } else {
            return false;
        }


    }

    protected void executeQueryforClear() throws SQLException {
        this.sqlConnection();
        String sql = "Delete from tweets";
        PreparedStatement state = this.conn.prepareStatement(sql);
        state.execute();
    }
    public boolean sqlConnection() {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/twitter?serverTimezone=UTC", "root", "");
            connected = true;
            return true;
        }catch (Exception e) {
            System.err.println("Got an exception");
            System.err.println(e.getMessage());
            return false;
        }

    }
    public void closeconn() throws SQLException {
        this.conn.close();
    }

}

