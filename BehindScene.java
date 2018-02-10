package testPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import com.squareup.moshi.JsonDataException;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.SearchSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dean.jraw.pagination.Paginator;
import net.dean.jraw.pagination.SearchPaginator;
import net.dean.jraw.pagination.SearchPaginator.Builder;
import net.dean.jraw.references.SubredditReference;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;

public class BehindScene {
	String keyWord;
	Connection conn;
	String query;
	RedditClient reddit;

	public BehindScene() {
		// TODO Auto-generated constructor stub
	}
	
	public BehindScene(String word) {
		this.keyWord = word;
	}
	
	protected boolean sqlConnection() {
		try {
        	String myDriver = "org.gjt.mm.mysql.Driver";
        	Class.forName(myDriver);
        	this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Reddit", "root", "");
    	    this.query = "insert into crawldata (Author, Body)" + "values (?, ?)";
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
	
	protected void executeQuery(String Author, String Body) throws SQLException {
		PreparedStatement preparedStmt  = this.conn.prepareStatement(this.query);
		preparedStmt.setString(1, Author);
		preparedStmt.setString(2, Body);
		preparedStmt.execute();
	}
	
	protected void StartCrawl () throws SQLException {
		int counter = 0;
		if (connectReddit()) {
			//Creating a Paginator and set subreddit to movies
	        DefaultPaginator<Submission> searchMovie = this.reddit
	        		.subreddit("movies") //Set Subreddit to movies 
	        		.posts()
	        	    .limit(Paginator.RECOMMENDED_MAX_LIMIT) 
	        	    .build();
	        //Put all results into a list
	        java.util.List<Submission> allRecords = searchMovie.accumulateMerged(-1);
			
			if (sqlConnection()) {
				 //Iterate through all posts and submissions
		        for (Submission sub: allRecords) {
		        	if (sub.getTitle().toLowerCase().contains(this.keyWord.toLowerCase())) {
		        		int incounter = 1;
		        		RootCommentNode root = reddit.submission(sub.getId()).comments();
		            	Iterator<CommentNode<PublicContribution<?>>> it = root.walkTree().iterator();
		        		System.out.println(counter + "=========");
		    			System.out.println("Title: " + sub.getTitle());
		    			System.out.println("=========================================");
		    			while (it.hasNext()) {
		    				PublicContribution<?> thing = it.next().getSubject();
		    				System.out.println("    "+counter+"."+incounter+")"+"Comments: "+thing.getBody());
		    				String user = thing.getAuthor();
		    				String comment = thing.getBody();
		    				if (comment == null) {
		    					comment = "No comment";
		    				}
		    				executeQuery(user, comment);
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
